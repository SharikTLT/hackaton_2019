package solver.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private List<PathActions> actions = new LinkedList<>();

    private int totalTime = 0;

    public void addAction(PathActions action){
        actions.add(action);
    }

    public List<PathActions> getActions() {
        return actions;
    }

    public void setActions(List<PathActions> actions) {
        this.actions = actions;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
