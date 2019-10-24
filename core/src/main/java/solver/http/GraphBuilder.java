package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.api.Api;
import solver.model.EdgeModel;
import solver.model.PointModel;

public interface GraphBuilder {
    Graph<PointModel, EdgeModel> buildGraph(Api api);
}
