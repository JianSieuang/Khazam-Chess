import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
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
