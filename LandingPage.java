import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LandingPage {

    // global variable to store the title
    private static final String TITLE = "Kwazam Chess";

    //for background image(but have some problem)
    // private static BufferedImage backgroundImage;
    // static {
    //     try {
    //         // Load the background image
    //         backgroundImage = ImageIO.read(new File("sources/download.jpeg"));
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         System.out.println("Error loading background image. Ensure 'background.jpg' is in the correct directory.");
    //     }
    // }
    
    public static void showMenu() {        
        // create the main frame
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);    // frame Size
        frame.setLocationRelativeTo(null);

        // Create the main panel with custom paintComponent to show background image
        // JPanel backgroundPanel = new JPanel() {
        //     @Override
        //     protected void paintComponent(Graphics g) {
        //         super.paintComponent(g);
        //         if (backgroundImage != null) {
        //             // Draw the background image, scaling it to fit the panel
        //             g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        //         }
        //     }
        // };
        // backgroundPanel.setLayout(new BorderLayout()); // Set layout for the background panel
        // frame.add(backgroundPanel); // Add the background panel to the frame

        // create the main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));   // add padding

        // inner panel to align content vertically
        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // add padding
        innerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);//let component align center x
        innerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);//let component align center y

        innerPanel.add(Box.createVerticalGlue()); // add space at the top
        // innerPanel.setOpaque(false); // Set transparent to match parent background

        // add title
        addTitle(innerPanel);

        // add menu buttons use utility class
        MenuButton.addMenuButtons(innerPanel, frame);

        innerPanel.add(Box.createVerticalGlue());// add space on bottom

        // add inner panel to the main panel
        panel.add(innerPanel,BorderLayout.CENTER);

        // add panel to the frame
        frame.add(panel);

        // make the frame visible
        frame.setVisible(true);
    }

    // title function
    private static void addTitle(JPanel panel) {
        JLabel titleLabel = new JLabel(TITLE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));   // set font
        titleLabel.setForeground(new Color(0, 0, 128)); // add color to match theme
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);   // align center
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // spacer
    }
    
    
    public static void main(String[] args) {
        SettingManager.loadSetting();
        showMenu();
    }
}