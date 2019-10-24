package solver.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
}
