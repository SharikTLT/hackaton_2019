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

    public Point getConnected(Point current){
        return current.equals(pointA) ? pointB : pointA;
    }

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public double getFeromoneAtoB() {
        return feromoneAtoB;
    }

    public void setFeromoneAtoB(double feromoneAtoB) {
        this.feromoneAtoB = feromoneAtoB;
    }

    public double getFeromoneBtoA() {
        return feromoneBtoA;
    }

    public void setFeromoneBtoA(double feromoneBtoA) {
        this.feromoneBtoA = feromoneBtoA;
    }
}
