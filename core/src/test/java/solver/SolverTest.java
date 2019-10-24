package solver;

import org.testng.annotations.Test;
import solver.api.Api;
import solver.api.ExampleApi;
import solver.api.TestApi;
import solver.http.ExternalGraphBuilder;
import solver.http.TestGraphBuilder;
import solver.pathfinder.PathFinder;
import solver.pathfinder.RouterPathFinder;

import java.io.IOException;

public class SolverTest {
    public static void main(String[] args) {
        Solver solver = new Solver(new TestApi(), new TestGraphBuilder(), new RouterPathFinder());
        solver.start();
    }

    @Test
    public void test() throws IOException {
        Api api = new ExampleApi();
        Solver solver = new Solver(api, new ExternalGraphBuilder(), new RouterPathFinder());
        solver.start();
        // Solver solver = new Solver();

    }
}