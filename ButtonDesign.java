import javax.swing.*;
import java.awt.*;

public class ButtonDesign {

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, JPanel panel, Runnable onClickAction) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Arial", Font.BOLD, 17));

        // dynamic change button size
        adjustButtonSize(button, panel);

        // set button hover and click effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null); // play sound when hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.BLACK);
            }
        });

        // button clicked action
        button.addActionListener(e -> {
            new BtnSound("click").actionPerformed(null); // play click sound
            onClickAction.run();
        });

        // dynamic change button size when panel resized
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                adjustButtonSize(button, panel);
                button.revalidate();
                button.repaint();
            }
        });

        return button;
    }

    // method to adjust button size based on panel width
    private static void adjustButtonSize(JButton button, JPanel panel) {
        int panelWidth = panel.getWidth();
        int buttonWidth = (int) (panelWidth * 0.4); // button will be panel 40% width
        int buttonHeight = 50; // fixed height
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
    }
}
