import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

/*
* View, a class that extends JButton to create a custom button with rounded corners
* custom button UI
*/
public class RoundedButtonUI extends BasicButtonUI {
    // set the border radius
    private static final int BORDER_RADIUS = 30;
    
    /* paint the button when be pressed */
    @Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        // convert to Graphics2D for better control over drawing
        Graphics2D g2 = (Graphics2D) g;
        
        // smooth the edges for a better look
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // use a darker color to shot the button is pressed
        g2.setColor(b.getBackground().darker());
        
        // draw a rounded retangle
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), BORDER_RADIUS, BORDER_RADIUS);
    }

    /* paint the button in notmal state */
    @Override
    public void paint(Graphics g, JComponent c) {
        // convert to Graphics2D for better control over drawing
        Graphics2D g2 = (Graphics2D) g;
        
        // smooth the edges for a better look
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // convert to JButton for easy to control the button
        JButton button = (JButton) c;
        
        // set the color to the button's background color
        g2.setColor(button.getBackground());
        
        // set the border radius to the button
        g2.fillRoundRect(0, 0, button.getWidth(), button.getHeight(), BORDER_RADIUS, BORDER_RADIUS);

        // call the original paint method to draw extra things
        super.paint(g, c);
    }
}
