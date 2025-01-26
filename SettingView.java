import javax.swing.*;
import java.awt.*;

// Lew Kien Yew
/*
 * view class for the setting page
 * handle UI and layout for the setting page, include buttons, labels, and color picker
 */
public class SettingView {
    // main frame
    private JFrame settingsFrame;
    
    // title label
    private JLabel titleLabel;
    
    // button for button sound
    private JButton buttonSoundButton;
    
    // button for music sound
    private JButton musicSoundButton;
    
    // button for save setting
    private JButton saveSettingButton;
    
    // button for back
    private JButton backButton;
    
    // setting controller
    private SettingController controller;
    
    // button for primary color
    private JButton primaryColorButton;
    
    // button for secondary color
    private JButton secondaryColorButton;
    
    // text color
    private Color txtColor = new Color(34, 34, 34);

    public SettingView(SettingController controller) {
        // set the controller
        this.controller = controller;
        
        // load the setting
        controller.runLoadSetting();
        
        // set the button text
        buttonSoundButton = new JButton(getOnOffLabel(controller.getIsButtonSoundEnabled()));
        
        // set the button design
        setButtonDesign(buttonSoundButton, controller.getIsButtonSoundEnabled());

        // set the button text
        musicSoundButton = new JButton(getOnOffLabel(controller.getIsMusicSoundEnabled()));
        
        // set the button design
        setButtonDesign(musicSoundButton, controller.getIsMusicSoundEnabled());

        // set the button text
        saveSettingButton = new JButton(getOnOffLabel(controller.getIsSaveSettingPermanently()));
        
        // set the button design
        setButtonDesign(saveSettingButton, controller.getIsSaveSettingPermanently());
        
        // set the button text
        primaryColorButton = new JButton("");
        
        // set the button design
        setButtonDesign(primaryColorButton, false);
        
        // set custom width and height
        primaryColorButton.setPreferredSize(new Dimension(100, 40));
        
        // set the button text
        secondaryColorButton = new JButton("");
        
        // set the button design
        setButtonDesign(secondaryColorButton, false);
        
        // set custom width and height
        secondaryColorButton.setPreferredSize(new Dimension(100, 40));

        // set the button text
        backButton = new JButton("Back");
        
        // set the button design
        setButtonDesign(backButton, false);
    }

    // show the setting page
    public void showSetting(JFrame parentFrame) {
        // load the setting
        controller.runLoadSetting();
        
        // new frame
        settingsFrame = new JFrame("Settings");
        
        // set default close operation
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // set application icon
        settingsFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png"));
        
        // set the size
        settingsFrame.setSize(600, 600);
        
        // set the location
        settingsFrame.setLocationRelativeTo(parentFrame);
        
        // new panel
        JPanel innerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        
        // set the border
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // set the panel to opaque
        innerPanel.setOpaque(false);
        
        // add the title
        addSettingTitle(innerPanel);
        
        // add the options
        addSettingOptions(innerPanel, settingsFrame);
        
        // new panel
        BackgroundPanel panel = new BackgroundPanel("setting_page");
        
        // set the layout
        panel.setLayout(new BorderLayout());
        
        // set the border
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // add the inner panel
        panel.add(innerPanel, BorderLayout.CENTER);
        
        // add the panel
        settingsFrame.add(panel);
        
        // set the frame to visible
        settingsFrame.setVisible(true);
    }

    // add setting title
    private void addSettingTitle(JPanel panel) {
        // setting label
        titleLabel = new JLabel("Settings");
         
        // set the font
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
         
        // set the color
        titleLabel.setForeground(txtColor);
         
        // set the alignment
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
         
        // add the label
        panel.add(titleLabel);
    }

