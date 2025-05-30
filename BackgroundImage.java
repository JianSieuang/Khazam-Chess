import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

// Lew Kien Yew
/*
 * utility class to get background image based on the index and page type
 */
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
    
    private static final String[] gameImagePaths = {
            "Picture/landing_page_bg_image_1.png",
            "Picture/landing_page_bg_image_2.png",
    };
    // get image by index
    // index = index of the image to retrieve.
    // pageType = type of page ("home_page", "setting_page") to determine which set
    // of images to use.
    // a BufferedImage object representing the image, or null if an error occurs.

    public static BufferedImage getImageByIndex(int index, String pageType) {
        // switch case to determine the image paths
        String[] imagePaths = switch (pageType.toLowerCase()) {
            // use landing page images
            case "home_page" -> landingImagePaths; 
            
            // use setting page images
            case "setting_page" -> settingImagePaths;
            
            // use game page images
            case "game_page" -> gameImagePaths;
            
            // default to landing page images
            default -> landingImagePaths;
        };

        /*
         * load and return thimage based on the given index
         * if the index exceeds the array length, it will loop back to the first image
         */
        try {
            return ImageIO.read(new File(imagePaths[index % imagePaths.length]));
        } catch (Exception e) { // catch exception if image not found
            e.printStackTrace();
            // print error message
            System.out.println("Error loading image by index: " + index);
            return null;
        }
    }
}