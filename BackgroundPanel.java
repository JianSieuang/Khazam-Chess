import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * View, custom JPanel to displaye the background image with change in accordingly to the timer
 */
public class BackgroundPanel extends JPanel {
    private int imageIndex = 0; // track current backgroumnd image index
    private Timer imageTimer; // timer to change background image
    private String pageType; // type of page

    // constructor for background panel
    // initialize the layout, border, and start the background image timer
    // type = home page or setting page
    public BackgroundPanel(String type) {
        super(); // call super constructor
        setLayout(new BorderLayout()); // set layout to border layout
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // add margin around the panel
        startBackgroundImageTimer(); // call function start timer to change the background image
        this.pageType = type; // store the page type
    }

    // start the timer to change the background image
    private void startBackgroundImageTimer() {
        imageTimer = new Timer(5000, e -> { // timer with 5 seconds delay
            imageIndex++; // increase the image based on the current index and page type
            repaint(); // repaint the panel to update the background panel
        });
        imageTimer.start(); // start the timer
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // call super paint component ro ensure the proper rendering
        BufferedImage backgroundImage = BackgroundImage.getImageByIndex(imageIndex, pageType); // get the background
                                                                                               // image based on the
                                                                                               // index and page type

        // if the background image is not null then draw the image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // draw the image to the panel
        }
    }
}