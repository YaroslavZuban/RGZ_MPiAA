package Graphics;

import Graphs.Circle;
import Graphs.LightweightRect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class Game {
    private ArrayList<Circle> circles=new ArrayList<>();
    private ArrayList<Graphs.Rectangle> rectangles=new ArrayList<>();

    private Point pressMousePoint = new Point();
    private Point oldDraggedPositionPoint = new Point();

    public  final static int splittingX = 15;
    public  final static int splittingY = 15;

    public static final int radius = 15;
    public static int width = radius*6;
    public static int height = radius*13;


    public Game(int countIntermediate, int countRectangles) {
        JComponent jComponent =new LightweightRect();
        LightweightRect l=(LightweightRect) jComponent;

        initCircles(countIntermediate, l);

        initRectangle(countRectangles,l);

        System.out.println("Запуск игры...");
        JFrame window = new JFrame("Мобильный робот движется по плоскости");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(900, 900);

        window.setLocationRelativeTo(null);
        window.setResizable(false);

        window.add(jComponent);


        jComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                pressMousePoint.x = e.getX();//не трогать
                pressMousePoint.y = e.getY();//не трогать

                isCirclePressed(e);

                isRectanglePressed(e);
            }
        });


        jComponent.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                isCircleDragged(e, window, jComponent);

                isRectangleDragged(e, window, jComponent);
            }
        });

        window.setVisible(true);
        System.out.println("Конец игры...");
    }

    private void isRectanglePressed(MouseEvent e) {
        for (int i = 0; i < rectangles.size(); i++) {
            Graphs.Rectangle rectangle=rectangles.get(i);

            if(rectangle.mouseInside(e)){
                oldDraggedPositionPoint.x =rectangle.point.x;//не трогать
                oldDraggedPositionPoint.y =rectangle.point.y;//не трогать
            }

        }
    }

    private void isRectangleDragged(MouseEvent e, JFrame window, JComponent jComponent) {
        for(int i=0;i<rectangles.size();i++) {
            Graphs.Rectangle rectangle=rectangles.get(i);

            if (rectangle.moving) {
                double newRectangleX = e.getX() - pressMousePoint.x +oldDraggedPositionPoint.x;
                double newRectangleY = e.getY() - pressMousePoint.y +oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newRectangleX / splittingX);
                newRectangleX = splittingX * pointAttractionX;

                long pointAttractionY = Math.round(newRectangleY / splittingY);
                newRectangleY = splittingY * pointAttractionY;

                if (newRectangleX <= window.getWidth()-2*radius && newRectangleY <= window.getHeight()-2*radius && newRectangleX >= 0 && newRectangleY >= 0) {
                    rectangle.point.setLocation(newRectangleX, newRectangleY);
                    jComponent.repaint();
                }
            }
        }
    }

    private void isCircleDragged(MouseEvent e, JFrame window, JComponent jComponent) {
        for(int i=0;i<circles.size();i++) {
            Circle circle1=circles.get(i);

            if (circle1.moving) {
                double newCircleX = e.getX() - pressMousePoint.x + oldDraggedPositionPoint.x;
                double newCircleY = e.getY() - pressMousePoint.y + oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newCircleX / splittingX);
                newCircleX = splittingX * pointAttractionX ;

                long pointAttractionY = Math.round(newCircleY / splittingY);
                newCircleY = splittingY * pointAttractionY ;
                System.out.println("newCircleX "+newCircleX);
                System.out.println("newCircleY "+newCircleY);


                System.out.println("w "+(window.getWidth() ));
                System.out.println("h "+(window.getHeight() ));

                if (newCircleX <= window.getWidth()-2*radius && newCircleY <= window.getHeight()-2*radius  && newCircleX >= 0 && newCircleY >= 0) {
                    System.out.println("repaint");
                    circle1.point.setLocation(newCircleX, newCircleY);
                    jComponent.repaint();
                }
            }
        }
    }

    private void isCirclePressed(MouseEvent e) {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1=circles.get(i);

            if(circle1.mouseInside(e)){
                oldDraggedPositionPoint.x =circle1.point.x;//не трогать
                oldDraggedPositionPoint.y =circle1.point.y;//не трогать
            }
        }
    }

    private void initRectangle(int countRectangles, LightweightRect l) {
        int u=60;
        int o=300;

        for (int i = 0; i< countRectangles; i++){
            if(i%2==0) {
                rectangles.add(new Graphs.Rectangle(15, u, height,width));
                u +=height;
            }else{
                rectangles.add(new Graphs.Rectangle(15,o, width,height));
                o+=width;
            }
        }

        l.rectangles=rectangles;
    }

    private void initCircles(int countIntermediate, LightweightRect l) {
        int t=30;
        countIntermediate+=2;

        for(int i = 0; i< countIntermediate; i++){
            int type=Circle.POINT_INTERMEDIATE;
            if(i==0){
                type=Circle.POINT_START;
            }

            if(i==countIntermediate-1){
                type=Circle.POINT_END;
            }

            circles.add(new Circle(t,30,radius,type));
            t+=30;
        }

        l.circles=circles;
    }


}





