import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingController {

    public SettingController() {
        SettingManager.loadSetting();
    }

    public ActionListener createSoundButtonListener(JButton soundButton) {
        return e -> {
            boolean newState = !SettingManager.isEnabledSound();
            SettingManager.setEnabledSound(newState);

            // update UI
            SettingView.updateSoundButton(soundButton, newState);

            new BtnSound("click").actionPerformed(null);
            SettingManager.saveSetting();
        };
    }

    public ActionListener createSaveSettingButtonListener(JButton saveSettingButton) {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);

            // update UI
            SettingView.updateSaveSettingButton(saveSettingButton, newState);

            new BtnSound("click").actionPerformed(null);

            if (newState) {
                SettingManager.saveSetting();
            } else {
                SettingManager.deleteSettingFile();
            }
        };
    }

    public void returnToLandingPage() {
        new HomePageController();
    }

    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose();
        SettingView.showSetting(currentFrame, this);
    }

    public void checkSetting() {
        SettingManager.checkBeforeQuit();
    }
}