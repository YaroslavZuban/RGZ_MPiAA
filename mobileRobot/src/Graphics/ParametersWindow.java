package Graphics;

import Graphs.Circle;
import Graphs.LightweightRect;
import Graphs.WorkingFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Graphics.Game.*;

public class ParametersWindow extends JPanel {
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


    public ParametersWindow(JComponent jComponent, LightweightRect l) {
        setLayout(new GridLayout(20, 1));

        workPoints(jComponent, l);

        workingRectangle(jComponent, l);

        squareSize(jComponent);

        robotSize(jComponent);

        pathBuilding(jComponent);
        
        conservationFile();

        download(jComponent, l);

    }

    private void download(JComponent jComponent, LightweightRect l) {
        add(textReadingFile);

        readingFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                WorkingFile workingFile = new WorkingFile();
                workingFile.readingCircle(circles);
                workingFile.readingRectangle(rectangles);
                workingFile.readingSizeSquare();

                radius = circles.get(0).radius;
                l.circles = circles;

                l.rectangles = rectangles;

                countCircle.setText("Промежуточные точки: " + countIntermediate);
                countRectangle.setText("Прямоугольников: " + countRectangles);
                jComponent.repaint();
            }
        });

        add(readingFile);
    }

    private void conservationFile() {
        add(testSaveFile);

        saveFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                WorkingFile workingFile = new WorkingFile();
                workingFile.saveCircle(Game.circles);
                workingFile.saveRectangle(Game.rectangles);
                workingFile.saveSizeSquare(splittingX);
            }
        });
        add(saveFile);
    }

    private void pathBuilding(JComponent jComponent) {
        add(testWay);
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

        add(way);
    }

    private void robotSize(JComponent jComponent) {
        add(textRadiusRobot);
        add(radiusRobot);
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
        add(saveRadiusRobot);
    }

    private void squareSize(JComponent jComponent) {
        add(textSizeSquare);
        add(sizeSquare);
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
        add(saveSizeSquare);
    }

    private void workingRectangle(JComponent jComponent, LightweightRect l) {
        add(countRectangle);
        addRectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                countRectangles++;
                Game.setAddRectangle(l, jComponent);
                countRectangle.setText("Прямоугольников: " + countRectangles);
            }
        });

        add(addRectangle);
        removeRectangle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (countRectangles >= 1) {
                    countRectangles--;
                    Game.deleteRectangles(l, jComponent);
                }

                countRectangle.setText("Прямоугольников: " + countRectangles);
            }
        });
        add(removeRectangle);
    }

    private void workPoints(JComponent jComponent, LightweightRect l) {
        add(countCircle);
        addCircle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                countIntermediate++;
                supplementCircle(l, jComponent);
                countCircle.setText("Промежуточные точки: " + countIntermediate);
            }
        });

        add(addCircle);
        removeCircle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (countIntermediate >= 1) {
                    countIntermediate--;
                    Game.deleteCircle(l, jComponent);
                }

                countCircle.setText("Промежуточные точки: " + countIntermediate);
            }
        });
        add(removeCircle);
    }

    private void supplementCircle(LightweightRect l, JComponent jComponent) {
        Game.randCircle();
        circles.add(circles.size() - 1, new Circle(Game.x, Game.y, radius, Circle.POINT_INTERMEDIATE));
        l.circles = circles;
        jComponent.repaint();
    }
}
