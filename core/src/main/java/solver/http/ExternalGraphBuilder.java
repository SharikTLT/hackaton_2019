package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.api.Api;
import solver.model.EdgeModel;
import solver.model.PointModel;

import java.util.HashMap;
import java.util.Map;

public class ExternalGraphBuilder implements GraphBuilder {

    private Map<Long, PointModel> pointMap = new HashMap<>();

    @Override
    public Graph<PointModel, EdgeModel> buildGraph(Api api){
        Graph<PointModel, EdgeModel> graph = new DefaultUndirectedWeightedGraph<>(EdgeModel.class);

        api.getPointList().stream().forEach(p->{
            PointModel v = new PointModel(p.getP(), p.getMoney());
            graph.addVertex(v);
            pointMap.put(v.getId(), v);
        });

        pointMap.get(api.getPointList().get(1)).setDropPoint(true);

        api.getRouteList().stream().forEach(r->{
            graph.addEdge(pointMap.get(r.getA()), pointMap.get(r.getB()), new EdgeModel(r.getTime()));
        });

        return graph;
    }
}
