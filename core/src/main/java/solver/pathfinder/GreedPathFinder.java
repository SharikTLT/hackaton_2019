package solver.pathfinder;

import org.jgrapht.Graph;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

import java.util.Comparator;
import java.util.Optional;

public class GreedPathFinder implements PathFinder {

    /**
     * Выбираем ближайшую точку
     *
     * @param solver
     * @param car
     * @return
     */
    @Override
    public PointModel findNext(Solver solver, Car car) {
        Optional<EdgeModel> first = solver.getGraph().edgesOf(car.getCurrentVertex()).stream()
                .filter((EdgeModel e)-> !e.getConnected(car.getCurrentVertex()).isProcessed())
                .sorted(Comparator.comparing((e -> e.getTime())))
                .limit(1)
                .findFirst();

        if(!first.isPresent()){
            return null;
        }
        EdgeModel edgeModel = first.get();
        return edgeModel.getConnected(car.getCurrentVertex());
    }
}
