package solver.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import solver.api.dto.Point;
import solver.api.dto.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestApi implements Api {
    @Override
    public void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException {

    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public List<Point> getPointList() {
        return null;
    }

    @Override
    public List<Route> getRouteList() {
        return null;
    }

    @Override
    public Map<String, Double> getTrafficMap() {
        return null;
    }

    @Override
    public Map<String, Car> getCarMap() {
        Map<String, Car> carMap = new HashMap<>();
        carMap.put("sb1", new Car("sb1", 0));
        return carMap;
    }
}
