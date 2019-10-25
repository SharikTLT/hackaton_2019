package solver.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.GraphPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.model.PointModel;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@EqualsAndHashCode
public class Car {

    private static final Logger LOGGER = LoggerFactory.getLogger(Car.class);

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
    @Setter
    volatile PointModel targetVertext;

    @Getter
    volatile boolean isGreed = true;

    @Getter
    volatile boolean isNeedDrop = false;

    @Getter
    @Setter
    volatile boolean isParked = false;

    @Getter
    @Setter
    volatile long spendedTime = 0;

    @Getter
    @Setter
    volatile long travelTime;

    @Getter
    volatile long delivered;

    private long greedFactor = 2;

    @Getter
    @Setter
    volatile private GraphPath plannedPath;

    volatile private double doubleTime;

    volatile ConcurrentLinkedQueue<Long> path = new ConcurrentLinkedQueue<Long>();


    public Car(String id) {
        this.id = id;
    }

    public Car(String car, long i) {
        this.id = car;
        this.currentPoint = i;
        this.target = i;
    }

    public boolean notRun() {
        return currentPoint == target;
    }


    public void load(long value) {
        currentLoad += value;
        updateLoad();
    }

    public void updateLoad(){
        if (currentLoad > maxLoad / greedFactor) {
            isGreed = false;
        }
        if (currentLoad >= maxLoad) {
            isNeedDrop = true;
        }
    }

    public void drop() {
        delivered+=currentLoad;
        currentLoad = 0;
        isGreed = true;
        isNeedDrop = false;
    }

    public void reachTarget(Double duration) {
        path.add(target);
        currentPoint = target;
        currentVertex = targetVertext;
        currentLoad += targetVertext.getMoney();
        if(duration != null){
            doubleTime += duration;
        }else {
            spendedTime += travelTime;
        }
        if(currentVertex.isDropPoint()){
            drop();
        }
        LOGGER.info("Spended: {}, Delivered: {}, Path: {}", getSpendedTime(), delivered, path.toString());
        updateLoad();
    }

    public boolean canLoad(long money) {
        return currentLoad + money <= maxLoad;
    }

    public long getSpendedTime(){
        if(doubleTime > 0){
            return Double.valueOf(Math.ceil(doubleTime)).longValue();
        }
        return spendedTime;
    }
}
