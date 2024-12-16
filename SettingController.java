import javax.swing.*;
import java.awt.event.*;

public class SettingController {

    public static ActionListener createSoundCheckBoxListener(JCheckBox soundCheckBox) {
        return e -> {
            SettingManager.setEnabledSound(soundCheckBox.isSelected());

            // play click sound
            new BtnSound("click").actionPerformed(null);

            // save settings if "Save as default" is enabled
            if (SettingManager.isSaveSettingPermanently()) {
                SettingManager.saveSetting();
            }
        };
    }

    public static ActionListener createSaveSettingCheckBoxListener(JCheckBox saveSettingCheckBox) {
        return e -> {
            SettingManager.setSaveSettingPermanently(saveSettingCheckBox.isSelected());

            // if "Save as default" is checked, save settings
            if (saveSettingCheckBox.isSelected()) {
                new BtnSound("click").actionPerformed(null); // play click sound
                SettingManager.saveSetting();
            } else {
                SettingManager.deleteSettingFile();
            }
        };
    }
}
