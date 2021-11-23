package Graphics;

import Graphs.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main {
    private static boolean startPointMoving = false;
    private static boolean endPointMoving = false;
    private static boolean rectanglePointMoving = false;

    private static Point pressMousePoint = new Point();
    private static Point pressMouseRectangle=new Point();

    private static Point circlePosition = new Point();
    private static Point rectanglePosition = new Point();

    public static final int splittingX = 15;
    public static final int splittingY = 15;

    public static final int radius = 15;

    public static void main(String[] args) {
        System.out.println("Запуск игры...");
        JFrame window = new JFrame("Мобильный робот движется по плоскости");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(900, 900);

        window.setLocationRelativeTo(null);
        window.setResizable(false);

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

                if (isEmpty(e.getPoint())) {
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


        jComponent.addMouseMotionListener(new MouseAdapter() {//прямоугольник
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

        window.setVisible(true);
        System.out.println("Конец игры...");
    }

    private static boolean isEmpty(Point mouse) {
        if (LightweightRect.rectangle.getX() <= mouse.getX() && LightweightRect.rectangle.getY() <= mouse.getY() &&

                (LightweightRect.rectangle.getX() + LightweightRect.width) >= mouse.getX() &&
                LightweightRect.rectangle.getY() <= mouse.getY() &&

                LightweightRect.rectangle.getX() <= mouse.getX() &&
                (LightweightRect.rectangle.getY() + LightweightRect.height) >= mouse.getY() &&

                (LightweightRect.rectangle.getX() + LightweightRect.width) >= mouse.getX() &&
                (LightweightRect.rectangle.getY() + LightweightRect.height) >= mouse.getY()
        )
            return true;

        return false;
    }


    static class LightweightRect extends JComponent {

        public static Point startPoint = new Point(30 - radius, 30 - radius);
        public static Point mouseStart;

        public static Point endPoint = new Point(270 - radius, 270 - radius);
        public static Point mouseEnd;


        public static Point rectangle = new Point(360, 360);
        public static Point mouseRectangle;
        public static int width = 7*splittingX;
        public static int height = 16*splittingY;


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

           //System.out.println(list);
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



                Graph.matrix = new int[getWidth() / splittingX][getHeight() / splittingY];

                for (int i = 0; i < getWidth() / splittingX; i++) {
                    for (int j = 0; j < getHeight() / splittingY; j++) {
                        Graph.matrix[i][j] = splittingY;
                    }
                }

                for(int i = (int) rectangle.getX()-splittingX; i<rectangle.getX()+width;i+=splittingX){
                    for(int j=(int)rectangle.getY()-splittingY;j<rectangle.getY()+height;j+=splittingY){
                        Graph.matrix[i/splittingX][j/splittingY]=Integer.MAX_VALUE;
                    }
                }

        }
    }
}



