import javax.swing.*;
import java.awt.*;

public class SettingView {

    private JFrame settingsFrame;
    private JLabel titleLabel;
    private JButton soundButton;
    private JButton saveSettingButton;
    private JButton backButton;
    private SettingController controller;

    public SettingView(SettingController controller) {
        this.controller = controller;
        controller.runLoadSetting();
        soundButton = new JButton(getOnOffLabel(controller.getIsSoundEnabled()));
        setButtonDesign(soundButton, controller.getIsSoundEnabled());

        saveSettingButton = new JButton(getOnOffLabel(controller.getIsSaveSettingPermanently()));
        setButtonDesign(saveSettingButton, controller.getIsSaveSettingPermanently());

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
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
    }

    private void addSettingOptions(JPanel panel, JFrame settingsFrame) {
        JLabel soundLabel = new JLabel("Sound:");
        soundLabel.setFont(new Font("Arial", Font.BOLD, 17));
        soundLabel.setForeground(new Color(255, 255, 255));

        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        saveAsDefaultLabel.setFont(new Font("Arial", Font.BOLD, 17));
        saveAsDefaultLabel.setForeground(new Color(255, 255, 255));

        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        soundPanel.setOpaque(false);

        soundPanel.add(soundLabel);
        soundPanel.add(soundButton);
        soundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        soundPanel.add(saveAsDefaultLabel);
        soundPanel.add(saveSettingButton);

        panel.add(soundPanel);

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
}
