package solver.api.dto;

import lombok.Getter;
import lombok.Setter;

public class PointsUpdate {

    @Getter
    @Setter
    Long p;

    @Getter
    @Setter
    Boolean disable;

    @Getter
    @Setter
    Boolean priority;
}
