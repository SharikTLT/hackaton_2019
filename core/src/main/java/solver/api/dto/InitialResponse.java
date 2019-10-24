package solver.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class InitialResponse {

    @Getter
    @Setter
    String token;

    @Getter
    @Setter
    List<String> cars;

    @Getter
    @Setter
    Long level;

}
