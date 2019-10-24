package solver.pathfinder;

import org.jgrapht.Graph;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

public class RouterPathFinder implements PathFinder {

    private PathFinder greed = new GreedPathFinder();
    private PathFinder safe = new SafePathFinder();

    @Override
    public PointModel findNext(Solver solver, Car car) {
        return car.isGreed() ? greed.findNext(solver, car) : safe.findNext(solver, car);
    }
}
