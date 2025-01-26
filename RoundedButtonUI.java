import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/*
    View, a class that extends JButton to create a custom button with rounded corners
*/
public class RoundedButtonUI extends BasicButtonUI { // custom button UI
    private static final int BORDER_RADIUS = 30; // border radius

    /* paint the button when be pressed */
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        Graphics2D g2 = (Graphics2D) g; // convert to Graphics2D for better control over drawing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth the edges for
                                                                                                 // a better look
        g2.setColor(b.getBackground().darker()); // use a darker color to shot the button is pressed
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), BORDER_RADIUS, BORDER_RADIUS); // draw a rounded retangle
    }

    /* paint the button in notmal state */
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g; // convert to Graphics2D for better control over drawing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth the edges for
                                                                                                 // a better look

        JButton button = (JButton) c; // convert to JButton for easy to control the button
        g2.setColor(button.getBackground()); // set the color to the button's background color
        g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), BORDER_RADIUS, BORDER_RADIUS); // set the border
                                                                                                     // radius to the
                                                                                                     // button

        super.paint(g, c); // call the original paint method to draw extra things
    }
}
