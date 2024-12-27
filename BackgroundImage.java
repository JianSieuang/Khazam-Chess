import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

public class BackgroundImage {

    // hold current image
    private static BufferedImage currentBackgroundImage;
    private static int currentImageIndex = 0;

    // image paths
    private static final String[] imagePaths = {
        "sources/bg_image_1.jpeg", "sources/bg_image_2.png"
    };

    static {
        loadCurrentImage();
    }

    // load current background image
    private static void loadCurrentImage() {
        try {
            currentBackgroundImage = ImageIO.read(new File(imagePaths[currentImageIndex]));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading background image. Ensure the image files exist in the correct directory.");
        }
    }

    // switch to next image
    private static void switchToNextImage() {
        currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
        try {
            currentBackgroundImage = ImageIO.read(new File(imagePaths[currentImageIndex]));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading background image during switch.");
        }
    }

    // change background image
    public static void startBackgroundImageTimer(JFrame frame, JLabel titleLabel) {
        // timer to switch background image
        Timer timer = new Timer(5000, e -> {
            switchToNextImage();

            // update the frame
            frame.repaint();

            // change the title color
            titleLabel.setForeground(getNextColor());
        });
        timer.start();
    }

    // change title color
    private static java.awt.Color getNextColor() {
        java.awt.Color[] colors = {
            new java.awt.Color(0, 0, 128),
            new java.awt.Color(255, 215, 0)
        };

        return colors[currentImageIndex % colors.length];
    }
    
    public static BufferedImage getCurrentBackgroundImage() {
        return currentBackgroundImage;
    }

    public static int getCurrentImageIndex() {
        return currentImageIndex;
    }
}
