package solver.model;

import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.DefaultWeightedEdge;
import solver.api.Api;

public class EdgeModel extends DefaultWeightedEdge {
    @Getter
    @Setter
    private double time;

    private PointModel a;

    private PointModel b;

    private Api api;

    public EdgeModel(double time, PointModel a, PointModel b) {
        this.time = time;
        this.a = a;
        this.b = b;
    }

    public EdgeModel(long time, PointModel a, PointModel b, Api api) {
        this.api = api;
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

    public double getTime(){
        return api != null ? this.time *  api.getTime(a.getId(),b.getId()) : this.time;
    }
}
