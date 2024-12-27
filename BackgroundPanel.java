import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundPanel extends JPanel {

    private int imageIndex = 0;
    private Timer imageTimer;

    public BackgroundPanel(JLabel titleLabel) {
        startBackgroundImageTimer(titleLabel);
    }

    private void startBackgroundImageTimer(JLabel titleLabel) {
        imageTimer = new Timer(5000, e -> {
            imageIndex++;
            repaint(); // repaint the panel
            titleLabel.setForeground(getNextColor());
        });
        imageTimer.start();
    }

    private Color getNextColor() {
        Color[] colors = {
            new Color(0, 0, 128),
            new Color(255, 215, 0)
        };

        return colors[imageIndex % colors.length];
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage backgroundImage = BackgroundImage.getImageByIndex(imageIndex);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}