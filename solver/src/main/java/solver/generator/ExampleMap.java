package solver.generator;

import solver.model.Connections;
import solver.model.Map;
import solver.model.Point;

import java.util.List;

public class ExampleMap {
    /**
     * Тестовая карта
     * D  - 1 - 2
     * |    |
     * 3 -  4 - 5
     * |    |
     * 6    7

     */
    public static Map getTestMap() {
        Map map = new Map();
        map.addPoint(10,0);      //D
        map.addPoint(20, 5);    //1
        map.addPoint(30, 20);    //2

        map.addPoint(5, 10);     //3
        map.addPoint(15, 15);    //4
        map.addPoint(22, 20);    //5

        map.addPoint(0, 18);    //6
        map.addPoint(10, 22);   //7

        List<Point> points = map.getPoints();

        points.get(0).setDropPoint(true);
        points.get(0).setScore(0);

        setScoreToCollectPoints(points);

        buildConnections(points.get(0), points.get(1), points.get(3));
        buildConnections(points.get(1), points.get(2), points.get(4));
        buildConnections(points.get(3), points.get(4), points.get(6));
        buildConnections(points.get(4), points.get(5), points.get(7));

        return map;
    }

    private static void buildConnections(Point ... points) {
        for (int i = 1; i < points.length; i++) {
            points[0].addConnection(new Connections(points[0], points[i]));
        }
    }

    private static void setScoreToCollectPoints(List<Point> points) {
        points.stream()
                .filter(p -> !p.isDropPoint())
                .forEach(p -> p.setScore(100));
    }
}
