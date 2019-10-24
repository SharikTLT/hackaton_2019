package solver;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solver.api.Api;
import solver.api.ExternalApi;
import solver.http.ExternalGraphBuilder;
import solver.pathfinder.RouterPathFinder;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {



    public static void main(String[] args) throws Exception {

        Api api = new ExternalApi("Лоцманы", "http://localhost:8080/race");

        Solver solver = new Solver(api, new ExternalGraphBuilder(), new RouterPathFinder());
        api.setSolver(solver);
        solver.start();

        while (solver.isRun()){
            try {
                Thread.sleep(1000);
            }catch (Exception e){

            }
        }
    }


}
