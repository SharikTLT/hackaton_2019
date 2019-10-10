package solver.generator;

import solver.model.Map;
import solver.model.Point;

import java.util.Random;

public class RandomPointsGenerator {
    private Random random;

    public RandomPointsGenerator() {
        this.random = new Random();
    }

    public RandomPointsGenerator(long seed){
        this.random = new Random(seed);
    }

    public Point generate(int xMax, int yMax){
        return new Point(this.random.nextInt(xMax), this.random.nextInt(yMax));
    }

    public void generateMap(Map map, int numberOfElements, int xMax, int yMax){
        for (int i = 0; i < numberOfElements; i++) {
            map.addPoint(generate(xMax, yMax));
        }
    }
}
