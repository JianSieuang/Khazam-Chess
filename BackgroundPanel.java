import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundPanel extends JPanel {

    private int imageIndex = 0;
    private Timer imageTimer;
    private String pageType;

    public BackgroundPanel(String type) {
        startBackgroundImageTimer();
        this.pageType = type;
    }

    private void startBackgroundImageTimer() {
        imageTimer = new Timer(5000, e -> {
            imageIndex++;
            repaint(); // repaint the panel
        });
        imageTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage backgroundImage = BackgroundImage.getImageByIndex(imageIndex, pageType);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}