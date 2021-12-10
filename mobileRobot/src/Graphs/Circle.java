package Graphs;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class Circle {
    public Point point=new Point();

    @Override
    public String toString() {
        return "Circle{" +
                "point=" + point +
                ", radius=" + radius +
                ", moving=" + moving +
                ", type=" + type +
                '}';
    }


    public int radius;
    public boolean moving;
    private int type;//0 ноль  это старт 1 конец 2 промежуточный

    public static final int POINT_START=0;
    public static final int POINT_END=1;
    public static final int POINT_INTERMEDIATE=2;

    public Circle(int x, int y, int radius, int type){
        this.type = type;
        this.point.setLocation(x,y);
        this.radius=radius;
    }

    public boolean mouseInside(MouseEvent e){
        int x =e.getX();
        int y =e.getY();

        int centerX = point.x + radius;
        int centerY = point.y + radius;

        if (Math.pow((x - centerX), 2) + Math.pow((y - centerY), 2) < Math.pow(radius, 2)) {
            moving=true;
        } else {
            moving=false;
        }

        return moving;
    }

    public int getType() {
        return type;
    }
}
