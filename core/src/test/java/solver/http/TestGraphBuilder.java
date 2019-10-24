package solver.http;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import solver.api.Api;
import solver.model.EdgeModel;
import solver.model.PointModel;

public class TestGraphBuilder implements GraphBuilder {
    @Override
    public Graph<PointModel, EdgeModel> buildGraph(Api api) {
        Graph<PointModel, EdgeModel> graph = new DefaultUndirectedWeightedGraph<>(EdgeModel.class);

        PointModel pointMode0 = new PointModel(0,0);
        PointModel pointMode1 = new PointModel(1,0);
        PointModel pointMode2 = new PointModel(2, 400_000);
        PointModel pointMode3 = new PointModel(3,500_000);
        PointModel pointMode4 = new PointModel(4,300_000);
        PointModel pointMode5= new PointModel(5,600_000);

        graph.addVertex(pointMode0); //техническая точка старта
        graph.addVertex(pointMode1); //техническая точка выгрузки

        graph.addVertex(pointMode2);
        graph.addVertex(pointMode3);
        graph.addVertex(pointMode4);
        graph.addVertex(pointMode5);

        graph.addEdge(pointMode0, pointMode1, new EdgeModel(30, pointMode0, pointMode1));
        graph.addEdge(pointMode0, pointMode2, new EdgeModel(10, pointMode0, pointMode2));
        graph.addEdge(pointMode0, pointMode3, new EdgeModel(14, pointMode0, pointMode3));
        graph.addEdge(pointMode0, pointMode4, new EdgeModel(12, pointMode0, pointMode4));

        graph.addEdge(pointMode1, pointMode2, new EdgeModel(20, pointMode1, pointMode2));
        graph.addEdge(pointMode1, pointMode3, new EdgeModel(11, pointMode1, pointMode3));
        graph.addEdge(pointMode1, pointMode4, new EdgeModel(13, pointMode1, pointMode4));
        graph.addEdge(pointMode1, pointMode5, new EdgeModel(40, pointMode1, pointMode5));

        graph.addEdge(pointMode2, pointMode3, new EdgeModel(5, pointMode2, pointMode3));
        graph.addEdge(pointMode2, pointMode5, new EdgeModel(10, pointMode2, pointMode5));

        graph.addEdge(pointMode3, pointMode4, new EdgeModel(15, pointMode3, pointMode4));

        graph.addEdge(pointMode4, pointMode5, new EdgeModel(16, pointMode4, pointMode5));

        return graph;
    }
}
