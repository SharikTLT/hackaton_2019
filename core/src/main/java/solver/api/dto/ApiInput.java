package solver.api.dto;

import lombok.Getter;
import lombok.Setter;
import solver.model.PointModel;

import java.util.List;

public class ApiInput {
    @Getter @Setter
    List<Route> routes;

    @Getter @Setter
    List<Traffic> traffic;

    @Getter @Setter
    List<Point> points;

    @Getter @Setter
    Long point;

    @Getter @Setter
    String car;
}