import java.io.*;
import java.util.Scanner;

public class SettingManager {

    private static final String setting_file = "setting.txt";
    private static boolean enabledSound = true;
    private static boolean saveSettingPermanently = false;

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
        AudioPlayer.playBackgroundMusic();
    }

    public static void setSaveSettingPermanently(boolean saveSettingPermanently) {
        SettingManager.saveSettingPermanently = saveSettingPermanently;
    }
}