package solver.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.dto.*;
import solver.http.ApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ExternalApi implements Api {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExternalApi.class);

    public static final String WS_SOCKET = "ws://socket";

    public static final String INIT_URL = "/init";
    public static final String KEY_FORMAT = "%d_%d";

    private String teamName;

    private String token;

    private ApiClient apiClient = new ApiClient();

    private WebSocketClient ws;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private volatile List<Point> pointList;
    private volatile boolean pointsReady = false;

    @Getter
    private volatile List<Route> routeList;
    private volatile boolean routesReady = false;

    @Getter
    private volatile Map<String,Double> trafficMap;
    private volatile boolean trafficReady = false;

    @Getter
    private volatile Map<String, Car> carMap = new ConcurrentHashMap<>();

    private volatile Long level;

    public ExternalApi(String teamName) throws Exception {
        Optional<InitialResponse> res = apiClient.post(INIT_URL, new InitialRequest(teamName), InitialResponse.class);
        if (!res.isPresent()) {
            LOGGER.error("No answer from initial request");
            throw new Exception("no answer");
        }
        InitialResponse apiRes = res.get();
        parseCars(apiRes.getCars());
        this.level = apiRes.getLevel();
        this.token = apiRes.getToken();
        ws = startWs(this);
    }

    private void parseCars(List<String> cars) {
        for (String car : cars) {
            this.carMap.put(car, new Car(car, 0));
        }
    }

    @Override
    public void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException {
        carMap.get(car).target = target;
        if(noMoney){
            send(ApiOutput.goTo(target, car, true));
        }else {
            send(ApiOutput.goTo(target, car));
        }
    }

    public void send(ApiOutput input) throws JsonProcessingException {
        ws.send(objectMapper.writeValueAsString(input));
    }

    public void onMessage(String message) {
        try {
            process(objectMapper.readValue(message, ApiInput.class));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void process(ApiInput input) {
        if (input.getPoints() != null) {
            this.pointList = input.getPoints();
        }

        if (input.getRoutes() != null) {
            this.routeList = input.getRoutes();
        }

        if (input.getTraffic() != null) {
            parseTraffic(input.getTraffic());
        }
    }

    private void parseTraffic(List<Traffic> trafficList) {
        for (Traffic traffic : trafficList) {
            String key = getTrafficKey(traffic.getA(), traffic.getB());
            trafficMap.put(key, traffic.getJam());
        }
    }

    private String getTrafficKey(Long a, Long b) {
        return a < b ? String.format(KEY_FORMAT, a, b) : String.format(KEY_FORMAT, b, a);
    }

    private WebSocketClient startWs(final ExternalApi self) throws URISyntaxException {

        return new WebSocketClient(new URI(WS_SOCKET)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                LOGGER.info("Connected");
            }

            @Override
            public void onMessage(String s) {
                self.onMessage(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                LOGGER.error("Closed {} {} {}", i, s, b);
            }

            @Override
            public void onError(Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        };
    }

    @Override
    public boolean isReady() {
        return trafficReady && routesReady && pointsReady;
    }
}
