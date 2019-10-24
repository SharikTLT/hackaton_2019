package solver.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import solver.Solver;

public interface Api {
    void goTo(String car, Long target, boolean noMoney) throws JsonProcessingException;

    boolean isReady();

    java.util.List<solver.api.dto.Point> getPointList();

    java.util.List<solver.api.dto.Route> getRouteList();

    java.util.Map<String, Double> getTrafficMap();

    java.util.Map<String, Car> getCarMap();

    void setSolver(Solver solver);

    //Solver getSolver();

    double getTime(long id, long id1);

    //String getTrafficKey(long id1, long id2);
}
