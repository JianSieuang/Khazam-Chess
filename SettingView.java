import javax.swing.*;
import java.awt.*;

/*
 * view class for the setting page
 */
public class SettingView {

    // UI component
    private JFrame settingsFrame; // main frame
    private JLabel titleLabel; // title label
    private JButton buttonSoundButton; // button for button sound
    private JButton musicSoundButton; // button for music sound
    private JButton saveSettingButton; // button for save setting
    private JButton backButton; // button for back
    private SettingController controller; // setting controller
    private JButton primaryColorButton; // button for primary color
    private JButton secondaryColorButton; // button for secondary color
    private Color txtColor = new Color(34, 34, 34); // text color

    public SettingView(SettingController controller) {
        this.controller = controller; // set the controller
        controller.runLoadSetting(); // load the setting

        buttonSoundButton = new JButton(getOnOffLabel(controller.getIsButtonSoundEnabled())); // set the button text
        setButtonDesign(buttonSoundButton, controller.getIsButtonSoundEnabled());// set the button design

        musicSoundButton = new JButton(getOnOffLabel(controller.getIsMusicSoundEnabled())); // set the button text
        setButtonDesign(musicSoundButton, controller.getIsMusicSoundEnabled()); // set the button design

        saveSettingButton = new JButton(getOnOffLabel(controller.getIsSaveSettingPermanently())); // set the button text
        setButtonDesign(saveSettingButton, controller.getIsSaveSettingPermanently()); // set the button design

        primaryColorButton = new JButton(""); // set the button text
        setButtonDesign(primaryColorButton, false); // set the button design
        primaryColorButton.setPreferredSize(new Dimension(100, 40)); // set custom width and height

        secondaryColorButton = new JButton(""); // set the button text
        setButtonDesign(secondaryColorButton, false); // set the button design
        secondaryColorButton.setPreferredSize(new Dimension(100, 40)); // set custom width and height

        backButton = new JButton("Back"); // set the button text
        setButtonDesign(backButton, false); // set the button design
    }

    // show the setting page
    public void showSetting(JFrame parentFrame) {
        controller.runLoadSetting(); // load the setting

        settingsFrame = new JFrame("Settings"); // new frame
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // set default close operation
        settingsFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png")); // set application icon
        settingsFrame.setSize(600, 600); // set the size
        settingsFrame.setLocationRelativeTo(parentFrame); // set the location

        JPanel innerPanel = new JPanel(new GridLayout(0, 1, 0, 10)); // new panel
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // set the border
        innerPanel.setOpaque(false); // set the panel to opaque

        addSettingTitle(innerPanel); // add the title
        addSettingOptions(innerPanel, settingsFrame); // add the options

        BackgroundPanel panel = new BackgroundPanel("setting_page"); // new panel
        panel.setLayout(new BorderLayout()); // set the layout
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // set the border
        panel.add(innerPanel, BorderLayout.CENTER); // add the inner panel

        settingsFrame.add(panel); // add the panel
        settingsFrame.setVisible(true); // set the frame to visible
    }

    // add setting title
    private void addSettingTitle(JPanel panel) {
        titleLabel = new JLabel("Settings"); // setting label
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // set the font
        titleLabel.setForeground(txtColor); // set the color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // set the alignment
        panel.add(titleLabel); // add the label
    }

    // add setting options
    private void addSettingOptions(JPanel panel, JFrame settingsFrame) {
        JLabel buttonSoundLabel = new JLabel("Button Sound:"); // button sound label
        configureLabel(buttonSoundLabel, new Font("Arial", Font.BOLD, 17), txtColor, false); // configure the label

        JLabel musicSoundLabel = new JLabel("Music Sound:"); // music sound label
        configureLabel(musicSoundLabel, new Font("Arial", Font.BOLD, 17), txtColor, false); // configure the label

        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // sound panel for the buttons
        soundPanel.setOpaque(false); // set the panel background to transparent
        soundPanel.add(buttonSoundLabel); // add the button sound label
        soundPanel.add(buttonSoundButton); // add the button sound button
        soundPanel.add(musicSoundLabel); // add the music sound label
        soundPanel.add(musicSoundButton); // add the music sound button

        panel.add(soundPanel); // add the sound panel

        JLabel saveAsDefaultLabel = new JLabel("Save as default:"); // save as default label
        configureLabel(saveAsDefaultLabel, new Font("Arial", Font.BOLD, 17), txtColor, false); // configure the label

        JPanel saveSettingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // save setting panel
        saveSettingPanel.setOpaque(false); // set the panel background to transparent
        saveSettingPanel.add(saveAsDefaultLabel); // add the save as default label
        saveSettingPanel.add(saveSettingButton); // add the save setting button

        panel.add(saveSettingPanel); // add the save setting panel

        JLabel themeColorLabel = new JLabel("Theme Color"); // theme color label
        configureLabel(themeColorLabel, new Font("Arial", Font.BOLD, 36), txtColor, false); // configure the label
        themeColorLabel.setHorizontalAlignment(SwingConstants.CENTER); // set the alignment

        JPanel themeTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // theme title panel
        themeTitlePanel.setOpaque(false); // set the panel background to transparent
        themeTitlePanel.add(themeColorLabel); // add the theme color label

        JLabel primaryColorLabel = new JLabel("     Primary Color:"); // primary color label
        configureLabel(primaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false); // configure the label

        JLabel secondaryColorLabel = new JLabel("Secondary Color:"); // secondary color label
        configureLabel(secondaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false); // configure the label

        JPanel primaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // primary color panel
        primaryColorPanel.setOpaque(false); // set the panel background to transparent
        primaryColorPanel.add(primaryColorLabel); // add the primary color label
        primaryColorPanel.add(primaryColorButton); // add the primary color button

        JPanel secondaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // secondary color panel
        secondaryColorPanel.setOpaque(false); // set the panel background to transparent
        secondaryColorPanel.add(secondaryColorLabel); // add the secondary color label
        secondaryColorPanel.add(secondaryColorButton); // add the secondary color button

        primaryColorButton.setBackground(controller.getPrimaryColor()); // set the primary color button background
        secondaryColorButton.setBackground(controller.getSecondaryColor()); // set the secondary color button background

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // vertical spacing
        panel.add(themeTitlePanel);
        panel.add(primaryColorPanel);
        panel.add(secondaryColorPanel);

        // back button
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.setOpaque(false); // set the panel background to transparent
        backButtonPanel.add(backButton);

        panel.add(Box.createRigidArea(new Dimension(0, 30))); // vertical spacing
        panel.add(backButtonPanel);
    }

    // reusable Methods for Button Updates
    private String getOnOffLabel(boolean state) {
        return state ? "ON" : "OFF";
    }

    private void setButtonDesign(JButton button, boolean isOn) {
        Color backgroundColor = isOn ? new Color(144, 238, 144) : new Color(240, 128, 128); // set the background color

        button.setUI(new RoundedButtonUI()); // set the button's UI to the custom UI
        button.setFocusPainted(false); // remove focus border
        button.setOpaque(false); // set to true to allow background color to show
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // remove default border

        button.setBackground(backgroundColor); // default background color
        button.setForeground(Color.BLACK); // default text color
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
        button.setText(getOnOffLabel(state)); // update the button text
        setButtonDesign(button, state); // update the button design
    }

    public JFrame getSettingFrame() {
        return settingsFrame;
    }

    private void configureLabel(JLabel label, Font font, Color foregroundColor, boolean isOpaque) {
        label.setFont(font); // set the font
        label.setForeground(foregroundColor); // set the color
        label.setOpaque(isOpaque); // set the label to opaque
    }
}
