import javax.swing.*;
import java.awt.*;

public class ButtonDesign {

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 17));

        // set the margin/padding based on the button text
        if (text.equals("New Game") || text.equals("Quit Game")) {
            button.setMargin(new Insets(5, 15, 5, 15)); // standard padding
        } else if (text.equals("Load Game")) {
            button.setMargin(new Insets(5, 11, 5, 11)); // specific padding for "Load Game"
        } else if (text.equals("Setting")) {
            button.setMargin(new Insets(5, 29, 5, 29)); // specific padding for "Setting"
        } else if (text.equals("Return")) {
            button.setMargin(new Insets(0, 0, 0, 0)); // standard padding
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            // when mouse hover to the btn
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null); // play hover sound
            }

            // when mouse away from the btn
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.BLACK);
            }
        });

        // when btn be cliked
        button.addActionListener(e -> {
            new BtnSound("click").actionPerformed(null); // play click sound
            onClickAction.run();
        });

        return button;
    }
}
