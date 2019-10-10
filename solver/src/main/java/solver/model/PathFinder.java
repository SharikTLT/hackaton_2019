package solver.model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class PathFinder {
    private Map map;

    private double a = 1;
    private double b = 1;
    private int antNumber = 10;

    private Set<Point> visited = new HashSet<>();
    private Random random = new Random();

    public Path getOptimizedPath(Point startPoint) {
        map.getPoints().stream()
                .map(p -> p.getConnections())
                .flatMap(s -> s.stream())
                .forEach(c -> {
                    c.setFeromoneAtoB(1);
                    c.setFeromoneBtoA(1);
                });

        Path bestPath = getEliteAntPath(startPoint);
        for (int i = 0; i < antNumber; ++i) {
            Path candidatePath = getRegularAntPath(startPoint);
        }

    }

    private Path getRegularAntPath(Point startPoint) {
        return getAntPath(a, b, startPoint);
    }

    private Path getEliteAntPath(Point startPoint) {
        return getAntPath(a, 0, startPoint);
    }

    private Path getAntPath(double a, double b, Point startPoint) {
        Path path = new Path();
        path.addAction(new PathActions(PathActions.Type.START, startPoint));
        visited.add(startPoint);

        boolean run = true;
        Point currentPoint = startPoint;
        while (run) {
            PathActions action = getNextAction(a, b, currentPoint);
            currentPoint = action.getPoint();
            if (action.getType() == PathActions.Type.END) ;
        }

    }


    /**
     * n = 1/time
     *
     * @param a
     * @param b
     * @param currentPoint
     * @return
     */
    private PathActions getNextAction(double a, double b, Point currentPoint) {
        float select = random.nextFloat();
        double sum = 0;

        List<Connections> shuffled = currentPoint.getConnections().stream()
                .filter(notVisitedPoints(currentPoint))
                .collect(Collectors.toList());
        Collections.shuffle(shuffled);
        if(shuffled.size() == 0){
            return new PathActions(PathActions.Type.END, currentPoint, 0);
        }


        double totalScore = 0;

        java.util.Map<Connections, Double> scores = new HashMap<>();

        for (Connections connection : shuffled) {
            double score = Math.pow(1/connection.getDistance(), b) * Math.pow(connection.getConnected(currentPoint).getScore(), b);
            totalScore += score;
            scores.put(connection, score);
        }

        for (Connections connections : shuffled) {
            sum += scores.get(connections) / totalScore;
            if(sum < select){
                return new PathActions(PathActions.Type.GO, connections.getConnected(currentPoint));
            }
        }

        return null;
    }

    private Predicate<Connections> notVisitedPoints(Point currentPoint) {
        return c -> !visited.contains(c.getConnected(currentPoint));
    }



    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
