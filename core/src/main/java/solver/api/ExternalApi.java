package solver.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.Solver;
import solver.api.dto.*;
import solver.http.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalApi extends AbstractApi implements Api {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalApi.class);

    public static final String WS_SOCKET = "ws://socket";

    public static final String INIT_URL = "/init";
    public static final String KEY_FORMAT = "%d_%d";

    private String teamName;

    private String token;

    private ApiClient apiClient = new ApiClient();

    private WebSocketClient ws;
    private volatile boolean isWsCreated;

    private ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    ;

    @Getter
    private volatile List<Point> pointList;
    private volatile boolean pointsReady = false;

    @Getter
    private volatile List<Route> routeList;
    private volatile boolean routesReady = false;

    @Getter
    private volatile Map<String, Double> trafficMap = new ConcurrentHashMap<>();
    private volatile boolean trafficReady = false;

    @Getter
    private volatile Map<String, Car> carMap = new ConcurrentHashMap<>();

    private volatile Long level;

    @Getter
    @Setter
    private volatile Solver solver;

    private final String url;

    public ExternalApi(String teamName, String initUrl) throws Exception {
        this.teamName = teamName;
        this.url = initUrl;
        restartWs();
        LOGGER.info("Start solver");
    }

    private void parseCars(List<String> cars) {
        for (String car : cars) {
            this.carMap.put(car, new Car(car, 0));
        }
    }

    @Override
    public void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException {
        carMap.get(car).target = target;
        if (noMoney) {
            send(ApiOutput.goTo(target, car, true));
        } else {
            send(ApiOutput.goTo(target, car));
        }
    }

    public void send(ApiOutput input) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(input);
        LOGGER.info("Send: {}", message);
        ws.send(message);
    }

    public void onMessage(String message) {
        try {
            LOGGER.info("Got message: {}", message);
            process(objectMapper.readValue(message, ApiInput.class));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void process(ApiInput input) {
        if (input.getPoints() != null) {
            this.pointList = input.getPoints();
            this.pointsReady = true;
        }

        if (input.getRoutes() != null) {
            this.routeList = input.getRoutes();
            this.routesReady = true;
        }

        if (input.getTraffic() != null) {
            parseTraffic(input.getTraffic());
            this.trafficReady = true;
        }

        if (input.getToken() != null) {
            this.token = input.getToken();
            parseCars(input.getCars());
        }
        if (input.getCar() != null) {
            onCarMessage(input);
        }

        if (input.getTeamsum() != null) {
            solver.setScore(input.getTeamsum());
        }

        if (isReady() && solver != null) {
            solver.solve();
        }
    }

    private void onCarMessage(ApiInput input) {
        Car car = this.getCarMap().get(input.getCar());
        //car.setCurrentLoad(input.getCarsum());
        car.reachTarget();
        car.updateLoad();
    }

    protected void parseTraffic(List<Traffic> trafficList) {
        for (Traffic traffic : trafficList) {
            String key = getTrafficKey(traffic.getA(), traffic.getB());
            trafficMap.put(key, traffic.getJam());
        }
    }

    private void restartWs() {
        try {
            synchronized (this) {
                if (!isWsCreated) {
                    LOGGER.info("Start socket");
                    ws = startWs(this, url);
                    ws.connect();
                    isWsCreated = true;
                }
            }
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private WebSocketClient startWs(final ExternalApi self, String initUrl) throws URISyntaxException {

        return new WebSocketClient(new URI(initUrl)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                LOGGER.info("Connected");
                try {
                    if (self.token == null) {
                        self.send(ApiOutput.init(teamName));
                    } else {
                        self.send(ApiOutput.reconnect(token));
                    }
                } catch (JsonProcessingException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }

            @Override
            public void onMessage(String s) {
                if(self.ws != this){
                    close();
                }
                String[] split = s.split("\n");
                for (String s1 : split) {
                    self.onMessage(s1);
                }

            }

            @Override
            public void onClose(int i, String s, boolean b) {
                LOGGER.error("Closed {} {} {}", i, s, b);
                if(self.ws == this) {
                    self.isWsCreated = false;
                reconnectToRace();
                }
            }

            @Override
            public void onError(Exception e) {
                self.isWsCreated = false;
                LOGGER.error(e.getMessage(), e);
                reconnectToRace();
            }

            private void reconnectToRace() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
                self.restartWs();
            }
        };
    }

    @Override
    public boolean isReady() {
        return trafficReady && routesReady && pointsReady && token != null;
    }

    @Override
    public double getTime(long id, long id1) {
        return trafficMap.get(getTrafficKey(id, id1));
    }
}
