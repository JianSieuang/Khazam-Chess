import java.awt.event.*;

import javax.swing.JButton;

public class SettingController {

    public static ActionListener createSoundButtonListener(JButton soundButton) {
        return e -> {
            boolean newState = !SettingManager.isEnabledSound();
            SettingManager.setEnabledSound(newState);

            if (soundButton != null) {
                // update the button text
                soundButton.setText(SettingUtils.getOnOffLabel(newState));

                // update button background color
                SettingUtils.setButtonDesign(soundButton, SettingManager.isEnabledSound());
            }

            // play click sound
            new BtnSound("click").actionPerformed(null);

            // save settings if "Save as default" is enabled
            if (SettingManager.isSaveSettingPermanently()) {
                SettingManager.saveSetting();
            }
        };
    }

    public static ActionListener createSaveSettingButtonListener(JButton saveSettingButton) {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);

            if (saveSettingButton != null) {
                // update the button text
                saveSettingButton.setText(SettingUtils.getOnOffLabel(newState));

                // update button background color
                SettingUtils.setButtonDesign(saveSettingButton, SettingManager.isSaveSettingPermanently());
            }

            new BtnSound("click").actionPerformed(null); // play click sound

            // if "Save as default" is checked, save settings
            if (newState) {
                SettingManager.saveSetting();
            } else {
                SettingManager.deleteSettingFile();
            }
        };
    }
}
