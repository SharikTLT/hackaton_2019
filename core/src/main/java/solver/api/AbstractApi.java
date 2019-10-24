package solver.api;

import solver.Solver;
import solver.api.dto.Traffic;

import java.util.List;

public abstract class AbstractApi implements Api{

    public static final String KEY_FORMAT = "%d_%d";

    //@Override
    public String getTrafficKey(long a, long b) {
        return a < b ? String.format(KEY_FORMAT, a, b) : String.format(KEY_FORMAT, b, a);
    }

    @Override
    public void setSolver(Solver solver) {

    }

    @Override
    public double getTime(long id, long id1) {
        return 1;
    }
}
