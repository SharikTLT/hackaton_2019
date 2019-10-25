package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;
import solver.api.dto.Point;
import solver.model.EdgeModel;
import solver.model.PointModel;

import java.util.HashMap;
import java.util.Map;

public class ExternalGraphBuilder implements GraphBuilder {

    private final static Logger LOGGER  = LoggerFactory.getLogger(ExternalGraphBuilder.class);

    private Map<Long, PointModel> pointMap = new HashMap<>();

    @Override
    public Graph<PointModel, EdgeModel> buildGraph(Api api) {
        Graph<PointModel, EdgeModel> graph = new DefaultUndirectedWeightedGraph<>(EdgeModel.class);

        api.getPointList().stream().forEach(p -> {
            PointModel v = new PointModel(p.getP(), p.getMoney());
            graph.addVertex(v);
            pointMap.put(v.getId(), v);
        });

        Point bankPointVar = api.getPointList().get(1);
        PointModel bankModel = pointMap.get(bankPointVar.getP());
        bankModel.setDropPoint(true);
        bankModel.setProcessed(true);


        api.getRouteList().stream().forEach(r -> {
            try {
                PointModel a = pointMap.get(r.getA());
                PointModel b = pointMap.get(r.getB());
                if(a != null && b != null) {
                    EdgeModel e = new EdgeModel(r.getTime(), a, b, api);
                    graph.addEdge(a, b, e);
                }else{
                    LOGGER.error("Invalid route, missing vertex: {} {}  {}", r.getA(), r.getB(), r.getTime());
                }
            }catch (Exception e){
                LOGGER.error("Wrong route: {} {}  {} - {}", r.getA(), r.getB(), r.getTime(), e.getMessage());
            }
        });

        return graph;
    }
}
