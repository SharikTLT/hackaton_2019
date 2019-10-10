package solver.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point {
    private int id;
    private int x;
    private int y;
    private int processingTime = 10;
    private int score;
    private boolean dropPoint;

    private Set<Connections> connections = new HashSet<>();

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addConnection(Connections connection) {
        connections.add(connection);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Set<Connections> getConnections() {
        return connections;
    }

    public void setConnections(Set<Connections> connections) {
        this.connections = connections;
    }

    public boolean isDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(boolean dropPoint) {
        this.dropPoint = dropPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id == point.id &&
                x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, x, y);
    }
}
