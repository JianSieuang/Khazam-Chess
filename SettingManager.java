import java.awt.Color;
import java.io.*;
import java.util.Scanner;

public class SettingManager {

    private static final String setting_file = "setting.txt";
    private static boolean enabledSound = true;
    private static boolean saveSettingPermanently = false;
    private static Color primaryColor = Color.WHITE;
    private static Color secondaryColor = Color.BLACK;

    public static void loadSetting() {
        File file = new File(setting_file);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith("Sound")) {
                        String soundValue = line.split(" : ")[1].trim();
                        enabledSound = soundValue.equalsIgnoreCase("On");
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

    public static void saveSetting() {
        try (PrintWriter writer = new PrintWriter(setting_file)) {
            writer.println("Sound : " + (enabledSound ? "On" : "Off"));
            writer.println("Save as default : " + (saveSettingPermanently ? "On" : "Off"));
            writer.println("Primary Color : " + String.format("#%06X", primaryColor.getRGB() & 0xFFFFFF));
            writer.println("Secondary Color : " + String.format("#%06X", secondaryColor.getRGB() & 0xFFFFFF));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSettingFile() {
        File file = new File(setting_file);
        if (file.exists() && !file.delete()) {
            System.err.println("Failed to delete " + setting_file);
        }
    }

    public static void checkBeforeQuit() {
        if (!saveSettingPermanently) {
            enabledSound = true; // set the sound 'on'
            saveSetting();
        }
    }

    public static boolean isEnabledSound() {
        return enabledSound;
    }

    public static boolean isSaveSettingPermanently() {
        return saveSettingPermanently;
    }

    public static void setEnabledSound(boolean enabledSound) {
        SettingManager.enabledSound = enabledSound;
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