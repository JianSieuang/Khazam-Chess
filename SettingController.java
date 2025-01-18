import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

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
            view.getSettingFrame().dispose();
            new BtnSound("click").actionPerformed(null);
            returnToLandingPage();
        });

        addHoverSound(view.getSoundButton());
        addHoverSound(view.getSaveSettingButton());
        addHoverSound(view.getBackButton());
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

    private void addHoverSound(JButton button) {
        Color originalBackgroundColor = button.getBackground();

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalBackgroundColor.darker());
                button.setForeground(Color.WHITE);
                new BtnSound("hover").actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackgroundColor);
                button.setForeground(Color.BLACK);
            }
        });
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