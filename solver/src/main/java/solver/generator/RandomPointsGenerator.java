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
        Point point = new Point(this.random.nextInt(xMax), this.random.nextInt(yMax));
        point.setScore(50+random.nextInt(100));
        return point;
    }

    public void generateMap(Map map, int numberOfElements, int xMax, int yMax){
        for (int i = 0; i < numberOfElements; i++) {
            map.addPoint(generate(xMax, yMax));
        }
        Point bank = map.getPoints().get(0);
        bank.setDropPoint(true);
        bank.setScore(0);
    }
}
