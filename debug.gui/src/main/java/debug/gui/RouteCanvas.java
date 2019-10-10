package debug.gui;

import solver.model.Map;
import solver.model.Point;

import javax.swing.*;
import java.awt.*;

public class RouteCanvas extends JPanel {

    private boolean drawLinesSequental = false;

    private Map map;

    public RouteCanvas(Map map) {
        this.map = map;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        //clear(graphics);
        drawPoints(graphics);

    }

    private void  drawPoints(Graphics graphics) {
        synchronized (map.getPoints()) {
            Point last = null;
            for (Point point : map.getPoints()) {
                graphics.drawOval(point.getX()-2, point.getY()-2, 4, 4);
                if(last != null && drawLinesSequental){
                    graphics.drawLine(point.getX(), point.getY(), last.getX(), last.getY());
                }
                last = point;
            }
        }
    }

    private void clear(Graphics graphics) {
        Color origColor = graphics.getColor();

        graphics.setColor(getBackground());
        graphics.clearRect(0,0, getWidth(), getHeight());
        graphics.setColor(origColor);
    }


}
