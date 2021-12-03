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
    private final ArrayList<Circle> circles = new ArrayList<>();
    private final ArrayList<Graphs.Rectangle> rectangles = new ArrayList<>();

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
    private int x;
    private int y;
    public static boolean isNoWay = false;

    private final JButton addCircle = new JButton("Добавить точку");
    private final JButton removeCircle = new JButton("Удалить точку");
    private final JLabel countCircle = new JLabel("Промежуточные точки: " + countIntermediate);

    private final JButton addRectangle = new JButton("Добавить прямоугольник");
    private final JButton removeRectangle = new JButton("Удалить прямоугольник");
    private final JLabel countRectangle = new JLabel("Прямоугольников: " + countRectangles);

    private final JLabel textSizeSquare = new JLabel("Изменить размер клеток");
    private final JTextField sizeSquare = new JTextField("15", 4);
    private final JButton saveSizeSquare = new JButton("Сохранить размер клекти");

    private final JLabel textRadiusRobot = new JLabel("Изменить радиус робота");
    private final JTextField radiusRobot = new JTextField("15", 4);
    private final JButton saveRadiusRobot = new JButton("Сохранить размер робота");

    private final JLabel testSaveFile = new JLabel("Сохранения в файл");
    private final JButton saveFile = new JButton("Сохранения в файл");

    private final JLabel textReadingFile = new JLabel("Загрузить из файла");
    private final JButton readingFile = new JButton("Загрузить из файла");

    private final JLabel testWay = new JLabel("Построение пути");
    private final JButton way = new JButton("Построить путь");
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
                isCircleDragged(e, this, jPanel);

                isRectangleDragged(e, this, jPanel);
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


        helpPanel(jPanel, l);
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.gridx = GridBagConstraints.LINE_END;
        constraints.ipadx = 100;
        constraints.ipady = 80;
        container.add(help, constraints);

        setVisible(true);
        System.out.println("Конец игры...");
    }

    private void helpPanel(JComponent jComponent, LightweightRect l) {
        //работа с кругом
        help.add(countCircle);
        addCircle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                countIntermediate++;
                supplementCircle(l, jComponent);
                countCircle.setText("Промежуточные точки: " + countIntermediate);
            }
        });

        help.add(addCircle);
        removeCircle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (countIntermediate >= 1) {
                    countIntermediate--;
                    deleteCircle(l, jComponent);
                }

                countCircle.setText("Промежуточные точки: " + countIntermediate);
            }
        });
        help.add(removeCircle);
//-------------------------------------------------------------------------------------------------------------------------
        //работа с прямоугольником
        help.add(countRectangle);
        addRectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                countRectangles++;
                setAddRectangle(l, jComponent);
                countRectangle.setText("Прямоугольников: " + countRectangles);
            }
        });

        help.add(addRectangle);
        removeRectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (countRectangles >= 1) {
                    countRectangles--;
                    deleteRectangles(l, jComponent);
                }

                countRectangle.setText("Прямоугольников: " + countRectangles);
            }
        });
        help.add(removeRectangle);
//-----------------------------------------------------------------------------------------------------------------------

        //размер квадрата
        help.add(textSizeSquare);
        help.add(sizeSquare);
        saveSizeSquare.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                try {
                    splittingX = Integer.parseInt(sizeSquare.getText());
                    splittingY = Integer.parseInt(sizeSquare.getText());
                    jComponent.repaint();
                } catch (Exception exception) {
                    ErrorConsole error = new ErrorConsole();
                    error.error("Не верное значение");
                }
            }
        });
        help.add(saveSizeSquare);
//-----------------------------------------------------------------------------------------------------------------------

        //размер робота
        help.add(textRadiusRobot);
        help.add(radiusRobot);
        saveRadiusRobot.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                try {
                    radius = Integer.parseInt(radiusRobot.getText());
                    jComponent.repaint();
                } catch (Exception exception) {
                    ErrorConsole error = new ErrorConsole();
                    error.error("Не верное значение");
                }
            }
        });
        help.add(saveRadiusRobot);
