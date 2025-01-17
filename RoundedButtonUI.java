import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

public class RoundedButtonUI extends BasicButtonUI {
    private static final int BORDER_RADIUS = 30; // border radius

    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(b.getBackground().darker());
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), BORDER_RADIUS, BORDER_RADIUS);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        JButton button = (JButton) c;
        g2.setColor(button.getBackground());
        g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), BORDER_RADIUS, BORDER_RADIUS);

        super.paint(g, c);
    }
}
