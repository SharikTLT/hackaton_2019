package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.model.PointModel;

public interface GraphBuilder {
    Graph<PointModel, DefaultWeightedEdge> buildGraph(Object input);
}
