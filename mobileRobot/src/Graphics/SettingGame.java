package Graphics;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class SettingGame extends JFrame {
    private Integer countIntermediate = 0;
    private Integer countRectangles = 0;

    private static JLabel intermediatePoints = new JLabel("Введите число промежуточных точек");
    private static JTextField countIntermediatePoints = new JTextField("0", 10);

    private static JLabel obstacles = new JLabel("Введите число препядствий");
    private static JTextField countObstacles = new JTextField("0", 10);

    private static JButton save = new JButton("Сохранить");
    private static JButton exit = new JButton("Назад");

    public SettingGame() {
        super("Настройка игры");
        this.setSize(500, 150);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2, 10, 10));

        container.add(intermediatePoints);
        container.add(countIntermediatePoints);

        container.add(obstacles);
        container.add(countObstacles);

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    countIntermediate = Integer.parseInt(countIntermediatePoints.getText());
                    if (countIntermediate >= 4 || countIntermediate < 0) {
                        throw new Exception();
                    }

                    countRectangles = Integer.parseInt(countObstacles.getText());

                    if (countRectangles >= 4 || countRectangles < 0) {
                        throw new Exception();
                    }

                    Game game = new Game(countIntermediate, countRectangles);
                } catch (Exception exception) {
                    errorConsole();
                }
                setVisible(false);
            }
        });
        container.add(save);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Menu.windowPatch.setVisible(true);
                setVisible(false);
            }
        });
        container.add(exit);

    }


    private JDialog createDialog(String title, boolean modal) {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(180, 90);
        return dialog;
    }

    private void errorConsole() {
        JDialog dialog = createDialog("Ошибка", true);
        dialog.setLocationRelativeTo(null);

        JLabel l = new JLabel("     Не верное значение");
        JButton b = new JButton("ок");

        dialog.add(l, BorderLayout.NORTH);

        b.addActionListener(e -> {
            dialog.setVisible(false);
        });

        b.setSize(20, 10);
        dialog.add(b, BorderLayout.SOUTH);
        dialog.setVisible(true);

    }
}
