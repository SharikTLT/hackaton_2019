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

    private volatile boolean ready = false;


    public Solver(Api api, GraphBuilder graphBuilder, PathFinder pathFinder) {
        this.api = api;
        this.graphBuilder = graphBuilder;
        this.pathFinder = pathFinder;
    }

    public void start() {
        while (!api.isReady()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        graph = graphBuilder.buildGraph(api);
        for (PointModel pointModel : graph.vertexSet()) {
            if(pointModel.getId() == 0){
                garagePoint = pointModel;
                garagePoint.setProcessed(true);
            }
            if(pointModel.getId() == 1){
                bankPoint = pointModel;
                bankPoint.setProcessed(true);
                bankPoint.setDropPoint(true);
            }

            if(garagePoint != null && bankPoint != null){
                break;
            }
        }
        for (Map.Entry<String, Car> entry : api.getCarMap().entrySet()) {
            entry.getValue().setCurrentVertex(garagePoint);
        }
        ready = true;
        solve();
    }

    public void solve() {
        if(!ready){
            return;
        }
        api.getCarMap().entrySet().stream().map(e -> e.getValue())
                .filter(c -> c.notRun())
                .forEach(c -> {
                    chooseNextPoint(c);
                });
    }

    private void chooseNextPoint(Car c) {
        PointModel next = pathFinder.findNext(this, c);
        if(next==null){
            LOGGER.info("No points");
        }
        try {
            next.setProcessed(true);
            c.setTargetVertext(next);
            api.goTo(c.getId(), next.getId(), false);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
