import javax.swing.*;
import java.awt.*;
import java.awt.Component;

public class LandingPage {

    // global variable to store the title
    private static final String TITLE = "Kwazam Chess";
    private static JLabel titleLabel;

    protected static void showMenu() {
        // create the main frame
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // frame Size
        frame.setLocationRelativeTo(null);
        
        // inner panel to align content vertically
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // add padding
        innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // let component align center x
        innerPanel.setAlignmentY(Component.CENTER_ALIGNMENT); // let component align center y
        innerPanel.setOpaque(false); // set transparent

        innerPanel.add(Box.createVerticalGlue()); // add space at the top

        // add title
        addTitle(innerPanel);

        // add menu buttons use utility class
        MenuButton.addMenuButtons(innerPanel, frame);

        // add space on bottom
        innerPanel.add(Box.createVerticalGlue());

        // set BackgroundPanel instead of a regular JPanel
        BackgroundPanel panel = new BackgroundPanel("landing_page");
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // add inner panel to the main panel
        panel.add(innerPanel, BorderLayout.CENTER);

        // add panel to the frame
        frame.add(panel);

        // make the frame visible
        frame.setVisible(true);

        // play background music
        AudioPlayer.playBackgroundMusic();
    }

    // title function
    private static void addTitle(JPanel panel) {
        titleLabel = new JLabel(TITLE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36)); // set font
        titleLabel.setForeground(new Color(255, 255, 255)); // add color to match theme
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // align center
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // spacer
    }

    public static void main(String[] args) {
        SettingManager.loadSetting();
        showMenu();
    }
}