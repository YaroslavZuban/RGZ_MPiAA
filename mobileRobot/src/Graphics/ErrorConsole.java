package Graphics;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class ErrorConsole {
    private JDialog createDialog(String title, boolean modal) {
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.setSize(200, 90);
        return dialog;
    }

    public void error(String line) {
        JDialog dialog = createDialog(line, true);
        dialog.setLocationRelativeTo(null);

        JLabel l = new JLabel(line);
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
