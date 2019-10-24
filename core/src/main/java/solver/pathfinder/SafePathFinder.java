package solver.pathfinder;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import solver.Solver;
import solver.api.Car;
import solver.api.dto.Point;
import solver.model.EdgeModel;
import solver.model.PointModel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SafePathFinder implements PathFinder {

    private ShortestPathAlgorithm shortPathAlgo;

    @Override
    public PointModel findNext(Solver solver, Car car) {
        if (isNeedToDrop(car)) {
            return getShortPathTo(solver, car);
        }
        shortPathAlgo = new DijkstraShortestPath(solver.getGraph());

        Object[] candidates = solver.getGraph().edgesOf(car.getCurrentVertex()).stream()

                .map(e -> e.getConnected(car.getCurrentVertex()))
                .filter(p -> !p.isProcessed())
                .sorted(Comparator.comparing(e -> getLengthOfShortest(e, solver.getBankPoint())))
                .toArray();
        Object[] edges = Arrays.stream(candidates)
                .filter(p->car.canLoad(((PointModel)p).getMoney()))
                .toArray();
        if(edges.length == 0){
            return getShortPathTo(solver, car);
        }
        PointModel connected = ((PointModel) edges[0]);
        return connected;
    }

    private Long getLengthOfShortest(PointModel connected, PointModel bankPoint) {
        GraphPath<PointModel, EdgeModel> path = shortPathAlgo.getPath(connected, bankPoint);
        return new Double(path.getEdgeList().stream()
                .mapToDouble(e -> e.getTime())
                .sum()).longValue();
    }

    private PointModel getShortPathTo(Solver solver, Car car) {
        if (car.getPlannedPath() == null) {
            GraphPath path = getShortest(solver, car);
            car.setPlannedPath(path);
        }

        List<PointModel> vertexList = car.getPlannedPath().getVertexList();
        boolean foundCurr = false;
        for (PointModel vert : vertexList) {
            if (foundCurr) {
                return vert;
            }
            if (vert == car.getCurrentVertex()) {
                foundCurr = true;
            }
        }
        return solver.getBankPoint();
    }

    private GraphPath getShortest(Solver solver, Car car) {

        return shortPathAlgo.getPath(car.getCurrentVertex(), solver.getBankPoint());
    }


    private boolean isNeedToDrop(Car car) {
        return car.isNeedDrop(); //TODO учет времени
    }
}
