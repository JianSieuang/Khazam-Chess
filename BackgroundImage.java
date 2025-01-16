import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class BackgroundImage {
    // landing page background images
    private static final String[] landingImagePaths = {
        "Picture/landing_page_bg_image_1.png",
        "Picture/landing_page_bg_image_2.png",
    };

    // setting page background images
    private static final String[] settingImagePaths = {
        "Picture/setting_page_bg_image_1.png",
        "Picture/setting_page_bg_image_2.png", 
    };

    // get image by index
    public static BufferedImage getImageByIndex(int index, String pageType) {
        // switch case to determine the image paths
        String[] imagePaths = switch (pageType.toLowerCase()) {
            case "home_page" -> landingImagePaths;
            case "setting_page" -> settingImagePaths;
            default -> landingImagePaths;
        };

        try {
            return ImageIO.read(new File(imagePaths[index % imagePaths.length]));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image by index: " + index);
            return null;
        }
    }
}