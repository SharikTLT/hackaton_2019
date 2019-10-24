package solver.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class InitialRequest {

    @Getter @Setter @NonNull String team;

}
