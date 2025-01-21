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

    public static SettingController getController() {
        if (controller == null) {
            controller = new SettingController();
        } else {
            controller.view = new SettingView(controller);
            controller.initializeListeners();
        }
        return controller;
    }

    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose();
        view.updateButton(view.getSoundButton(), SettingManager.isEnabledSound());
        view.showSetting(currentFrame);
        addWindowListener();
    }

    private void initializeListeners() {
        view.getSoundButton().addActionListener(createSoundButtonListener());
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener());
        view.getPrimaryColorButton().addActionListener(createColorPickerListener(true));
        view.getSecondaryColorButton().addActionListener(createColorPickerListener(false));
        view.getBackButton().addActionListener(e -> {
            view.getSettingFrame().dispose();
            new BtnSound("click", SettingController.this).actionPerformed(null);
            returnToLandingPage();
        });

        addHoverSound(view.getSoundButton());
        addHoverSound(view.getSaveSettingButton());
        addHoverSound(view.getBackButton());

        addColorPickerHoverSound(view.getPrimaryColorButton(), true);
        addColorPickerHoverSound(view.getSecondaryColorButton(), false);
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

    private void addColorPickerHoverSound(JButton button, boolean isPrimary) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                if (isPrimary) {
                    button.setBackground(getPrimaryColor().darker());
                } else {
                    button.setBackground(getSecondaryColor().darker());
                }
                button.setForeground(Color.WHITE);
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isPrimary) {
                    button.setBackground(getPrimaryColor().brighter());
                } else {
                    button.setBackground(getSecondaryColor().brighter());
                }
                button.setForeground(Color.BLACK);
            }
        });
    }

    private ActionListener createColorPickerListener(boolean isPrimary) {
        new BtnSound("click", SettingController.this).actionPerformed(null);

        return e -> {
            Color initialColor = isPrimary ? SettingManager.getPrimaryColor() : SettingManager.getSecondaryColor();
            Color newColor = JColorChooser.showDialog(view.getPrimaryColorButton(), "Choose a color", initialColor);
            if (newColor != null) {
                if (isPrimary) {
                    SettingManager.setPrimaryColor(newColor);
                    view.getPrimaryColorButton().setBackground(newColor);
                } else {
                    SettingManager.setSecondaryColor(newColor);
                    view.getSecondaryColorButton().setBackground(newColor);
                }
                SettingManager.saveSetting();
            }
        };
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

    public Color getPrimaryColor() {
        return SettingManager.getPrimaryColor();
    }

    public Color getSecondaryColor() {
        return SettingManager.getSecondaryColor();
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