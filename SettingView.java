import javax.swing.*;
import java.awt.*;

public class SettingView {

    private JFrame settingsFrame;
    private JLabel titleLabel;
    private JButton soundButton;
    private JButton saveSettingButton;
    private JButton backButton;
    private SettingController controller;
    private JButton primaryColorButton;
    private JButton secondaryColorButton;
    private Color txtColor = new Color(34, 34, 34);

    public SettingView(SettingController controller) {
        this.controller = controller;
        controller.runLoadSetting();
        soundButton = new JButton(getOnOffLabel(controller.getIsSoundEnabled()));
        setButtonDesign(soundButton, controller.getIsSoundEnabled());

        saveSettingButton = new JButton(getOnOffLabel(controller.getIsSaveSettingPermanently()));
        setButtonDesign(saveSettingButton, controller.getIsSaveSettingPermanently());

        primaryColorButton = new JButton("Color Picker");
        setButtonDesign(primaryColorButton, false);
        secondaryColorButton = new JButton("Color Picker");
        setButtonDesign(secondaryColorButton, false);

        backButton = new JButton("Back");
        setButtonDesign(backButton, false);
    }

    public void showSetting(JFrame parentFrame) {
        controller.runLoadSetting();

        settingsFrame = new JFrame("Settings");
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(600, 600);
        settingsFrame.setLocationRelativeTo(parentFrame);

        JPanel innerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerPanel.setOpaque(false);

        addSettingTitle(innerPanel);
        addSettingOptions(innerPanel, settingsFrame);

        BackgroundPanel panel = new BackgroundPanel("setting_page");
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(innerPanel, BorderLayout.CENTER);

        settingsFrame.add(panel);
        settingsFrame.setVisible(true);
    }

    private void addSettingTitle(JPanel panel) {
        titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(txtColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
    }

    private void addSettingOptions(JPanel panel, JFrame settingsFrame) {
        JLabel soundLabel = new JLabel("Sound:");
        configureLabel(soundLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);

        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        configureLabel(saveAsDefaultLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);

        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        soundPanel.setOpaque(false);

        soundPanel.add(soundLabel);
        soundPanel.add(soundButton);
        soundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        soundPanel.add(saveAsDefaultLabel);
        soundPanel.add(saveSettingButton);

        JLabel themeColorLabel = new JLabel("Theme Color");
        configureLabel(themeColorLabel, new Font("Arial", Font.BOLD, 26), txtColor, false);
        themeColorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel themeTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        themeTitlePanel.setOpaque(false);
        themeTitlePanel.add(themeColorLabel);

        JLabel primaryColorLabel = new JLabel("     Primary Color:");
        configureLabel(primaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);

        JLabel secondaryColorLabel = new JLabel("Secondary Color:");
        configureLabel(secondaryColorLabel, new Font("Arial", Font.BOLD, 17), txtColor, false);

        JPanel primaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        primaryColorPanel.setOpaque(false);
        primaryColorPanel.add(primaryColorLabel);
        primaryColorPanel.add(primaryColorButton);

        JPanel secondaryColorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondaryColorPanel.setOpaque(false);
        secondaryColorPanel.add(secondaryColorLabel);
        secondaryColorPanel.add(secondaryColorButton);

        primaryColorButton.setBackground(controller.getPrimaryColor());
        secondaryColorButton.setBackground(controller.getSecondaryColor());

        panel.add(soundPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(themeTitlePanel);
        panel.add(primaryColorPanel);
        panel.add(secondaryColorPanel);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backButtonPanel);
    }

    // reusable Methods for Button Updates
    private String getOnOffLabel(boolean state) {
        return state ? "ON" : "OFF";
    }

    private void setButtonDesign(JButton button, boolean isOn) {
        Color backgroundColor = isOn ? new Color(144, 238, 144) : new Color(240, 128, 128);

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

    public JButton getSoundButton() {
        return soundButton;
    }

    public JButton getSaveSettingButton() {
        return saveSettingButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void updateButton(JButton button, boolean state) {
        button.setText(getOnOffLabel(state));
        setButtonDesign(button, state);
    }

    public JFrame getSettingFrame() {
        return settingsFrame;
    }

    private void configureLabel(JLabel label, Font font, Color foregroundColor, boolean isOpaque) {
        label.setFont(font);
        label.setForeground(foregroundColor);
        label.setOpaque(isOpaque);
    }
}
