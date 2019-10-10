package solver.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.maxBy;

public class Map {
    private volatile  int idSource;
    private List<Point> points = new ArrayList<>();
    private Point[][] locations;
    private int defaultRadius = 100;
    private int width;
    private int height;


    public void addPoint(int x, int y){
        this.addPoint(new Point(x, y));

    }

    public void addPoint(Point point){
        point.setId(++idSource);
        points.add(point);
    }


    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void reset(){
        synchronized (this.points) {
            this.points.clear();
            this.idSource = 0;
        }
    }

    public void calculateMap(){
        builLocations();
        points.stream()
                .forEach(point -> findConnections(point));
    }

    public void findConnections(Point point){
        int radius = defaultRadius;
        findConnections(point, point.getX()-radius, point.getY()-radius, point.getX()+radius, point.getY()+radius);
    }

    public void findConnections(Point point, int minX, int minY, int maxX, int maxY){
        for(int x = Math.max(0, minX); x < Math.min(width, maxX); ++x){
            for(int y = Math.max(0, minY); y < Math.min(height, maxY); ++y){
                Point candidate = locations[x][y];
                if(candidate != null && !point.equals(candidate) ){
                    Connections connection = new Connections(point, candidate);
                    point.addConnection(connection);
                    candidate.addConnection(connection);
                }
            }
        }
    }

    public void builLocations(){
        Optional<Point> maxX = points.stream()
                .collect(maxBy(Comparator.comparing(Point::getX)));
        Optional<Point> maxY = points.stream()
                .collect(maxBy(Comparator.comparing(Point::getY)));

        width = maxX.get().getX()+1;
        height = maxY.get().getY()+1;
        locations = new Point[width][height];
        points.stream().forEach(p -> locations[p.getX()][p.getY()] = p);
    }


}
