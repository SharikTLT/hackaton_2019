package solver.api;

import solver.Solver;
import solver.api.dto.Traffic;

import java.util.List;

public abstract class AbstractApi implements Api{


    protected String getTrafficKey(Long a, Long b) {
        return a < b ? String.format(ExternalApi.KEY_FORMAT, a, b) : String.format(ExternalApi.KEY_FORMAT, b, a);
    }

    @Override
    public void setSolver(Solver solver) {

    }

    @Override
    public double getTime(long id, long id1) {
        return 1;
    }
}
