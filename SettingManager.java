import java.io.*;
import java.util.Scanner;

public class SettingManager {
    
    //  global Varible
    private static final String setting_file = "setting.txt";
    private static boolean enabledSound = true;
    private static boolean saveSettingPermanently = false;

    //  load setting from the setting.txt file
    //  if file exist, read and update the setting
    //  if file not exist, it create a new file with default value
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
            // if file does not exist, create a new one with the default values
            saveSetting();
        }
    }

    //  save current setting to setting.txt when "Save as default" checked
    //  if setting.txt exist, it will overwrite the setting.txt
    public static void saveSetting() {
        try (PrintWriter writer = new PrintWriter(setting_file)) {
            //  write sound setting to the setting.txt
            writer.println("Sound : " + (enabledSound ? "On" : "Off"));
            writer.println("Save as default : " + (saveSettingPermanently ? "On" : "Off"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // delete setting.txt, if exist
    public static void deleteSettingFile() {
        File file = new File(setting_file);
        if (file.exists()) {
            if (!file.delete()) {
                System.err.println("Failed to delete " + setting_file);
            }
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
}
