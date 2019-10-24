package solver;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;
import solver.api.Car;
import solver.http.ExternalGraphBuilder;
import solver.http.GraphBuilder;
import solver.model.EdgeModel;
import solver.model.PointModel;

public class Solver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Solver.class);

    private Api api;

    private Graph<PointModel, EdgeModel> graph;

    private GraphBuilder graphBuilder;

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

    }
}
