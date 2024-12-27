import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BackgroundImage {
    // image paths
    private static final String[] imagePaths = {
        "sources/bg_image_1.jpeg", "sources/bg_image_2.png"
    };

    // get image by index
    public static BufferedImage getImageByIndex(int index) {
        try {
            return ImageIO.read(new File(imagePaths[index % imagePaths.length]));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image by index: " + index);
            return null;
        }
    }
}