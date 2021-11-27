package Graphs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static Graphics.Game.radius;
import static Graphics.Game.splittingX;
import static Graphics.Game.splittingY;

public class LightweightRect extends JComponent {
    public ArrayList<Circle> circles = new ArrayList<>();
    public ArrayList<Rectangle> rectangles = new ArrayList<>();
    private int drawCount=0;
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawCount++;
        System.out.println("start_draw_"+drawCount);

        drawGrid(graphics);

        drawCircle(graphics);

        drawRectangle(graphics);

        drawLine(graphics);

        System.out.println("end_draw_"+drawCount);
    }

    private void drawLine(Graphics graphics) {
        graphics.setColor(Color.RED);
        for (int j = 0; j < circles.size() - 1; j++) {
            ArrayList<Point> list = Graph.FindPath(new Point(
                            (int) circles.get(j).point.getX() / splittingX
                            , (int) circles.get(j).point.getY() / splittingY),
                    new Point((int) circles.get(j + 1).point.getX() / splittingX,
                            (int) circles.get(j + 1).point.getY() / splittingY));

            System.out.println("j= "+j+" list= "+list);
            double start_x=circles.get(j).point.getX();
            double start_y=circles.get(j).point.getY();

            System.out.println(j+"("+start_x+" "+start_y+")");

            double end_x= circles.get(j + 1).point.getX() ;
            double end_y= circles.get(j + 1).point.getY() ;
            System.out.println((j+1)+"("+end_x+" "+end_y+")");

            Graphics2D g = (Graphics2D) graphics;
            g.setStroke(new BasicStroke(3));

            for (int i = 0; i < list.size() - 1; i++) {
                graphics.drawLine(list.get(i).X * splittingX + radius, list.get(i).Y * splittingY + radius
                        , list.get(i + 1).X * splittingX + radius, list.get(i + 1).Y * splittingY + radius);
            }
        }
    }

    private void drawRectangle(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rectangle = rectangles.get(i);
            graphics.fillRect((int) rectangle.point.getX(), (int) rectangle.point.getY(), rectangle.w, rectangle.h);
        }
    }

    private void drawCircle(Graphics graphics) {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1 = circles.get(i);

            if (Circle.POINT_START == circle1.getType() || Circle.POINT_END == circle1.getType())
                graphics.setColor(Color.RED);
            else
                graphics.setColor(Color.BLUE);

            graphics.fillOval(circle1.point.x, circle1.point.y, radius * 2, radius * 2);

        }
    }

    private void drawGrid(Graphics g) {
        int w = getWidth();
        int h = getHeight();

        int dw = splittingX;
        int dh = splittingY;

        g.setColor(Color.BLACK);

        for (int i = 1; i <= getWidth() / splittingX; i++) {
            int y1 = dh * i;
            int x1 = dw * i;

            int y2 = dh * i;
            int x2 = dw * i;

            if (y2 <= getHeight() && y1 <= getHeight()) {
                g.drawLine(0, y1, w, y2);
            }

            if (x1 <= getWidth() && x2 <= getWidth())
                g.drawLine(x1, 0, x2, h);
        }


        Graph.matrix = new int[getWidth() / splittingX + 1][getHeight() / splittingY + 1];

        for (int i = 0; i < 870 / splittingX; i++) {
            for (int j = 0; j < 870 / splittingY; j++) {
                Graph.matrix[i][j] = splittingY;
            }
        }

        for (int i = 0; i < rectangles.size(); i++) {
            Rectangle rectangle = rectangles.get(i);
            infiniteSpace(rectangle.point, rectangle.w, rectangle.h);
        }
    }

    private void infiniteSpace(java.awt.Point p, int width, int height) {
        for (int i = (int) p.getX() - splittingX; i < p.getX() + width; i += splittingX) {
            for (int j = (int) p.getY() - splittingY; j < p.getY() + height; j += splittingY) {
                Graph.matrix[i / splittingX][j / splittingY] = Integer.MAX_VALUE;
            }
        }
    }
}

