package solver.api;

import solver.api.dto.Traffic;

import java.util.List;

public abstract class AbstractApi implements Api{


    protected String getTrafficKey(Long a, Long b) {
        return a < b ? String.format(ExternalApi.KEY_FORMAT, a, b) : String.format(ExternalApi.KEY_FORMAT, b, a);
    }
}
