package solver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;

public class Solver {

    private static  final Logger LOGGER = LoggerFactory.getLogger(Solver.class);

    private Api api;

    public Solver(Api api) {
        this.api = api;
    }

    public void start(){
        while (!api.isReady()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
