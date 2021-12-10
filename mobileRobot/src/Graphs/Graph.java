package Graphs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Graph {
    public static int[][] matrix;

    public static ArrayList<Point> FindPath(Point start, Point goal) {
        ArrayList<PathNode> closedSet = new ArrayList<>();//список рассмотренных точек
        ArrayList<PathNode> openSet = new ArrayList<>();//список еще не рассмотренных точек

        PathNode startNode = new PathNode(start, null, goal);

        startNode.LengthFromStart = 0;
        int z = 0;
        openSet.add(startNode);
        while (openSet.size() > 0) {
            PathNode currentNode = minLength(openSet);//находит точку с min "Полным путем"
            if (currentNode.position.X == goal.X && currentNode.position.Y == goal.Y)
                return GetPath(currentNode);//если в конечной точке, то возращает весь путь
            closedSet.add(currentNode);
            openSet.remove(currentNode);

            for (PathNode neighbourNode : GetNeighbours(currentNode, goal))//рассматривает соседей текущей точки
            {
                if (CountInList(closedSet, neighbourNode) > 0)//проверяет есть ли точка с этими координатами в closedSet
                    continue;//если да, то берем следующию
                PathNode openNode = getInList(openSet, neighbourNode);//ищет точку с такими же координатами в openSet
                if (openNode == null) {//если нет, то добавляет
                    openSet.add(neighbourNode);
                } else {//если есть то проверяет не большее ли у нее путь от старта, чем у соседа текущей точки
                    if (openNode.LengthFromStart > neighbourNode.LengthFromStart) {
                        //если больше то заменяем ее данные на данные соседа
                        openNode.setCameFrome(currentNode);
                        openNode.LengthFromStart = neighbourNode.LengthFromStart;
                        openNode.FullLength = neighbourNode.FullLength;
                    }
                }
            }
        }

        return null;
    }

    private static ArrayList<PathNode> GetNeighbours(PathNode pathNode, Point goal)//возвращает список "годных" соседей
    {
        int i=0;
        int x=1;
        ArrayList<PathNode> result = new ArrayList<>();
        Point[] neighbourPoints = new Point[8];
        neighbourPoints[0] = new Point(pathNode.position.X + 1, pathNode.position.Y);
        neighbourPoints[1] = new Point(pathNode.position.X - 1, pathNode.position.Y);
        neighbourPoints[2] = new Point(pathNode.position.X, pathNode.position.Y + 1);
        neighbourPoints[3] = new Point(pathNode.position.X, pathNode.position.Y - 1);

        neighbourPoints[4] = new Point(pathNode.position.X - 1, pathNode.position.Y - 1);
        neighbourPoints[5] = new Point(pathNode.position.X + 1, pathNode.position.Y - 1);
        neighbourPoints[6] = new Point(pathNode.position.X - 1, pathNode.position.Y + 1);
        neighbourPoints[7] = new Point(pathNode.position.X + 1, pathNode.position.Y + 1);

        for (Point point : neighbourPoints) {
            if (point.X < 0 || point.X >= matrix.length)
                continue;
            if (point.Y < 0 || point.Y >= matrix[0].length)
                continue;
            if (matrix[point.X][point.Y] == Integer.MAX_VALUE)
                continue;

            PathNode neighbourNode = new PathNode(point, pathNode, goal);
            result.add(neighbourNode);
        }
        return result;
    }

    public static int CountInList(ArrayList<PathNode> list, PathNode pathNode)//проверяет есть ли точка с этими координатами в closedSet
    {

        for (PathNode point : list) {
            if (point.position.X == pathNode.position.X && point.position.Y == pathNode.position.Y) {
                return 1;
            }
        }
        return 0;
    }

    public static PathNode getInList(ArrayList<PathNode> list, PathNode pathNode)//ищет точку с такими же координатами в openSet
    {
        for (PathNode point : list) {
            if (point.position.X == pathNode.position.X && point.position.Y == pathNode.position.Y) {

                return point;
            }
        }
        return null;
    }

    public static ArrayList<Point> GetPath(PathNode pathNode)//возвращает список точек до данной.
    {
        ArrayList<Point> result = new ArrayList<>();
        PathNode current = pathNode;
        while (current != null) {
            result.add(new Point(current.X, current.Y));
            current = current.CameFrome;
        }
        return result;
    }


    public static PathNode minLength(ArrayList<PathNode> set)//находит точку с min "Полным путем"
    {
        PathNode result = set.get(0);
        for (PathNode min : set) {
            if (min.FullLength < result.getFullLength()) {
                result = min;
            }
        }
        return result;
    }

    private static boolean isEmpty(Point p) {
        Point temp1 = new Point(p.getX() - 1, p.getY() - 1);
        Point temp2 = new Point(p.getX() + 1, p.getY() - 1);
        Point temp3 = new Point(p.getX() - 1, p.getY() + 1);
        Point temp4 = new Point(p.getX() + 1, p.getY() + 1);

        if (matrix[temp1.X][temp1.Y] == Integer.MAX_VALUE ||
                matrix[temp2.X][temp2.Y] == Integer.MAX_VALUE ||
                matrix[temp3.X][temp3.Y] == Integer.MAX_VALUE ||
                matrix[temp4.X][temp4.Y] == Integer.MAX_VALUE
        )
            return true;

        return false;
    }
}

