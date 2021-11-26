package Graphics;

import Graphs.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Game {
    private int countIntermediate;
    private int countRectangles;
    private boolean startPointMoving = false;
    private boolean endPointMoving = false;

    private JButton wau=new JButton("Путь");
    private JButton exit=new JButton("Назад");

    private boolean rectanglePointMoving = false;
    private boolean rectanglePointMoving2 = false;

    private Point pressMousePoint = new Point();
    private Point pressMouseRectangle = new Point();
    private Point pressMouseRectangle2 = new Point();

    private Point circlePosition = new Point();
    private Point rectanglePosition = new Point();
    private Point rectanglePosition2 = new Point();

    public final int splittingX = 15;
    public final int splittingY = 15;

    public static final int radius = 15;

    public Game(int countIntermediate, int countRectangles) {
        this.countIntermediate = countIntermediate;
        this.countRectangles = countRectangles;

        System.out.println("Запуск игры...");
        JFrame window = new JFrame("Мобильный робот движется по плоскости");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(900, 900);

        window.setLocationRelativeTo(null);
        window.setResizable(false);

      /*  wau.setBounds(800,20,80,40);
        wau.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

            }
        });
        window.add(wau);

        exit.setBounds(800,80,80,40);
        window.add(exit);*/

        JComponent jComponent = new LightweightRect();

        window.add(jComponent);

        jComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                LightweightRect.mouseStart = e.getPoint();

                int x = e.getX();
                int y = e.getY();

                int centerX = LightweightRect.startPoint.x + radius;
                int centerY = LightweightRect.startPoint.y + radius;

                if (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2) < Math.pow(radius, 2)) {
                    startPointMoving = true;

                    pressMousePoint.x = e.getX();
                    pressMousePoint.y = e.getY();

                    circlePosition.x = LightweightRect.startPoint.x;
                    circlePosition.y = LightweightRect.startPoint.y;
                } else {
                    startPointMoving = false;
                }
            }
        });

        jComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                LightweightRect.mouseEnd = e.getPoint();


                int x = e.getX();
                int y = e.getY();

                int centerX = LightweightRect.endPoint.x + radius;
                int centerY = LightweightRect.endPoint.y + radius;

                if (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2) < Math.pow(radius, 2)) {
                    endPointMoving = true;

                    pressMousePoint.x = e.getX();
                    pressMousePoint.y = e.getY();

                    circlePosition.x = LightweightRect.endPoint.x;
                    circlePosition.y = LightweightRect.endPoint.y;
                } else {
                    endPointMoving = false;
                }
            }
        });

        jComponent.addMouseListener(new MouseAdapter() {//прямоугольник
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                LightweightRect.mouseRectangle = e.getPoint();

                System.out.println(e.getX());
                System.out.println(e.getY());

                if (isEmpty(LightweightRect.rectangle, e.getPoint(), LightweightRect.width, LightweightRect.height)) {
                    rectanglePointMoving = true;

                    pressMouseRectangle.x = e.getX();
                    pressMouseRectangle.y = e.getY();

                    rectanglePosition.x = LightweightRect.rectangle.x;
                    rectanglePosition.y = LightweightRect.rectangle.y;
                } else {
                    rectanglePointMoving = false;
                }
            }
        });

        jComponent.addMouseListener(new MouseAdapter() {//прямоугольник 2
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                LightweightRect.mouseRectangle2 = e.getPoint();

                System.out.println(e.getX());
                System.out.println(e.getY());

                if (isEmpty(LightweightRect.rectangle2, e.getPoint(), LightweightRect.height, LightweightRect.width)) {
                    rectanglePointMoving2 = true;

                    pressMouseRectangle2.x = e.getX();
                    pressMouseRectangle2.y = e.getY();

                    rectanglePosition2.x = LightweightRect.rectangle2.x;
                    rectanglePosition2.y = LightweightRect.rectangle2.y;
                } else {
                    rectanglePointMoving2 = false;
                }
            }
        });

        jComponent.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (startPointMoving) {
                    double newCircleX = e.getX() - pressMousePoint.x + circlePosition.x;
                    double newCircleY = e.getY() - pressMousePoint.y + circlePosition.y;

                    long pointAttractionX = Math.round(newCircleX / splittingX);
                    newCircleX = splittingX * pointAttractionX - radius;

                    long pointAttractionY = Math.round(newCircleY / splittingY);
                    newCircleY = splittingY * pointAttractionY - radius;

                    if (newCircleX <= window.getWidth() && newCircleY <= window.getHeight() && newCircleX >= 0 && newCircleY >= 0) {
                        LightweightRect.startPoint.setLocation(newCircleX, newCircleY);
                    }

                    jComponent.repaint();
                }
            }
        });

        jComponent.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (endPointMoving) {
                    double newCircleX = e.getX() - pressMousePoint.x + circlePosition.x;
                    double newCircleY = e.getY() - pressMousePoint.y + circlePosition.y;

                    long pointAttractionX = Math.round(newCircleX / splittingX);
                    newCircleX = splittingX * pointAttractionX - radius;

                    long pointAttractionY = Math.round(newCircleY / splittingY);
                    newCircleY = splittingY * pointAttractionY - radius;

                    if (newCircleX <= window.getWidth() && newCircleY <= window.getHeight() && newCircleX >= 0 && newCircleY >= 0) {
                        LightweightRect.endPoint.setLocation(newCircleX, newCircleY);
                    }

                    jComponent.repaint();
                }
            }
        });


        jComponent.addMouseMotionListener(new MouseAdapter() {//прямоугольник1
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (rectanglePointMoving) {
                    double newRectangleX = e.getX() - pressMouseRectangle.x + rectanglePosition.x;
                    double newRectangleY = e.getY() - pressMouseRectangle.y + rectanglePosition.y;

                    long pointAttractionX = Math.round(newRectangleX / splittingX);
                    newRectangleX = splittingX * pointAttractionX;

                    long pointAttractionY = Math.round(newRectangleY / splittingY);
                    newRectangleY = splittingY * pointAttractionY;

                    if (newRectangleX <= window.getWidth() && newRectangleY <= window.getHeight() && newRectangleX >= 0 && newRectangleY >= 0) {
                        LightweightRect.rectangle.setLocation(newRectangleX, newRectangleY);
                    }

                    jComponent.repaint();
                }
            }
        });

        jComponent.addMouseMotionListener(new MouseAdapter() {//прямоугольник2
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (rectanglePointMoving2) {
                    double newRectangleX = e.getX() - pressMouseRectangle2.x + rectanglePosition2.x;
                    double newRectangleY = e.getY() - pressMouseRectangle2.y + rectanglePosition2.y;

                    long pointAttractionX = Math.round(newRectangleX / splittingX);
                    newRectangleX = splittingX * pointAttractionX;

                    long pointAttractionY = Math.round(newRectangleY / splittingY);
                    newRectangleY = splittingY * pointAttractionY;

                    if (newRectangleX <= window.getWidth() && newRectangleY <= window.getHeight() && newRectangleX >= 0 && newRectangleY >= 0) {
                        LightweightRect.rectangle2.setLocation(newRectangleX, newRectangleY);
                    }

                    jComponent.repaint();
                }
            }
        });

        window.setVisible(true);
        System.out.println("Конец игры...");
    }

    private boolean isEmpty(Point point, Point mouse, int width, int height) {
        if (point.getX() <= mouse.getX() && point.getY() <= mouse.getY() &&

                (point.getX() + width) >= mouse.getX() &&
                point.getY() <= mouse.getY() &&

                point.getX() <= mouse.getX() &&
                (point.getY() + height) >= mouse.getY() &&

                (point.getX() + width) >= mouse.getX() &&
                (point.getY() + height) >= mouse.getY()
        )
            return true;

        return false;
    }


    class LightweightRect extends JComponent {

        public static Point startPoint = new Point(30 - radius, 30 - radius);
        public static Point mouseStart;

        public static Point endPoint = new Point(270 - radius, 270 - radius);
        public static Point mouseEnd;


        public static Point rectangle = new Point(360, 360);
        public static Point mouseRectangle;

        public static Point rectangle2 = new Point(600, 600);
        public static Point mouseRectangle2;

        public static int width = 100;
        public static int height = 200;


        private static boolean isWork = true;

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            drawGrid(graphics);

            graphics.setColor(Color.RED);
            graphics.fillOval(startPoint.x, startPoint.y, 30, 30);

            graphics.setColor(Color.BLUE);
            graphics.fillOval(endPoint.x, endPoint.y, 30, 30);


            graphics.setColor(Color.ORANGE);
            graphics.fillRect((int) rectangle.getX(), (int) rectangle.getY(), width, height);

            graphics.setColor(Color.ORANGE);
            graphics.fillRect((int) rectangle2.getX(), (int) rectangle2.getY(), height, width);

            ArrayList<Graphs.Point> list = Graph.FindPath(new Graphs.Point(
                            (int) startPoint.getX() / splittingX
                            , (int) startPoint.getY() / splittingY),
                    new Graphs.Point((int) endPoint.getX() / splittingX,
                            (int) endPoint.getY() / splittingY));


            graphics.setColor(Color.RED);
            Graphics2D g = (Graphics2D) graphics;
            g.setStroke(new BasicStroke(3));

            for (int i = 0; i < list.size() - 1; i++) {
                graphics.drawLine(list.get(i).X * splittingX + radius, list.get(i).Y * splittingY + radius
                        , list.get(i + 1).X * splittingX + radius, list.get(i + 1).Y * splittingY + radius);
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

            for (int i = 0; i < 855 / splittingX; i++) {
                for (int j = 0; j < 855 / splittingY; j++) {
                    Graph.matrix[i][j] = splittingY;
                }
            }

            infiniteSpace(rectangle, width, height);
            infiniteSpace(rectangle2, height, width);

        }

        private void infiniteSpace(Point p, int width, int height) {
            for (int i = (int) p.getX() - splittingX; i < p.getX() + width; i += splittingX) {
                for (int j = (int) p.getY() - splittingY; j < p.getY() + height; j += splittingY) {
                    Graph.matrix[i / splittingX][j / splittingY] = Integer.MAX_VALUE;
                }
            }
        }
    }
}





