import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * control setting view and model
 */
public class SettingController {
    private static SettingController controller; // signleton design pattern, instance of SettingController
    private SettingView view; // UI for setting page

    // constructor, to set up view, load setting, and initialize listeners
    private SettingController() {
        this.view = new SettingView(this); // create new SettingView
        AudioPlayer.setSettingController(this); // set setting controller for audio player
        SettingManager.loadSetting(); // load setting from setting.txt
        initializeListeners(); // initialize listeners for setting page
    }

    // Chong Jian Sieuang
    // get the singleton instance of the controller
    public static SettingController getController() {
        if (controller == null) {
            controller = new SettingController();
        } else {
            controller.view = new SettingView(controller);
            controller.initializeListeners();
        }
        return controller;
    }

    // navigate to setting page
    public void navigateToSettingPage(JFrame currentFrame) {
        currentFrame.dispose(); // close the current page
        view.updateButton(view.getButtonSoundButton(), SettingManager.isEnabledButtonSound()); // update button sound
                                                                                               // button
        view.updateButton(view.getMusicSoundButton(), SettingManager.isEnabledMusicSound()); // update music sound
                                                                                             // button
        view.showSetting(currentFrame); // display setting page
        addWindowListener(); // handle window close action
    }

    // initialize listeners for setting page
    private void initializeListeners() {
        view.getButtonSoundButton().addActionListener(createButtonSoundListener()); // button sound
        view.getMusicSoundButton().addActionListener(createMusicSoundListener()); // music sound
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener()); // save as default setting
        view.getPrimaryColorButton().addActionListener(createColorPickerListener(true)); // primary color picker
        view.getSecondaryColorButton().addActionListener(createColorPickerListener(false)); // secondary color picker
        view.getBackButton().addActionListener(e -> { // back button
            view.getSettingFrame().dispose(); // get setting view to close setting page
            new BtnSound("click", SettingController.this).actionPerformed(null); // play click sound
            returnToLandingPage(); // return to landing page
        });

        // add hover sound for all buttons
        addHoverSound(view.getButtonSoundButton());
        addHoverSound(view.getMusicSoundButton());
        addHoverSound(view.getSaveSettingButton());
        addHoverSound(view.getBackButton());

        addColorPickerHoverSound(view.getPrimaryColorButton(), true);
        addColorPickerHoverSound(view.getSecondaryColorButton(), false);
    }

    // action listener for button sound
    private ActionListener createButtonSoundListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledButtonSound();
            SettingManager.setEnabledButtonSound(newState);
            view.updateButton(view.getButtonSoundButton(), newState); // update button sound button
            SettingManager.saveSetting(); // call setting manager to save setting
        };
    }

    // action listener for music sound
    private ActionListener createMusicSoundListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledMusicSound();
            SettingManager.setEnabledMusicSound(newState);
            view.updateButton(view.getMusicSoundButton(), newState); // update music sound button

            // play or stop background music
            if (newState) {
                AudioPlayer.playBackgroundMusic(); // call audio player to play music
            } else {
                AudioPlayer.stopBackgroundMusic(); // call audio player stop music
            }

            SettingManager.saveSetting(); // call setting manager to save setting
        };
    }

    // action listener for save setting button
    private ActionListener createSaveSettingButtonListener() {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);

            view.updateButton(view.getSaveSettingButton(), newState); // update save setting button

            new BtnSound("click", SettingController.this).actionPerformed(null); // play click sound

            // save file or delete file
            if (newState) {
                SettingManager.saveSetting(); // call setitng manager to save setting
            } else {
                SettingManager.deleteSettingFile(); // call setting manager to delete setting file
            }
        };
    }

    // add hover sound for button
    private void addHoverSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // when mouse enter
                boolean isOn = button.getText().equals("ON");
                button.setBackground(isOn ? new Color(144, 238, 144).darker() : new Color(240, 128, 128).darker()); // darken
                                                                                                                    // color
                button.setForeground(Color.WHITE);
                new BtnSound("hover", SettingController.this).actionPerformed(null); // call button sound play sound
            }

            @Override
            public void mouseExited(MouseEvent e) { // when mouse exit
                boolean isOn = button.getText().equals("ON");
                button.setBackground(isOn ? new Color(144, 238, 144) : new Color(240, 128, 128)); // change the color
                                                                                                  // back to normal
                button.setForeground(Color.BLACK);
            }
        });
    }

    // add hover sound for color picker
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

    // action listener for color picker
    private ActionListener createColorPickerListener(boolean isPrimary) {
        new BtnSound("click", SettingController.this).actionPerformed(null); // play click sound

        return e -> {
            Color initialColor = isPrimary ? SettingManager.getPrimaryColor() : SettingManager.getSecondaryColor(); // get
                                                                                                                    // initial
                                                                                                                    // color
            Color newColor = JColorChooser.showDialog(view.getPrimaryColorButton(), "Choose a color", initialColor); // open
                                                                                                                     // color
                                                                                                                     // picker

            // if have color, set the color
            if (newColor != null) {
                if (isPrimary) { // set primary color
                    SettingManager.setPrimaryColor(newColor); // call setting manager to set primary color
                    view.getPrimaryColorButton().setBackground(newColor); // set primary color button background
                } else {
                    SettingManager.setSecondaryColor(newColor); // call setting manager to set secondary color
                    view.getSecondaryColorButton().setBackground(newColor); // set secondary color button background
                }
                SettingManager.saveSetting(); // call setting manager to save setting
            }
        };
    }

    // add window listener for setting page
    private void addWindowListener() {
        JFrame settingsFrame = view.getSettingFrame(); // get setting frame
        settingsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // when window is closing
                new BtnSound("click", SettingController.this).actionPerformed(null); // play click sound
                returnToLandingPage(); // return to landing page
                settingsFrame.dispose(); // close setting page
            }
        });
    }

    // check if color is pure black
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