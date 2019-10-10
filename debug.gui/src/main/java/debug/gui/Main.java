package debug.gui;

import solver.generator.RandomPointsGenerator;
import solver.model.Map;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.awt.noerasebackground", "true");
        Map map = new Map();
        RandomPointsGenerator randomPointsGenerator = new RandomPointsGenerator(123L);
        randomPointsGenerator.generateMap(map, 5, 10, 10);
        map.calculateMap();

        RouteCanvas routeCanvas = new RouteCanvas(map);
        JFrame frame = getjFrame();
        frame.add(routeCanvas);
        boolean run = false;
        while (run){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                run = false;
            }
            synchronized (map.getPoints()) {
                randomPointsGenerator.generateMap(map, 500, 780, 750);
            }
            routeCanvas.repaint();
        }
    }

    private static JFrame getjFrame() {
        JFrame frame = new JFrame("Debug GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        return frame;
    }
}
