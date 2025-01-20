import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SettingController {
    private static SettingController controller;
    private SettingView view;

    private SettingController() {
        this.view = new SettingView(this);
        AudioPlayer.setSettingController(this);
        SettingManager.loadSetting();
        initializeListeners();
    }
    
        
    public static SettingController getController()
    {
        if(controller == null)
        {
            controller = new SettingController();
        }
        else
        {
            controller.view = new SettingView(controller);
            controller.initializeListeners();
        }
        return controller;
    }
    

    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose();
        view.showSetting(currentFrame);
        addWindowListener();
    }

    private void initializeListeners() {
        view.getSoundButton().addActionListener(createSoundButtonListener());
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener());
        view.getBackButton().addActionListener(e -> {
            view.getSettingFrame().dispose();
            new BtnSound("click", SettingController.this).actionPerformed(null);
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
            view.updateButton(view.getSoundButton(), newState);

            if (newState) {
                AudioPlayer.playBackgroundMusic();
            } else {
                AudioPlayer.stopBackgroundMusic();
            }

            new BtnSound("click", SettingController.this).actionPerformed(null);
            SettingManager.saveSetting();
        };
    }

    private ActionListener createSaveSettingButtonListener() {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);

            view.updateButton(view.getSaveSettingButton(), newState);

            new BtnSound("click", SettingController.this).actionPerformed(null);

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
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(button.getBackground().brighter());
                button.setForeground(Color.BLACK);
            }
        });
    }

    private void addWindowListener() {
        JFrame settingsFrame = view.getSettingFrame();
        settingsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new BtnSound("click", SettingController.this).actionPerformed(null);
                returnToLandingPage();
                settingsFrame.dispose();
            }
        });
    }

    public boolean getIsSoundEnabled() {
        return SettingManager.isEnabledSound();
    }

    public boolean getIsSaveSettingPermanently() {
        return SettingManager.isSaveSettingPermanently();
    }

    public void runLoadSetting() {
        SettingManager.loadSetting();
    }

    public void checkSetting() {
        SettingManager.checkBeforeQuit();
    }

    public void returnToLandingPage() {
        HomePageController.getController();
    }
}