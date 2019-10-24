package solver;

import solver.api.Api;
import solver.api.ExternalApi;
import solver.http.ExternalGraphBuilder;
import solver.pathfinder.RouterPathFinder;

public class Main {



    public static void main(String[] args) throws Exception {
        long maxLoad = 200_000L;

        String initUrl = "http://localhost:8080/race";
        //initUrl = "http://172.30.9.50:8080/race";
        String teamName = "Лоцманы";
        //teamName = "Отладка";
        Api api = new ExternalApi(teamName, initUrl);

        Solver solver = new Solver(api, new ExternalGraphBuilder(), new RouterPathFinder());
        api.setSolver(solver);
        solver.setTotalTime(100);

        solver.start(maxLoad);

        while (solver.isRun()){
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }


}
