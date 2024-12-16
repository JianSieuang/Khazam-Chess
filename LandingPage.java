import javax.swing.*;
import java.awt.*;

public class LandingPage {

    // global variable to store the title
    private static final String TITLE = "Kwazam Chess";
    
    public static void showMenu() {        
        // create the main frame
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);    // frame Size
        frame.setLocationRelativeTo(null);

        // create the main panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));   // add padding

        // inner panel to align content vertically
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // add padding

        // add title
        addTitle(innerPanel);

        // add menu buttons use utility class
        MenuButton.addMenuButtons(innerPanel, frame);

        // add inner panel to the main panel
        panel.add(innerPanel);

        // add panel to the frame
        frame.add(panel);

        // make the frame visible
        frame.setVisible(true);
    }

    // title function
    private static void addTitle(JPanel panel) {
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));   // set font
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   // align center
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // spacer
    }
    
    public static void main(String[] args) {
        SettingManager.loadSetting();
        showMenu();
    }
}