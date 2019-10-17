package solver.model;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {

    private double weight;

    public Edge(double weight) {
        this.weight = weight;
    }

    @Override
    protected double getWeight() {
        return super.getWeight();
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +", "+super.toString()+
                '}';
    }
}
