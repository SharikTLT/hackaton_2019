package solver.pathfinder;

import org.jgrapht.Graph;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

public interface PathFinder {
    PointModel findNext(Solver solver, Car car);
}
