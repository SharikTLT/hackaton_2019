package solver;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;
import solver.api.Car;
import solver.http.GraphBuilder;
import solver.model.EdgeModel;
import solver.model.PointModel;
import solver.pathfinder.PathFinder;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class Solver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Solver.class);

    private Api api;

    @Getter
    private Graph<PointModel, EdgeModel> graph;

    private GraphBuilder graphBuilder;

    private PathFinder pathFinder;

    @Getter
    private PointModel bankPoint;

    private PointModel garagePoint;

    @Getter
    @Setter
    private boolean run = true;

    @Getter
    @Setter
    private volatile long score;

    @Getter
    @Setter
    private volatile long timeExist;

    @Getter
    @Setter
    private volatile long totalTime;

    @Getter
    @Setter
    private volatile Map<Long, PointModel> pointMap = new ConcurrentHashMap<>();

    @Getter
    @Setter
    private volatile Set<PointModel> priority = new ConcurrentSkipListSet<>();

    private volatile boolean ready = false;

    @Getter
    @Setter
    private volatile boolean isShuffle = false;


    public Solver(Api api, GraphBuilder graphBuilder, PathFinder pathFinder) {
        this.api = api;
        this.graphBuilder = graphBuilder;
        this.pathFinder = pathFinder;
    }

    public void start(long maxLoad) {
        while (!api.isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        graph = graphBuilder.buildGraph(api);
        for (PointModel pointModel : graph.vertexSet()) {
            pointMap.put(pointModel.getId(), pointModel);
            if (pointModel.getId() == 0) {
                garagePoint = pointModel;
                garagePoint.setProcessed(true);
            }
            if (pointModel.getId() == 1) {
                bankPoint = pointModel;
                bankPoint.setProcessed(true);
                bankPoint.setDropPoint(true);
            }

            if (garagePoint != null && bankPoint != null) {
                break;
            }
        }
        for (Map.Entry<String, Car> entry : api.getCarMap().entrySet()) {
            Car car = entry.getValue();
            car.setMaxLoad(maxLoad);
            car.setCurrentVertex(garagePoint);
        }
        ready = true;
        solve();
    }

    public synchronized void solve() {
        if (!ready) {
            return;
        }
        api.getCarMap().entrySet().stream().map(e -> e.getValue())
                .filter(c -> c.notRun() && !c.isParked())
                .forEach(c -> {
                    chooseNextPoint(c);
                });
    }

    private void chooseNextPoint(Car c) {
        PointModel next = pathFinder.findNext(this, c);
        if (next == null) {
            LOGGER.info("No points, park car {} with result: {} time: {}", c.getId(), c.getDelivered(), c.getSpendedTime());
            c.setTravelTime(0);
            c.setParked(true);
            return;
        }
        try {
            next.setProcessed(true);
            if (priority.contains(next)) {
                priority.remove(next);
            }
            c.setTargetVertext(next);
            EdgeModel edge = graph.getEdge(c.getCurrentVertex(), c.getTargetVertext());
            double ceiled = Math.ceil(edge.getTime());
            c.setTravelTime(Double.valueOf(ceiled).longValue());
            api.goTo(c.getId(), next.getId(), false);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
