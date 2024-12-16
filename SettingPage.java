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
        settingsFrame.setSize(400, 400); // Frame Size
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
        // example settings options
        JLabel soundLabel = new JLabel("Sound:");
        JCheckBox soundCheckBox = new JCheckBox("Enable Sound", SettingManager.isEnabledSound()); // use the saved state
        JCheckBox saveSettingCheckBox = new JCheckBox("Save as default", SettingManager.isSaveSettingPermanently()); // default false

        // Add listeners to save state when checkboxes change
        soundCheckBox.addActionListener(e -> {
            SettingManager.setEnabledSound(soundCheckBox.isSelected());
            
            new BtnSound("click").actionPerformed(null); // play click sound
            
            //  if setting save as default "On"
            if (SettingManager.isSaveSettingPermanently()) {
                SettingManager.saveSetting();
            }
        });

        saveSettingCheckBox.addActionListener(e -> {
            SettingManager.setSaveSettingPermanently(saveSettingCheckBox.isSelected());
            
            //  if save as default checked
            if (saveSettingCheckBox.isSelected()) {
                new BtnSound("click").actionPerformed(null); // play click sound
                SettingManager.saveSetting();
            } else {
                SettingManager.deleteSettingFile();
            }
        });

        // create a panel for sound option to control spacing
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // align center
        soundPanel.add(soundLabel);
        soundPanel.add(soundCheckBox);
        soundPanel.add(saveSettingCheckBox); // add the new checkbox

        // add the panel to the main settings panel
        panel.add(soundPanel);
    }
}
