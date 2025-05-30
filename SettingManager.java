import java.awt.Color;
import java.io.*;
import java.util.Scanner;

// Lew Kien Yew
/*
 Model, this class is to manage application settings such as button sound, music sound, and colors
 Settings are saved to and loaded from a file named "setting.txt"
 */
public class SettingManager {
    // naming for setting file
    private static final String setting_file = "setting.txt";

    // new field for button sound
    private static boolean enabledButtonSound = true;

    // new field for music sound
    private static boolean enabledMusicSound = true;

    // save setting default
    private static boolean saveSettingPermanently = false;

    // default primary color
    private static Color primaryColor = Color.WHITE;

    // default secondary color
    private static Color secondaryColor = Color.BLACK;

    // load settiing from setting.txt file, if don't exist create a default setting
    // file
    public static void loadSetting() {
        File file = new File(setting_file);

        // check if the file exists
        if (file.exists()) {

            // open setting.txt file
            try (Scanner scanner = new Scanner(file)) {

                // read the file line by line
                while (scanner.hasNextLine()) {
                    // read the line
                    String line = scanner.nextLine();

                    // check the line and set the value
                    if (line.startsWith("Button Sound")) {
                        enabledButtonSound = line.split(" : ")[1].trim().equalsIgnoreCase("On");
                    } else if (line.startsWith("Music Sound")) {
                        enabledMusicSound = line.split(" : ")[1].trim().equalsIgnoreCase("On");
                    } else if (line.startsWith("Save as default")) {
                        String saveValue = line.split(" : ")[1].trim();
                        saveSettingPermanently = saveValue.equalsIgnoreCase("On");
                    } else if (line.startsWith("Primary Color")) {
                        primaryColor = Color.decode(line.split(" : ")[1].trim());
                    } else if (line.startsWith("Secondary Color")) {
                        secondaryColor = Color.decode(line.split(" : ")[1].trim());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            saveSetting();
        }
    }

    // save setting to setting.txt file
    public static void saveSetting() {
        // open setting.txt file
        try (PrintWriter writer = new PrintWriter(setting_file)) {
            writer.println("Button Sound : " + (enabledButtonSound ? "On" : "Off"));
            writer.println("Music Sound : " + (enabledMusicSound ? "On" : "Off"));
            writer.println("Save as default : " + (saveSettingPermanently ? "On" : "Off"));
            writer.println("Primary Color : " + String.format("#%06X", primaryColor.getRGB() & 0xFFFFFF));
            writer.println("Secondary Color : " + String.format("#%06X", secondaryColor.getRGB() & 0xFFFFFF));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // delete setting.txt file
    public static void deleteSettingFile() {
        File file = new File(setting_file);

        // if the file exist and not deleted, print error message
        if (file.exists() && !file.delete()) {
            System.err.println("Failed to delete " + setting_file);
        }
    }

    /*
     * before quit the program, check the save as default value
     * if not save as default then it will auto change button & music sound be on
     */
    public static void checkBeforeQuit() {
        if (!saveSettingPermanently) {

            // set the sound 'on'
            enabledButtonSound = true;

            // set the music 'on'
            enabledMusicSound = true;
            saveSetting();
        }
    }

    public static boolean isEnabledButtonSound() {
        return enabledButtonSound;
    }

    public static boolean isEnabledMusicSound() {
        return enabledMusicSound;
    }

    public static boolean isSaveSettingPermanently() {
        return saveSettingPermanently;
    }

    public static void setEnabledButtonSound(boolean enabled) {
        enabledButtonSound = enabled;
    }

    public static void setEnabledMusicSound(boolean enabled) {
        enabledMusicSound = enabled;
    }

    public static void setSaveSettingPermanently(boolean saveSettingPermanently) {
        SettingManager.saveSettingPermanently = saveSettingPermanently;
    }

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    public static Color getSecondaryColor() {
        return secondaryColor;
    }

    public static void setPrimaryColor(Color color) {
        primaryColor = color;
    }

    public static void setSecondaryColor(Color color) {
        secondaryColor = color;
    }
}