    // add setting options
    private void addSettingOptions(JPanel panel, JFrame settingsFrame) {
        // button sound label
        JLabel buttonSoundLabel = new JLabel("Button Sound:");
        
        // configure the label
        configureLabel(buttonSoundLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);

        // music sound label
        JLabel musicSoundLabel = new JLabel("Music Sound:");
        
        // configure the label
        configureLabel(musicSoundLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);
        
        // sound panel for the buttons
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        soundPanel.setOpaque(false);
        
        // add the button sound label
        soundPanel.add(buttonSoundLabel);
        
        // add the button sound button
        soundPanel.add(buttonSoundButton);
        
        // add the music sound label
        soundPanel.add(musicSoundLabel);
        
        // add the music sound button
        soundPanel.add(musicSoundButton);
        
        // add the sound panel
        panel.add(soundPanel);

        // save as default label
        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        
        // configure the label
        configureLabel(saveAsDefaultLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);
        
        // save setting panel
        JPanel saveSettingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        saveSettingPanel.setOpaque(false);
        
        // add the save as default label
        saveSettingPanel.add(saveAsDefaultLabel);
        
        // add the save setting button
        saveSettingPanel.add(saveSettingButton);
        
        // add the save setting panel
        panel.add(saveSettingPanel);

        // theme color label
        JLabel themeColorLabel = new JLabel("Theme Color");
        
        // configure the label
        configureLabel(themeColorLabel, new Font("Arial", Font.BOLD, 36), txtColor, false);
        
        // set the alignment
        themeColorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // theme title panel
        JPanel themeTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        themeTitlePanel.setOpaque(false);
        
        // add the theme color label
        themeTitlePanel.add(themeColorLabel);
        
        // primary color label
        JLabel primaryColorLabel = new JLabel("     Primary Color:");
        
        // configure the label
        configureLabel(primaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);
        
        // secondary color label
        JLabel secondaryColorLabel = new JLabel("Secondary Color:");
        
        // configure the label
        configureLabel(secondaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);
        
        // primary color panel
        JPanel primaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        primaryColorPanel.setOpaque(false);
        
        // add the primary color label
        primaryColorPanel.add(primaryColorLabel);
        
        // add the primary color button
        primaryColorPanel.add(primaryColorButton);
        
        // secondary color panel
        JPanel secondaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        secondaryColorPanel.setOpaque(false);
        
        // add the secondary color label
        secondaryColorPanel.add(secondaryColorLabel);
        
        // add the secondary color button
        secondaryColorPanel.add(secondaryColorButton);
        
        // set the primary color button background
        primaryColorButton.setBackground(controller.getPrimaryColor());
        
        // set the secondary color button background
        secondaryColorButton.setBackground(controller.getSecondaryColor());

        // vertical spacing
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(themeTitlePanel);
        panel.add(primaryColorPanel);
        panel.add(secondaryColorPanel);

        // back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // set the panel background to transparent
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        
        // vertical spacing
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backButtonPanel);
    }

    // reusable Methods for Button Updates
    private String getOnOffLabel(boolean state) {
        return state ? "ON" : "OFF";
    }

    private void setButtonDesign(JButton button, boolean isOn) {
        // set the background color
        Color backgroundColor = isOn ? new Color(144, 238, 144) : new Color(240, 128, 128);

        // set the button's UI to the custom UI
        button.setUI(new RoundedButtonUI());
        
        // remove focus border
        button.setFocusPainted(false);
        
        // set to true to allow background color to show
        button.setOpaque(false);
        
        // remove default border
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // default background color
        button.setBackground(backgroundColor);
        
        // default text color
        button.setForeground(Color.BLACK);
    }

    public JButton getPrimaryColorButton() {
        return primaryColorButton;
    }

    public JButton getSecondaryColorButton() {
        return secondaryColorButton;
    }

    public JButton getButtonSoundButton() {
        return buttonSoundButton;
    }

    public JButton getMusicSoundButton() {
        return musicSoundButton;
    }

    public JButton getSaveSettingButton() {
        return saveSettingButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void updateButton(JButton button, boolean state) {
        // update the button text
        button.setText(getOnOffLabel(state));
        
        // update the button design
        setButtonDesign(button, state);
    }

    public JFrame getSettingFrame() {
        return settingsFrame;
    }

    private void configureLabel(JLabel label, Font font, Color foregroundColor, boolean isOpaque) {
        // set the font
        label.setFont(font);
        
        // set the color
        label.setForeground(foregroundColor);
        
        // set the label to opaque
        label.setOpaque(isOpaque);
    }
}
