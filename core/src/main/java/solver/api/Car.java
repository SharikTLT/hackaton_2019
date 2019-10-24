package solver.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.GraphPath;
import solver.model.PointModel;

@EqualsAndHashCode
public class Car {

    @Getter
    @Setter
    String id;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    volatile long currentPoint;

    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    volatile long target;

    @Getter
    @Setter
    volatile long currentLoad = 0;

    @Getter
    @Setter
    volatile long maxLoad = 1_000_000l;

    @Getter
    @Setter
    volatile PointModel currentVertex;

    @Getter
    volatile boolean isGreed = true;

    @Getter
    volatile boolean isNeedDrop = false;

    private long greedFactor = 2;

    @Getter
    @Setter
    volatile private GraphPath plannedPath;


    public Car(String id) {
        this.id = id;
    }

    public Car(String car, long i) {
        this.id = car;
        this.currentPoint = i;
        this.target = i;
    }

    public boolean notRun() {
        return currentPoint != target;
    }


    public void load(long value) {
        currentLoad += value;
        if (currentLoad > maxLoad / greedFactor) {
            isGreed = false;
        }
        if (currentLoad == maxLoad) {
            isNeedDrop = true;
        }
    }

    public void drop() {
        currentLoad = 0;
        isGreed = true;
        isNeedDrop = false;
    }
}
