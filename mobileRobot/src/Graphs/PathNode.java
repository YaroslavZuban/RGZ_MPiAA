package Graphs;

import java.awt.*;

public class PathNode extends Point{
    public Point goal;
    public Point position;

    public PathNode(Point point, PathNode pathNode, Point goal) {
        super(point.X, point.Y);
        position = point;
        setCameFrome(pathNode);
        this.goal = goal;
        if (pathNode!=null)//нужно только для стартовой точки
            setLengthFromStart(pathNode.getLengthFromStart());
        else setLengthFromStart(-1);
        setLengthToGoal(goal);
        setFullLength(getLengthFromStart()+getLengthToGoal());
    }

    public PathNode CameFrome;//родительская точка

    public void setCameFrome(PathNode CameFrome) {
        this.CameFrome = CameFrome;
    }

    public int LengthFromStart;

    public void setLengthFromStart(int i) {
        LengthFromStart = i + 1;
    }

    public int getLengthFromStart() {
        return LengthFromStart;
    }

    public int LengthToGoal;
    public void setLengthToGoal(Point goal)//манхэттенское расстояние до цели
    {
        LengthToGoal = (int) (Math.abs( this.position.X-goal.X ) + Math.abs(this.position.Y-goal.Y));
    }
    public int getLengthToGoal()
    {
        return LengthToGoal;
    }

    public int FullLength;
    public void setFullLength(int i)
    {
        this.FullLength=i;
    }
    public int getFullLength() {
        return FullLength;
    }
}
