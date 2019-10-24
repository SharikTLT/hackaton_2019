package solver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class PointModel {
    @Getter
    private long id;

    @Getter
    private volatile long money;

    @Getter
    @Setter
    private boolean processed;

    private int processingTime = 10;

    @Getter
    @Setter
    private boolean dropPoint;


    public PointModel() {
    }

    public PointModel(long id, long money){
        this.id = id;
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointModel pointModel = (PointModel) o;
        return id == pointModel.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
