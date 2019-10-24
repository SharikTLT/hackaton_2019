package solver;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.jgrapht.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;
import solver.api.Car;
import solver.http.GraphBuilder;
import solver.model.EdgeModel;
import solver.model.PointModel;
import solver.pathfinder.PathFinder;

public class Solver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Solver.class);

    private Api api;

    @Getter
    private Graph<PointModel, EdgeModel> graph;

    private GraphBuilder graphBuilder;

    private PathFinder pathFinder;

    @Getter
    private PointModel bankPoint;

    public Solver(Api api, GraphBuilder graphBuilder) {
        this.api = api;
        this.graphBuilder = graphBuilder;
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
        graph.vertexSet().stream().filter(p -> p.getId() == 1)
                .forEach(p -> bankPoint = p);
        solve();
    }

    private void solve() {
        api.getCarMap().entrySet().stream().map(e -> e.getValue())
                .filter(c -> c.notRun())
                .forEach(c -> {
                    chooseNextPoint(c);
                });
    }

    private void chooseNextPoint(Car c) {
        PointModel next = pathFinder.findNext(this, c);
        try {
            api.goTo(c.getId(), next.getId(), false);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
