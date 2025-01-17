import javax.swing.*;
import java.awt.*;

public class SettingView {

    private static final String title = "Settings";
    private static JLabel titleLabel;

    public static void showSetting(JFrame parentFrame, SettingController controller) {
        SettingManager.loadSetting();

        JFrame settingsFrame = new JFrame(title);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setSize(600, 600);
        settingsFrame.setLocationRelativeTo(parentFrame);

        JPanel innerPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        innerPanel.setOpaque(false);

        addSettingTitle(innerPanel);
        addSettingOptions(innerPanel, settingsFrame, controller);

        BackgroundPanel panel = new BackgroundPanel("setting_page");
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(innerPanel, BorderLayout.CENTER);

        settingsFrame.add(panel);
        settingsFrame.setVisible(true);
    }

    private static void addSettingTitle(JPanel panel) {
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
    }

    private static void addSettingOptions(JPanel panel, JFrame settingsFrame, SettingController controller) {
        JLabel soundLabel = new JLabel("Sound:");
        soundLabel.setFont(new Font("Arial", Font.BOLD, 17));
        soundLabel.setForeground(new Color(255, 255, 255));

        JButton soundButton = new JButton(SettingViewUtils.getOnOffLabel(SettingManager.isEnabledSound()));
        SettingViewUtils.setButtonDesign(soundButton, SettingManager.isEnabledSound());
        soundButton.addActionListener(controller.createSoundButtonListener(soundButton));

        JLabel saveAsDefaultLabel = new JLabel("Save as default:");
        saveAsDefaultLabel.setFont(new Font("Arial", Font.BOLD, 17));
        saveAsDefaultLabel.setForeground(new Color(255, 255, 255));

        JButton saveSettingButton = new JButton(SettingViewUtils.getOnOffLabel(SettingManager.isSaveSettingPermanently()));
        SettingViewUtils.setButtonDesign(saveSettingButton, SettingManager.isSaveSettingPermanently());
        saveSettingButton.addActionListener(controller.createSaveSettingButtonListener(saveSettingButton));

        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        soundPanel.setOpaque(false);

        soundPanel.add(soundLabel);
        soundPanel.add(soundButton);
        soundPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        soundPanel.add(saveAsDefaultLabel);
        soundPanel.add(saveSettingButton);

        panel.add(soundPanel);

        JButton backButton = new JButton("Back");
        SettingViewUtils.setButtonDesign(backButton, false);
        backButton.addActionListener(e -> {
            settingsFrame.dispose();
            controller.returnToLandingPage();
        });

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(backButtonPanel);
    }

    public static void updateSoundButton(JButton soundButton, boolean isEnabled) {
        soundButton.setText(SettingViewUtils.getOnOffLabel(isEnabled));
        SettingViewUtils.setButtonDesign(soundButton, isEnabled);
    }

    public static void updateSaveSettingButton(JButton saveSettingButton, boolean isEnabled) {
        saveSettingButton.setText(SettingViewUtils.getOnOffLabel(isEnabled));
        SettingViewUtils.setButtonDesign(saveSettingButton, isEnabled);
    }
}
