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
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

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

    @Test
    public void test2(){
        int[] a ={6, 20, 4, 1, 25, 22, 9, 1, 40, 16, 1, 3, 34, 1, 26, 24, 1, 12, 11, 1, 27, 21, 1, 23, 38, 1, 17, 18, 19, 1, 35, 37, 32, 1, 29, 1, 30, 1, 14, 15, 2, 1, 13, 7, 39, 1, 28, 1, 8, 5, 10, 1, 33, 1, 36, 31, 1};
        int[] path = Arrays.stream(a).filter(i -> i != 1).sorted().toArray();
        assertEquals(path.length, 39);
    }
}