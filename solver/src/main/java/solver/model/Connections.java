package solver.model;

import java.util.Objects;

public class Connections {

    private Point pointA;
    private Point pointB;

    private long distance;

    private double feromoneAtoB;
    private double feromoneBtoA;

    public Connections(Point pointA, Point pointB) {
        if(pointA.getId() < pointB.getId()) {
            this.pointA = pointA;
            this.pointB = pointB;
        }else{
            this.pointA = pointB;
            this.pointB = pointA;
        }
        double xPow2 = Math.pow((pointB.getX()-pointA.getX()), 2);
        double yPow2 = Math.pow((pointB.getY()-pointA.getY()), 2);
        distance = new Double(Math.ceil(Math.sqrt(xPow2 + yPow2))).intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connections that = (Connections) o;
        return distance == that.distance &&
                Objects.equals(pointA, that.pointA) &&
                Objects.equals(pointB, that.pointB);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pointA, pointB, distance);
    }
}
