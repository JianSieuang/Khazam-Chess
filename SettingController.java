import javax.swing.*;
import java.awt.event.ActionListener;

public class SettingController {

    private SettingView view;

    public SettingController() {
        this.view = new SettingView(this);
        SettingManager.loadSetting();
        initializeListeners();
    }

    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose();
        view.showSetting(currentFrame);
    }

    private void initializeListeners() {
        view.getSoundButton().addActionListener(createSoundButtonListener());
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener());
        view.getBackButton().addActionListener(e -> {
            new BtnSound("click").actionPerformed(null);
            returnToLandingPage();
        });
    }

    private ActionListener createSoundButtonListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledSound();
            SettingManager.setEnabledSound(newState);
            view.updateSoundButton(view.getSoundButton(), newState);

            new BtnSound("click").actionPerformed(null);
            SettingManager.saveSetting();
        };
    }

    private ActionListener createSaveSettingButtonListener() {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);

            view.updateSaveSettingButton(view.getSaveSettingButton(), newState);

            new BtnSound("click").actionPerformed(null);

            if (newState) {
                SettingManager.saveSetting();
            } else {
                SettingManager.deleteSettingFile();
            }
        };
    }

    public boolean getIsSoundEnabled() {
        return SettingManager.isEnabledSound();
    }

    public boolean getIsSaveSettingPermanently() {
        return SettingManager.isSaveSettingPermanently();
    }

    public void checkSetting() {
        SettingManager.checkBeforeQuit();
    }

    public void returnToLandingPage() {
        new HomePageController();
    }
}