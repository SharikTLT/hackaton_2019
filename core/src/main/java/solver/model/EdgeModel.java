package solver.model;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.DefaultWeightedEdge;

public class EdgeModel extends DefaultWeightedEdge {
    @Getter
    @Setter
    private double time;

    private PointModel a;

    private PointModel b;

    public EdgeModel(long time, PointModel a, PointModel b) {
        this.time = (double) time;
        if (a.getId() < b.getId()) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
    }

    public PointModel getConnected(PointModel curr) {
        return curr.equals(a) ? b : a;
    }
}
