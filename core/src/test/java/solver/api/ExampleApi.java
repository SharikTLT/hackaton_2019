package solver.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import solver.api.dto.ApiInput;
import solver.api.dto.Point;
import solver.api.dto.Route;
import solver.api.dto.Traffic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExampleApi extends AbstractApi {

    private List<Route> routeList;
    private List<Point> pointList;
    private Map<String, Double> trafficMap = new HashMap<>();
    private Map<String,Car> carMap = new HashMap<>();

    public ExampleApi() throws IOException {
        File res = new File("src/test/resources/example.json");
        ApiInput input = new ObjectMapper().readValue(res, ApiInput.class);
        this.routeList = input.getRoutes();
        this.pointList = input.getPoints();

        this.carMap.put("sb1", new Car("sb1", 0));

        for (Traffic traffic : input.getTraffic()) {
            String key = getTrafficKey(traffic.getA(), traffic.getB());
            trafficMap.put(key, traffic.getJam());
        }
    }

    @Override
    public void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public List<Point> getPointList() {
        return pointList;
    }

    @Override
    public List<Route> getRouteList() {
        return routeList;
    }

    @Override
    public Map<String, Double> getTrafficMap() {
        return trafficMap;
    }

    @Override
    public Map<String, Car> getCarMap() {
        return carMap;
    }
}
