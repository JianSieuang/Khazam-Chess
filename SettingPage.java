import javax.swing.*;
import java.awt.*;

public class SettingPage {

    // global Variables
    private static final String title = "Settings";

    public static void showSetting(JFrame parentFrame) {
        // load settings when showing settings
        SettingManager.loadSetting();

        // create a new frame for the settings page
        JFrame settingsFrame = new JFrame(title);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(600, 600); // Frame Size
        settingsFrame.setLocationRelativeTo(parentFrame);

        // create the main panel for settings
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 10)); // gridLayout: rows, columns, hgap, vgap
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // add padding

        // add title
        addSettingTitle(panel);

        // add settings options
        addSettingOptions(panel);

        // add panel to the frame
        settingsFrame.add(panel);

        // make the frame visible
        settingsFrame.setVisible(true);
    }

    private static void addSettingTitle(JPanel panel) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // set font
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // align center
        panel.add(titleLabel);
    }

    private static void addSettingOptions(JPanel panel) {
        JLabel soundLabel = new JLabel("Sound:");
        ButtonDesign.RoundedButton soundButton = new ButtonDesign.RoundedButton(
                SettingUtils.getOnOffLabel(SettingManager.isEnabledSound()), 20); // set corner radius
        SettingUtils.setButtonDesign(soundButton, SettingManager.isEnabledSound());

        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        ButtonDesign.RoundedButton saveSettingButton = new ButtonDesign.RoundedButton(
                SettingUtils.getOnOffLabel(SettingManager.isSaveSettingPermanently()), 20); // set corner radius
        SettingUtils.setButtonDesign(saveSettingButton, SettingManager.isSaveSettingPermanently());

        soundButton.addActionListener(SettingController.createSoundButtonListener(soundButton));
        saveSettingButton.addActionListener(SettingController.createSaveSettingButtonListener(saveSettingButton));

        // create a panel for sound option to control spacing
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // align center

        // sound
        soundPanel.add(soundLabel);
        soundPanel.add(soundButton);

        // break line next row
        soundPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // save as default
        soundPanel.add(saveAsDefaultLabel);
        soundPanel.add(saveSettingButton);

        // add the panel to the main settings panel
        panel.add(soundPanel);
    }
}
