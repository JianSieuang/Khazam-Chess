import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Lew Kien Yew
/*
 * View, custom JPanel to displaye the background image with change in accordingly to the timer
 */
public class BackgroundPanel extends JPanel {
    // track current backgroumnd image index
    private int imageIndex = 0;
    
    // timer to change background image
    private Timer imageTimer;

    // type of page
    private String pageType;

    // constructor for background panel
    // initialize the layout, border, and start the background image timer
    // type = home page or setting page
    public BackgroundPanel(String type) {
        // call super constructor
        super();

        // set layout to border layout
        setLayout(new BorderLayout());

        // add margin around the panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // call function start timer to change the background image
        startBackgroundImageTimer();
        
        // store the page type
        this.pageType = type;
    }

    // start the timer to change the background image
    private void startBackgroundImageTimer() {
        // timer with 5 seconds delay
        imageTimer = new Timer(5000, e -> {
            // increase the image based on the current index and page type
            imageIndex++;
            
            // repaint the panel to update the background panel
            repaint();
        });
        // start the timer
        imageTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // call super paint component ro ensure the proper rendering
        super.paintComponent(g);

        // get the background image based on the index and page type
        BufferedImage backgroundImage = BackgroundImage.getImageByIndex(imageIndex, pageType);

        // if the background image is not null then draw the image
        if (backgroundImage != null) {
            // draw the image to the panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}