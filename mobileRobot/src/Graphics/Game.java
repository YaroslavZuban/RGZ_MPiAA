package Graphics;

import Graphs.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Game {
    private ArrayList<Boolean> pointsMoving = new ArrayList<>();

    private Point pressMousePoint = new Point();
    private Point circlePosition = new Point();

    private ArrayList<Point> pressMouseR = new ArrayList<>();
    private ArrayList<Boolean> movingRectangle = new ArrayList<>();

    private ArrayList<Point> positionR = new ArrayList<>();

    public final int splittingX = 15;
    public final int splittingY = 15;

    public static final int radius = 15;

    public Game(int countIntermediate, int countRectangles) {
        //Работа с окружностями
        LightweightRect.points.add(LightweightRect.startPoint);

        int d=60;
        for (int i = 0; i < countIntermediate; i++) {
            LightweightRect.points.add(new Point(d - radius, 30 - radius));
            d+=d;
        }

        LightweightRect.points.add(LightweightRect.endPoint);

        for (int i = 0; i < LightweightRect.points.size(); i++) {
            pointsMoving.add(false);
            LightweightRect.mouse.add(new Point());
        }

        int w = radius;
        int h = radius;
        //Работа с прямоугольниками
        for (int i = 0; i < countRectangles; i++) {
            if (i % 2 != 0) {
                LightweightRect.rectangle.add(new Point(w, 90));
                w += LightweightRect.width + 15;
            } else {
                LightweightRect.rectangle.add(new Point(h, 400));
                h += LightweightRect.height + 15;
            }

            movingRectangle.add(false);
            pressMouseR.add(new Point());
        }
        System.out.println("Запуск игры...");
        JFrame window = new JFrame("Мобильный робот движется по плоскости");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(900, 900);

        window.setLocationRelativeTo(null);
        window.setResizable(false);

        JComponent jComponent = new LightweightRect();

        window.add(jComponent);

        for (int i = 0; i < LightweightRect.points.size(); i++) {
            int finalI = i;

            jComponent.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    LightweightRect.mouse.set(finalI, e.getPoint());

                    int x = e.getX();
                    int y = e.getY();

                    int centerX = LightweightRect.points.get(finalI).x + radius;
                    int centerY = LightweightRect.points.get(finalI).y + radius;

                    if (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2) < Math.pow(radius, 2)) {
                        pointsMoving.set(finalI, true);

                        pressMousePoint.x = e.getX();//не трогать
                        pressMousePoint.y = e.getY();//не трогать

                        circlePosition.x = LightweightRect.points.get(finalI).x;//не трогать
                        circlePosition.y = LightweightRect.points.get(finalI).y;//не трогать
                    } else {
                        pointsMoving.set(finalI, false);
                    }
                }
            });
        }


        for (int i = 0; i < LightweightRect.rectangle.size(); i++) {
            int finalI = i;
            jComponent.addMouseListener(new MouseAdapter() {//прямоугольник
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);

                    if (finalI % 2 == 0) {
                        if (isEmpty(LightweightRect.rectangle.get(finalI), e.getPoint(), LightweightRect.width, LightweightRect.height)) {
                            movingRectangle.set(finalI, true);

                            pressMouseR.set(finalI, e.getPoint());

                            positionR.set(finalI, LightweightRect.rectangle.get(finalI));
                        } else {
                            movingRectangle.set(finalI, false);
                        }
                    } else {
                        if (isEmpty(LightweightRect.rectangle.get(finalI), e.getPoint(), LightweightRect.height, LightweightRect.width)) {
                            movingRectangle.set(finalI, true);

                            pressMouseR.set(finalI, e.getPoint());

                            positionR.set(finalI, LightweightRect.rectangle.get(finalI));
                        } else {
                            movingRectangle.set(finalI, false);
                        }
                    }
                }
            });
        }

        for (int i = 0; i < LightweightRect.points.size(); i++) {
            int finalI = i;

            jComponent.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    if (pointsMoving.get(finalI)) {
                        double newCircleX = e.getX() - pressMousePoint.x + circlePosition.x;
                        double newCircleY = e.getY() - pressMousePoint.y + circlePosition.y;

                        long pointAttractionX = Math.round(newCircleX / splittingX);
                        newCircleX = splittingX * pointAttractionX - radius;

                        long pointAttractionY = Math.round(newCircleY / splittingY);
                        newCircleY = splittingY * pointAttractionY - radius;

                        if (newCircleX <= window.getWidth() && newCircleY <= window.getHeight() && newCircleX >= 0 && newCircleY >= 0) {
                            LightweightRect.points.get(finalI).setLocation(newCircleX, newCircleY);
                        }

                        jComponent.repaint();
                    }
                }
            });
        }

        for (int i = 0; i < LightweightRect.points.size(); i++) {
            int finalI = i;
            jComponent.addMouseMotionListener(new MouseAdapter() {//прямоугольник
                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    if (movingRectangle.get(finalI)) {
                        double newRectangleX = e.getX() - pressMouseR.get(finalI).x + positionR.get(finalI).x;
                        double newRectangleY = e.getY() - pressMouseR.get(finalI).y + positionR.get(finalI).y;

                        long pointAttractionX = Math.round(newRectangleX / splittingX);
                        newRectangleX = splittingX * pointAttractionX;

                        long pointAttractionY = Math.round(newRectangleY / splittingY);
                        newRectangleY = splittingY * pointAttractionY;

                        if (newRectangleX <= window.getWidth() && newRectangleY <= window.getHeight() && newRectangleX >= 0 && newRectangleY >= 0) {
                            Point temp = new Point();
                            temp.setLocation(newRectangleX, newRectangleY);

                            LightweightRect.rectangle.set(finalI, temp);
                        }

                        jComponent.repaint();
                    }
                }
            });
        }

        window.setVisible(true);
        System.out.println("Конец игры...");
    }

    private boolean isEmpty(Point point, Point mouse, int width, int height) {
        return point.getX() <= mouse.getX() && point.getY() <= mouse.getY() &&

                (point.getX() + width) >= mouse.getX() &&
                point.getY() <= mouse.getY() &&

                point.getX() <= mouse.getX() &&
                (point.getY() + height) >= mouse.getY() &&

                (point.getX() + width) >= mouse.getX() &&
                (point.getY() + height) >= mouse.getY();
    }


    class LightweightRect extends JComponent {
        //Окружности
        public static ArrayList<Point> points = new ArrayList<>();
        public static ArrayList<Point> mouse = new ArrayList<>();

        public static Point startPoint = new Point(30 - radius, 30 - radius);

        public static Point endPoint = new Point(270 - radius, 270 - radius);

        //прямоугольники
        public static ArrayList<Point> rectangle = new ArrayList<>();

        public static int width = 100;
        public static int height = 200;

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            drawGrid(graphics);

            graphics.setColor(Color.RED);
            graphics.fillOval(startPoint.x, startPoint.y, 30, 30);
            graphics.fillOval(endPoint.x, endPoint.y, 30, 30);

            graphics.setColor(Color.BLUE);
            for (int i = 1; i < points.size() - 1; i++) {
                graphics.fillOval(points.get(i).x, points.get(i).y, 30, 30);
            }

            graphics.setColor(Color.ORANGE);
            for (int i = 0; i < rectangle.size(); i++) {
                if (i % 2 == 0) {
                    graphics.fillRect((int) rectangle.get(i).getX(), (int) rectangle.get(i).getY(), width, height);
                } else {
                    graphics.fillRect((int) rectangle.get(i).getX(), (int) rectangle.get(i).getY(), height, width);
                }
            }

            graphics.setColor(Color.RED);
            for(int j=0;j<points.size()-1;j++) {
                ArrayList<Graphs.Point> list = Graph.FindPath(new Graphs.Point(
                                (int) points.get(j).getX() / splittingX
                                , (int) points.get(j).getY() / splittingY),
                        new Graphs.Point((int) points.get(j+1).getX() / splittingX,
                                (int) points.get(j+1).getY() / splittingY));


                Graphics2D g = (Graphics2D) graphics;
                g.setStroke(new BasicStroke(3));

                for (int i = 0; i < list.size() - 1; i++) {
                    graphics.drawLine(list.get(i).X * splittingX + radius, list.get(i).Y * splittingY + radius
                            , list.get(i + 1).X * splittingX + radius, list.get(i + 1).Y * splittingY + radius);
                }
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

            for (int i=0;i<rectangle.size();i++){
                if(i%2==0){
                    infiniteSpace(rectangle.get(i), width, height);
                }else{
                    infiniteSpace(rectangle.get(i), height,width);
                }
            }
        }

        private void infiniteSpace(Point p, int width, int height) {
            for (int i = (int) p.getX()-splittingX ; i < p.getX() + width; i += splittingX) {
                for (int j = (int) p.getY()-splittingY ; j < p.getY() + height; j += splittingY) {
                    Graph.matrix[i / splittingX][j / splittingY] = Integer.MAX_VALUE;
                }
            }
        }
    }
}





