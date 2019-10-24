package solver.api;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Api {
    void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException;

    boolean isReady();

    java.util.List<solver.api.dto.Point> getPointList();

    java.util.List<solver.api.dto.Route> getRouteList();

    java.util.Map<String, Double> getTrafficMap();

    java.util.Map<String, Car> getCarMap();
}
