package solver.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class EdgeModel extends DefaultWeightedEdge {
    private double time;

    public EdgeModel(long time) {
        this.time = (double) time;
    }
}