//-----------------------------------------------------------------------------------------------------------------------

        //построение пути
        help.add(testWay);
        way.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    LightweightRect.travelTime = false;
                } else {
                    LightweightRect.travelTime = true;
                    jComponent.repaint();

                    if (isNoWay) {
                        isNoWay = false;
                        LightweightRect.travelTime = false;
                        ErrorConsole error = new ErrorConsole();
                        error.error("Не возможно построить путь");
                    }

                    jComponent.repaint();
                }

                jComponent.repaint();
            }

        });

        help.add(way);

        //-----------------------------------------------------------------------------------------------------------------------
        help.add(testSaveFile);

        saveFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                WorkingFile workingFile = new WorkingFile();
                workingFile.save();
            }
        });
        help.add(saveFile);
        //-----------------------------------------------------------------------------------------------------------------------

        help.add(textReadingFile);

        readingFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                for (int i = 1; i < circles.size() - 1; i++) {
                    circles.remove(i);
                }

                rectangles.clear();

                WorkingFile workingFile = new WorkingFile();
                workingFile.reading();

                setAddRectangle(l, jComponent);
                supplementCircle(l, jComponent);

                countCircle.setText("Промежуточные точки: " + countIntermediate);
                countRectangle.setText("Прямоугольников: " + countRectangles);
            }
        });

        help.add(readingFile);
    }

    private void deleteRectangles(LightweightRect l, JComponent jComponent) {
        rectangles.remove(rectangles.size() - 1);
        l.rectangles = rectangles;
        jComponent.repaint();
    }

    private void setAddRectangle(LightweightRect l, JComponent jComponent) {
        randRectangle();
        if (rectangles.size() % 2 == 0) {
            rectangles.add(new Graphs.Rectangle(x, y, height, width));
        } else {
            rectangles.add(new Graphs.Rectangle(x, y, width, height));
        }

        l.rectangles = rectangles;
        jComponent.repaint();
    }

    private void randRectangle() {
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

    private void supplementCircle(LightweightRect l, JComponent jComponent) {
        randCircle();
        circles.add(circles.size() - 1, new Circle(x, y, radius, Circle.POINT_INTERMEDIATE));
        l.circles = circles;
        jComponent.repaint();
    }

    private void randCircle() {
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

    private void isRectangleDragged(MouseEvent e, MouseAdapter window, JComponent jComponent) {
        for (int i = 0; i < rectangles.size(); i++) {
            Graphs.Rectangle rectangle = rectangles.get(i);

            if (rectangle.moving) {
                double newRectangleX = e.getX() - pressMousePoint.x + oldDraggedPositionPoint.x;
                double newRectangleY = e.getY() - pressMousePoint.y + oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newRectangleX / splittingX);
                newRectangleX = splittingX * pointAttractionX;

                long pointAttractionY = Math.round(newRectangleY / splittingY);
                newRectangleY = splittingY * pointAttractionY;

                if (newRectangleX + rectangle.w < fieldSize + splittingY && newRectangleY + rectangle.h < fieldSize-2*splittingY && newRectangleX >= 0 && newRectangleY >= 0) {
                    rectangle.point.setLocation(newRectangleX, newRectangleY);
                    jComponent.repaint();
                }
            }
        }
    }

    private void isCircleDragged(MouseEvent e, MouseAdapter window, JComponent jComponent) {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1 = circles.get(i);

            if (circle1.moving) {
                double newCircleX = e.getX() - pressMousePoint.x + oldDraggedPositionPoint.x;
                double newCircleY = e.getY() - pressMousePoint.y + oldDraggedPositionPoint.y;

                long pointAttractionX = Math.round(newCircleX / splittingX);
                newCircleX = splittingX * pointAttractionX;

                long pointAttractionY = Math.round(newCircleY / splittingY);
                newCircleY = splittingY * pointAttractionY;

                if (newCircleX <= fieldSize - 2 * radius && newCircleY <= fieldSize - 5 * radius && newCircleX >= 0 && newCircleY >= 0 && isExistingPoint(newCircleX, newCircleY)) {
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

        for (int i = 0; i < countRectangles; i++) {
            if (i % 2 == 0) {
                rectangles.add(new Graphs.Rectangle(40, u, height, width));
                u += height;
            } else {
                rectangles.add(new Graphs.Rectangle(40, o, width, height));
                o += width;
            }
        }

        l.rectangles = rectangles;
    }

    private void deleteCircle(LightweightRect l, JComponent jComponent) {
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





