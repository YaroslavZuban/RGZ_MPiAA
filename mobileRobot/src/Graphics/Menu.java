package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu {
    private static JButton play = new JButton("Играть");
    private static JButton saveCoordinates = new JButton("Правила");
    private static JButton exit = new JButton("Выход");

    public static JFrame windowPatch = new JFrame("Мобильный робот движется по плоскости");

    public static void main(String[] args) {
        windowPatch.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        windowPatch.setSize(300, 250);
        windowPatch.setResizable(false);
        windowPatch.setLocationRelativeTo(null);

        play.setPreferredSize(new Dimension(180, 60));
        play.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Game game = new Game();
                windowPatch.setVisible(false);
            }
        });

        saveCoordinates.setPreferredSize(new Dimension(180, 60));
        saveCoordinates.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Rules rules = new Rules();
                windowPatch.setVisible(false);
            }
        });

        exit.setPreferredSize(new Dimension(180, 60));
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.exit(0);
            }
        });

        windowPatch.setLayout(new BorderLayout(20, 15));
        windowPatch.add(play, BorderLayout.NORTH);
        windowPatch.add(exit, BorderLayout.SOUTH);
        windowPatch.add(saveCoordinates, BorderLayout.CENTER);

        windowPatch.setVisible(true);
    }
}
