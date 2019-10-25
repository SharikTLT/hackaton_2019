package solver.pathfinder;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GreedPathFinder extends AbstractPathFinder {


    /**
     * Выбираем ближайшую точку
     *
     * @param solver
     * @param car
     * @return
     */
    @Override
    public PointModel findNext(Solver solver, Car car) {
        setUpAlgo(solver);


        if(solver.getPriority().size() > 0){
            Optional<PointModel> first = solver.getPriority().stream()
                    .sorted(Comparator.comparing((p) -> getLengthOfShortest(car.getCurrentVertex(), p)))
                    .findFirst();
            return first.get();
        }

        List<EdgeModel> edges = solver.getGraph().edgesOf(car.getCurrentVertex()).stream()
                .filter((EdgeModel e)-> !e.getConnected(car.getCurrentVertex()).isProcessed())
                .sorted(Comparator.comparing((e -> e.getTime())))
                .limit(20)
                .sorted(Comparator.comparing((EdgeModel e)->e.getConnected(car.getCurrentVertex()).getMoney()).reversed())
                .collect(Collectors.toList());

        if(solver.isShuffle()) {
            Collections.shuffle(edges);
        }

        if(edges.size() == 0){
            return null;
        }
        EdgeModel edgeModel = edges.get(0);
        return edgeModel.getConnected(car.getCurrentVertex());
    }
}
