package solver.model;

import java.util.Objects;

public class PointModel {
    private int id;
    private int x;
    private int y;
    private int processingTime = 10;
    private int score;
    private boolean dropPoint;


    public PointModel() {
    }

    public PointModel(int x, int y) {
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
        PointModel pointModel = (PointModel) o;
        return id == pointModel.id &&
                x == pointModel.x &&
                y == pointModel.y;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
