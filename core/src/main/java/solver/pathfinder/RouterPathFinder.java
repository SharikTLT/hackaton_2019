package solver.pathfinder;

import org.jgrapht.Graph;
import solver.Solver;
import solver.api.Car;
import solver.model.EdgeModel;
import solver.model.PointModel;

public class RouterPathFinder extends AbstractPathFinder{

    private PathFinder greed = new GreedPathFinder();
    private PathFinder safe = new SafePathFinder();

    @Override
    public PointModel findNext(Solver solver, Car car) {
        setUpAlgo(solver);
        PointModel next = car.isGreed() ? greed.findNext(solver, car) : safe.findNext(solver, car);
        if(next!= null && cantGetBack(solver, car, next)){
            if(car.getCurrentVertex().isDropPoint()){
                return null;
            }
           return solver.getBankPoint();
        }
        return next;
    }

    private boolean cantGetBack(Solver solver, Car car, PointModel next) {
        long toPoint = getLengthOfWorstShortest(car.getCurrentVertex(), next);
        long toBank = getLengthOfWorstShortest(next, solver.getBankPoint());
        return toPoint + toBank > solver.getTimeExist()*(0.9);
    }
}
