import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingController
{
    public SettingController() {
        SettingManager.loadSetting();
    }
    
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
            
            // save settings if "Save as default"
            SettingManager.setEnabledSound(newState);
            SettingManager.saveSetting();
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

    public void returnToLandingPage() {
        LandingPage.showMenu();
    }
    
    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose();
        SettingView.showSetting(currentFrame, this);
    }
    
    public void checkSetting() {
        SettingManager.checkBeforeQuit();
    }
}
