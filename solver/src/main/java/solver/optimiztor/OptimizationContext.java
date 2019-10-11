package solver.optimiztor;

import solver.model.Connections;
import solver.model.Map;
import solver.model.Point;

public class OptimizationContext {

    private Map localMap;

    private boolean isCollectMode;

    private Point currentPoint;

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    public boolean isCollectMode() {
        return isCollectMode;
    }

    public void setCollectMode(boolean collectMode) {
        isCollectMode = collectMode;
    }

    public Map getLocalMap() {
        return localMap;
    }

    public void setLocalMap(Map localMap) {
        this.localMap = localMap;
    }
}
