package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.model.PointModel;

public class ExternalGraphBuilder implements GraphBuilder {

    @Override
    public Graph<PointModel, DefaultWeightedEdge> buildGraph(Object input){
        Graph<PointModel, DefaultWeightedEdge> graph = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        return graph;
    }
}
