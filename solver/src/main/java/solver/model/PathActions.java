package solver.model;

public class PathActions {

    private Type type;

    private Point point;

    private int usedTime;

    public PathActions(Type start, Point point){
        this(start, point, 0);
    }

    public PathActions(Type start, Point point, int usedTime) {
        this.type = start;
        this.point = point;
        this.usedTime = usedTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(int usedTime) {
        this.usedTime = usedTime;
    }

    public enum Type {
        GO,
        COLLECT,
        START,
        END
    }
}

