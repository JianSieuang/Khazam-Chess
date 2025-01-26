import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Lew Kien Yew
/*
 * control setting view and model
 * implement singleton design pattern
 * manage interaction between setting view (UI) and setting model (SettingManager)
 * handle setting page navigation and setting changes
 * pass setting changes to SettingManager to save setting
 * get data from setting manager to other othe class
 * - btnsound -> manage button sound based on the setting manager (button sound value)
 * - audio player -> manage background music based on the setting manager (music sound value) 
 * - primary & secondary color -> get the value from setting manager and send to setting view
 */
public class SettingController {
    // signleton design pattern, instance of SettingController
    private static SettingController controller;
    
    // UI for setting page
    private SettingView view;

    // constructor, to set up view, load setting, and initialize listeners
    private SettingController() {
        // create new SettingView
        this.view = new SettingView(this);
        
        // set setting controller for audio player
        AudioPlayer.setSettingController(this);
        
        // load setting from setting.txt
        SettingManager.loadSetting();
        
        // initialize listeners for setting page
        initializeListeners();
    }

    // Chong Jian Sieuang
    // get the singleton instance of the controller
    public static SettingController getController() {
        if (controller == null) {
            controller = new SettingController();
        }
        return controller;
    }

    // navigate to setting page
    public void navigateToSettingPage(JFrame currentFrame) {
        // close the current page
        currentFrame.dispose();
        
        // update button sound button
        view.updateButton(view.getButtonSoundButton(), SettingManager.isEnabledButtonSound());
        
        // update music sound button
        view.updateButton(view.getMusicSoundButton(), SettingManager.isEnabledMusicSound());
        
        // display setting page
        view.showSetting(currentFrame);
        
        // handle window close action
        addWindowListener();
    }

    // initialize listeners for setting page
    private void initializeListeners() {
        // button sound
        view.getButtonSoundButton().addActionListener(createButtonSoundListener());
        
        // music sound
        view.getMusicSoundButton().addActionListener(createMusicSoundListener());
        
        // save as default setting
        view.getSaveSettingButton().addActionListener(createSaveSettingButtonListener());
        
        // primary color picker
        view.getPrimaryColorButton().addActionListener(createColorPickerListener(true));
        
        // secondary color picker
        view.getSecondaryColorButton().addActionListener(createColorPickerListener(false));
        
        // back button
        view.getBackButton().addActionListener(e -> {

            // get setting view to close setting page
            view.getSettingFrame().dispose();
            
            // play click sound
            new BtnSound("click", SettingController.this).actionPerformed(null);
            
            // return to landing page
            returnToLandingPage();
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
            
            // update button sound button
            view.updateButton(view.getButtonSoundButton(), newState);
            
            // call setting manager to save setting
            SettingManager.saveSetting();
        };
    }

    // action listener for music sound
    private ActionListener createMusicSoundListener() {
        return e -> {
            boolean newState = !SettingManager.isEnabledMusicSound();
            SettingManager.setEnabledMusicSound(newState);
            
            // update music sound button
            view.updateButton(view.getMusicSoundButton(), newState);

            // play or stop background music
            if (newState) {

                // call audio player to play music
                AudioPlayer.playBackgroundMusic();
            } else {

                // call audio player stop music
                AudioPlayer.stopBackgroundMusic();
            }

            // call setting manager to save setting
            SettingManager.saveSetting();
        };
    }

    // action listener for save setting button
    private ActionListener createSaveSettingButtonListener() {
        return e -> {
            boolean newState = !SettingManager.isSaveSettingPermanently();
            SettingManager.setSaveSettingPermanently(newState);
            
            // update save setting button
            view.updateButton(view.getSaveSettingButton(), newState);

            // play click sound
            new BtnSound("click", SettingController.this).actionPerformed(null);

            // save file or delete file
            if (newState) {
                
                // call setitng manager to save setting
                SettingManager.saveSetting();
            } else {
                
                // call setting manager to delete setting file
                SettingManager.deleteSettingFile();
            }
        };
    }

    // add hover sound for button
    private void addHoverSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            
            /*
             * when mouse entered, darken the color and play hover sound
             * if condition isOn the butotn color be green else red
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                boolean isOn = button.getText().equals("ON");
                
                // darken color
                button.setBackground(isOn ? new Color(144, 238, 144).darker() : new Color(240, 128, 128).darker());
                button.setForeground(Color.WHITE);

                // call button sound play sound
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            /* 
             * when mouse exited, change the color back to normal
             * if condition isOn the butotn color be green else red
             */
            @Override
            public void mouseExited(MouseEvent e) {
                boolean isOn = button.getText().equals("ON");
                
                // change the color back to normal
                button.setBackground(isOn ? new Color(144, 238, 144) : new Color(240, 128, 128));
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
                    
                    // Gray if black
                    button.setBackground(new Color(80, 80, 80));
                } else {
                    
                    // Darken if not black
                    button.setBackground(currentColor.darker());
                }

                button.setForeground(Color.WHITE);
                new BtnSound("hover", SettingController.this).actionPerformed(null);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Color currentColor = isPrimary ? getPrimaryColor() : getSecondaryColor();

                if (isPureBlack(currentColor)) {
                    
                    // Black if black
                    button.setBackground(new Color(0, 0, 0));
                } else {
                    
                    // Original color if not black
                    button.setBackground(currentColor);
                }

                button.setForeground(Color.BLACK);
            }
        });
    }

    // action listener for color picker
    private ActionListener createColorPickerListener(boolean isPrimary) {
        // play click sound
        new BtnSound("click", SettingController.this).actionPerformed(null);

        return e -> {
            // get initial color
            Color initialColor = isPrimary ? SettingManager.getPrimaryColor() : SettingManager.getSecondaryColor();
            
            // open color picker
            Color newColor = JColorChooser.showDialog(view.getPrimaryColorButton(), "Choose a color", initialColor);
            
            // if have color, set the color
            if (newColor != null) {
                
                // set primary color
                if (isPrimary) {
                    
                    // call setting manager to set primary color
                    SettingManager.setPrimaryColor(newColor);
                    
                    // set primary color button background
                    view.getPrimaryColorButton().setBackground(newColor);
                } else {
                    
                    // call setting manager to set secondary color
                    SettingManager.setSecondaryColor(newColor);
                    
                    // set secondary color button background
                    view.getSecondaryColorButton().setBackground(newColor);
                }
                // call setting manager to save setting
                SettingManager.saveSetting();
            }
        };
    }

    // add window listener for setting page
    private void addWindowListener() {
        
        // get setting frame
        JFrame settingsFrame = view.getSettingFrame();
        settingsFrame.addWindowListener(new WindowAdapter() {
            /* 
             * when window closing, return to landing page
             * play click sound
             * return to landing page
             * close setting page
             */
            @Override            
            public void windowClosing(WindowEvent e) {
                
                // play click sound
                new BtnSound("click", SettingController.this).actionPerformed(null);
                
                // return to landing page
                returnToLandingPage();
                
                // close setting page
                settingsFrame.dispose();
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