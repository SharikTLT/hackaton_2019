package solver.pathfinder;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

public abstract class AbstractPathFinder implements PathFinder {
    private volatile ShortestPathAlgorithm shortPathAlgo;

    public void setUpAlgo(Solver solver){
        if(shortPathAlgo == null) {
            shortPathAlgo = new DijkstraShortestPath(solver.getGraph());
        }
    }

    public Long getLengthOfShortest(PointModel connected, PointModel bankPoint) {
        GraphPath<PointModel, EdgeModel> path = shortPathAlgo.getPath(connected, bankPoint);
        return new Double(path.getEdgeList().stream()
                .mapToDouble(e -> e.getTime())
                .sum()).longValue();
    }

    public Long getLengthOfWorstShortest(PointModel connected, PointModel bankPoint) {
        GraphPath<PointModel, EdgeModel> path = shortPathAlgo.getPath(connected, bankPoint);
        return new Double(path.getEdgeList().stream()
                .mapToDouble(e -> e.getWorstTime())
                .sum()).longValue();
    }

    public GraphPath getShortest(Solver solver, Car car) {

        return shortPathAlgo.getPath(car.getCurrentVertex(), solver.getBankPoint());
    }
}
