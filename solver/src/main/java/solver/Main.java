package solver;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.model.Edge;
import solver.model.Point;

public class Main {
    public static void main(String[] args) {
        Graph<Point, DefaultWeightedEdge> g = new DefaultUndirectedWeightedGraph<>(DefaultWeightedEdge.class);

        Point[] v = {new Point(0,0), new Point(0,1), new Point(0,2), new Point(0,3), new Point(0,4)};
        for (Point point : v) {
            g.addVertex(point);
        }

        for (int i = 1; i < v.length; i++) {
            g.addEdge(v[i],v[i-1], new Edge(i));
        }

        g.edgeSet().stream().forEach(e->{
            System.out.println(e);
        });
    }
}
