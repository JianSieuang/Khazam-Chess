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
        view.updateButton(view.getButtonSoundButton(), SettingManager.isEnabledButtonSound());
        view.updateButton(view.getMusicSoundButton(), SettingManager.isEnabledMusicSound());
        view.showSetting(currentFrame);
        addWindowListener();
    }

    private void initializeListeners() {
        view.getButtonSoundButton().addActionListener(createButtonSoundListener());
        view.getMusicSoundButton().addActionListener(createMusicSoundListener());
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener());
        view.getPrimaryColorButton().addActionListener(createColorPickerListener(true));
        view.getSecondaryColorButton().addActionListener(createColorPickerListener(false));
        view.getBackButton().addActionListener(e -> {
            view.getSettingFrame().dispose();
            new BtnSound("click", SettingController.this).actionPerformed(null);
            returnToLandingPage();
        });

        addHoverSound(view.getButtonSoundButton());
        addHoverSound(view.getMusicSoundButton());
        addHoverSound(view.getSaveSettingButton());
        addHoverSound(view.getBackButton());

        addColorPickerHoverSound(view.getPrimaryColorButton(), true);
        addColorPickerHoverSound(view.getSecondaryColorButton(), false);
    }

    private ActionListener createButtonSoundListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledButtonSound();
            SettingManager.setEnabledButtonSound(newState);
            view.updateButton(view.getButtonSoundButton(), newState);
            SettingManager.saveSetting();
        };
    }

    private ActionListener createMusicSoundListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledMusicSound();
            SettingManager.setEnabledMusicSound(newState);
            view.updateButton(view.getMusicSoundButton(), newState);

            if (newState) {
                AudioPlayer.playBackgroundMusic();
            } else {
                AudioPlayer.stopBackgroundMusic();
            }

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
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boolean isOn = button.getText().equals("ON");
                button.setBackground(isOn ? new Color(144, 238, 144).darker() : new Color(240, 128, 128).darker());
                button.setForeground(Color.WHITE);
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boolean isOn = button.getText().equals("ON");
                button.setBackground(isOn ? new Color(144, 238, 144) : new Color(240, 128, 128));
                button.setForeground(Color.BLACK);
            }
        });
    }

    private void addColorPickerHoverSound(JButton button, boolean isPrimary) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color currentColor = isPrimary ? getPrimaryColor() : getSecondaryColor();

                if (isPureBlack(currentColor)) {
                    button.setBackground(new Color(80, 80, 80)); // Gray if black
                } else {
                    button.setBackground(currentColor.darker()); // Darken if not black
                }

                button.setForeground(Color.WHITE);
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Color currentColor = isPrimary ? getPrimaryColor() : getSecondaryColor();

                if (isPureBlack(currentColor)) {
                    button.setBackground(new Color(0, 0, 0)); // Black if black
                } else {
                    button.setBackground(currentColor); // Original color if not black
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

    private boolean isPureBlack(Color color) {
        return color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0;
    }

    public boolean getIsButtonSoundEnabled() {
        return SettingManager.isEnabledButtonSound();
    }

    public boolean getIsMusicSoundEnabled() {
        return SettingManager.isEnabledMusicSound();
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