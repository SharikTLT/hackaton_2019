package solver.optimiztor;

import solver.model.Path;
import solver.model.PathActions;
import solver.model.Point;

public class ChooseWay {

    public PathActions getNext(OptimizationContext context){

        return PathActions.stop(context.getCurrentPoint());
    }
}
