package Graphs;

public class Point {
    @Override
    public String toString() {
        return "Point{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int X;
    public int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }
}
