import javax.swing.*;
import java.awt.*;
import java.awt.Component;

public class ButtonDesign {
    // inner class to create rounded button
    private static class RoundedButton extends JButton {
        private int cornerRadius;
        private int margin = 5; // set btn margin

        public RoundedButton(String text, int cornerRadius) {
            super(text);
            this.cornerRadius = cornerRadius;
            setFocusPainted(false); // remove focus border
            setOpaque(false); // allow transparency
        }

        @Override
        protected void paintComponent(Graphics g) {
            // draw background
            g.setColor(getBackground());

            // adjust the rectangle to fit the margin (x, y, width, height, arcWidth, arcHeight)
            g.fillRoundRect(margin / 2, margin / 2, getWidth() - margin, getHeight() - margin, cornerRadius, cornerRadius);

            // draw text
            super.paintComponent(g);

            // no longer needed after the drawing operations are complete
            g.dispose();
        }
    }

    public static JButton createMenuButton(String text, Color backgroundColor, JFrame frame, JPanel panel, Runnable onClickAction) {
        RoundedButton button = new RoundedButton(text, 50); // set corner radius
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(backgroundColor);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 17));

        // remove default border
        button.setBorder(BorderFactory.createEmptyBorder());

        // apply hover effect
        button.addMouseListener(ButtonAction.createButtonHoverEffect(button, backgroundColor));

        // apply click action
        button.addActionListener(ButtonAction.createButtonClickAction(onClickAction));

        // add resize listener
        panel.addComponentListener(ButtonAction.createPanelResizeListener(button, panel));

        return button;
    }
}
