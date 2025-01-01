import javax.swing.*;
import java.awt.*;

public class SettingPage {

    // global Variables
    private static final String title = "Settings";
    private static JLabel titleLabel;

    public static void showSetting(JFrame parentFrame) {
        // load settings when showing settings
        SettingManager.loadSetting();

        // create a new frame for the settings page
        JFrame settingsFrame = new JFrame(title);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(600, 600); // Frame Size
        settingsFrame.setLocationRelativeTo(parentFrame);
        
        // inner panel to align content vertically
        JPanel innerpanel = new JPanel(new GridLayout(0, 1, 0, 10)); // gridLayout: rows, columns, hgap, vgap
        innerpanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // add padding
        innerpanel.setOpaque(false);

        // add title
        addSettingTitle(innerpanel);

        // add settings options
        addSettingOptions(innerpanel);

        // use BackgroundPanel instead of a regular JPanel
        BackgroundPanel panel = new BackgroundPanel("setting_page");
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(innerpanel, BorderLayout.CENTER);

        // add panel to the frame
        settingsFrame.add(panel);

        // make the frame visible
        settingsFrame.setVisible(true);
    }

    private static void addSettingTitle(JPanel panel) {
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // set font
        titleLabel.setForeground(new Color(255, 255, 255)); // add color to match theme
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // align center
        panel.add(titleLabel);
    }

    private static void addSettingOptions(JPanel panel) {
        JLabel soundLabel = new JLabel("Sound:");
        soundLabel.setFont(new Font("Arial", Font.BOLD, 17)); // set font
        soundLabel.setForeground(new Color(255, 255, 255)); // add color to match theme

        ButtonDesign.RoundedButton soundButton = new ButtonDesign.RoundedButton(SettingUtils.getOnOffLabel(SettingManager.isEnabledSound()), 20); // set corner radius
        soundButton.setFont(new Font("Arial", Font.BOLD, 17)); // set font
        SettingUtils.setButtonDesign(soundButton, SettingManager.isEnabledSound());

        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        saveAsDefaultLabel.setFont(new Font("Arial", Font.BOLD, 17)); // set font
        saveAsDefaultLabel.setForeground(new Color(255, 255, 255)); // add color to match theme

        ButtonDesign.RoundedButton saveSettingButton = new ButtonDesign.RoundedButton(SettingUtils.getOnOffLabel(SettingManager.isSaveSettingPermanently()), 20); // set corner radius
        saveSettingButton.setFont(new Font("Arial", Font.BOLD, 17)); // set font
        SettingUtils.setButtonDesign(saveSettingButton, SettingManager.isSaveSettingPermanently());

        soundButton.addActionListener(SettingController.createSoundButtonListener(soundButton));
        saveSettingButton.addActionListener(SettingController.createSaveSettingButtonListener(saveSettingButton));

        // create a panel for sound option to control spacing
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // align center
        soundPanel.setOpaque(false); // set transparent

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