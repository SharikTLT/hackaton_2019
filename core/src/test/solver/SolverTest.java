package solver;

import solver.api.TestApi;
import solver.http.TestGraphBuilder;

public class SolverTest {
    public static void main(String[] args) {
        Solver solver = new Solver(new TestApi(), new TestGraphBuilder());
        solver.start();
    }
}