package Graphics;

import Graphs.Circle;
import Graphs.Graph;
import Graphs.LightweightRect;
import Graphs.WorkingFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Game extends JFrame {
    public static final ArrayList<Circle> circles = new ArrayList<>();
    public static final ArrayList<Graphs.Rectangle> rectangles = new ArrayList<>();

    private final Point pressMousePoint = new Point();
    private final Point oldDraggedPositionPoint = new Point();

    public static int splittingX = 15;
    public static int splittingY = 15;

    public final static int fieldSize = 900;

    public static int radius = 15;
    public static int width = radius * 6;
    public static int height = radius * 13;
    public static int countIntermediate = 0;
    public static int countRectangles = 0;
    public static int x;
    public static int y;
    public static boolean isNoWay = false;

    private final Container container = this.getContentPane();
    private final JPanel help = new JPanel(new GridLayout(20, 1));

    public Game() {
        super("Мобильный робот движется по плоскости");
        JComponent jPanel = new LightweightRect();
        jPanel.setBounds(0, 0, 900, 900);
        LightweightRect l = (LightweightRect) jPanel;

        initCircles(countIntermediate, l);

        initRectangle(countRectangles, l);

        System.out.println("Запуск игры...");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1200, 900);

        setLocationRelativeTo(null);
        setResizable(false);

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pressMousePoint.x = e.getX();//не трогать
                pressMousePoint.y = e.getY();//не трогать

                isCirclePressed(e);

                isRectanglePressed(e);
            }
        });

        jPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                isCircleDragged(e, this, jPanel, splittingX, splittingY, radius);

                isRectangleDragged(e, this, jPanel, splittingX, splittingY);
            }
        });

        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.gridx = GridBagConstraints.LINE_START;
        constraints.ipadx = 990;
        constraints.ipady = 990;
        container.add(jPanel, constraints);

        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.gridx = GridBagConstraints.LINE_END;
        constraints.ipadx = 100;
        constraints.ipady = 80;
        container.add(new ParametersWindow(jPanel, l), constraints);

        setVisible(true);
        System.out.println("Конец игры...");
    }


    public static void deleteRectangles(LightweightRect l, JComponent jComponent) {
        rectangles.remove(rectangles.size() - 1);
        l.rectangles = rectangles;
        jComponent.repaint();
    }

    public static void setAddRectangle(LightweightRect l, JComponent jComponent) {
        randRectangle();
        if (rectangles.size() % 2 == 0) {
            rectangles.add(new Graphs.Rectangle(x, y, height, width));
        } else {
            rectangles.add(new Graphs.Rectangle(x, y, width, height));
        }

        l.rectangles = rectangles;
        jComponent.repaint();
    }

    private static void randRectangle() {
        for (int i = 5; i < Graph.matrix.length; i++) {
            boolean isEnd = false;

            for (int j = 5; j < Graph.matrix[i].length; j++) {
                if (Graph.matrix[i][j] != Integer.MAX_VALUE) {
                    int count = 0;
                    x = i * splittingX;
                    y = j * splittingY;

                    for (int k = 0; k < rectangles.size(); k++) {
                        Graphs.Rectangle temp = rectangles.get(k);

                        if (temp.point.getY() != y && temp.point.getX() != x) {
                            count++;
                        }
                    }

                    if (count == rectangles.size()) {
                        isEnd = true;
                        break;
                    }

                }
            }

            if (isEnd)
                break;
        }
    }


    public static void randCircle() {
        for (int i = 3; i < Graph.matrix.length; i++) {
            boolean isEnd = false;

            for (int j = 3; j < Graph.matrix[i].length; j++) {
                if (Graph.matrix[i][j] != Integer.MAX_VALUE) {
                    int count = 0;
                    x = i * splittingX;
                    y = j * splittingY;

                    for (int k = 0; k < circles.size(); k++) {
                        Circle temp = circles.get(k);

                        if (temp.point.getY() != y && temp.point.getX() != x) {
                            count++;
                        }
                    }

                    if (count == circles.size()) {
                        isEnd = true;
                        break;
                    }

                }
            }

            if (isEnd)
                break;
        }
    }

    private void isRectanglePressed(MouseEvent e) {
        for (int i = 0; i < rectangles.size(); i++) {
            Graphs.Rectangle rectangle = rectangles.get(i);

            if (rectangle.mouseInside(e)) {
                oldDraggedPositionPoint.x = rectangle.point.x;//не трогать
                oldDraggedPositionPoint.y = rectangle.point.y;//не трогать
            }

        }
    }

    private void isRectangleDragged(MouseEvent e, MouseAdapter window, JComponent jComponent, int splittingX, int splittingY) {
        for (int i = 0; i < rectangles.size(); i++) {
            Graphs.Rectangle rectangle = rectangles.get(i);

            if (rectangle.moving) {
                double newRectangleX = e.getX() - pressMousePoint.x + oldDraggedPositionPoint.x;
                double newRectangleY = e.getY() - pressMousePoint.y + oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newRectangleX / splittingX);
                newRectangleX = splittingX * pointAttractionX;

                long pointAttractionY = Math.round(newRectangleY / splittingY);
                newRectangleY = splittingY * pointAttractionY;

                if (newRectangleX + rectangle.w < fieldSize + radius && newRectangleY + rectangle.h < fieldSize - 2 * radius && newRectangleX >= 0 && newRectangleY >= 0) {
                    rectangle.point.setLocation(newRectangleX, newRectangleY);
                    jComponent.repaint();
                }
            }
        }
    }

    private void isCircleDragged(MouseEvent e, MouseAdapter window, JComponent jComponent, int splittingX, int splittingY, int radius) {
        for (int i = 0; i < circles.size(); i++) {
            circles.get(i).radius = radius;
            Circle circle1 = circles.get(i);

            if (circle1.moving) {
                double newCircleX = e.getX() - pressMousePoint.x + oldDraggedPositionPoint.x;
                double newCircleY = e.getY() - pressMousePoint.y + oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newCircleX / splittingX);
                newCircleX = splittingX * pointAttractionX;

                long pointAttractionY = Math.round(newCircleY / splittingY);
                newCircleY = splittingY * pointAttractionY;

                if (newCircleX <= fieldSize - splittingX - radius && newCircleY <= fieldSize - 4 * splittingY - radius && newCircleX >= 0 && newCircleY >= 0 && isExistingPoint(newCircleX, newCircleY)) {
                    circle1.point.setLocation(newCircleX, newCircleY);
                    jComponent.repaint();
                }
            }
        }
    }

    private void isCirclePressed(MouseEvent e) {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1 = circles.get(i);

            if (circle1.mouseInside(e)) {
                oldDraggedPositionPoint.x = circle1.point.x;//не трогать
                oldDraggedPositionPoint.y = circle1.point.y;//не трогать
            }
        }
    }

    private void initRectangle(int countRectangles, LightweightRect l) {
        int u = 60;
        int o = 300;
        int x1 = 40;
        int x2 = 40;

        for (int i = 0; i < countRectangles; i++) {
            if (u > 800 || o > 800) {
                x1 += width + 20;
                u = 60;
                x2 += height + 20;
                o = 300;
            }

            if (i % 2 == 0) {
                rectangles.add(new Graphs.Rectangle(x1, u, height, width));
                u += height;
            } else {
                rectangles.add(new Graphs.Rectangle(x2, o, width, height));
                o += width + 20;

            }
        }

        l.rectangles = rectangles;
    }

    public static void deleteCircle(LightweightRect l, JComponent jComponent) {
        if (circles.size() > 2) {
            circles.remove(1);
            l.circles = circles;
            jComponent.repaint();
        }
    }

    private void initCircles(int countIntermediate, LightweightRect l) {
        int t = 30;
        countIntermediate += 2;

        for (int i = 0; i < countIntermediate; i++) {
            int type = Circle.POINT_INTERMEDIATE;
            if (i == 0) {
                type = Circle.POINT_START;
            }

            if (i == countIntermediate - 1) {
                type = Circle.POINT_END;
            }

            circles.add(new Circle(t, 30, radius, type));
            t += 30;
        }

        l.circles = circles;
    }

    private boolean isExistingPoint(double x, double y) {
        for (int i = 0; i < circles.size(); i++) {
            Circle temp = circles.get(i);

            if (temp.point.getX() == x && temp.point.getY() == y) {
                return false;
            }
        }

        return true;
    }
}





