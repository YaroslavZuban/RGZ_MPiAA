package Graphs;

import java.awt.Point;
import java.awt.event.MouseEvent;

public class Rectangle {

    public Rectangle(int x,int y, int h, int w) {
        this.point.setLocation(x,y);
        this.h = h;
        this.w = w;
    }

    public Point point=new Point();
    public int h;
    public int w;
    public boolean moving;

    @Override
    public String toString() {
        return "Rectangle{" +
                "point=" + point +
                ", h=" + h +
                ", w=" + w +
                ", moving=" + moving +
                '}';
    }

    public boolean mouseInside(MouseEvent e){
        moving=isEmpty(point,e.getPoint(),w,h);
        return moving;
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

}
