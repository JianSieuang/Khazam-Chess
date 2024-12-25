import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class LandingPage {

    // global variable to store the title
    private static final String TITLE = "Kwazam Chess";
    private static JLabel titleLabel;
    private static BufferedImage backgroundImage;
    private static int currentImageIndex = 0;
    private static final String[] imagePaths = {
        "sources/bg_image_1.jpeg", "sources/bg_image_2.png"
    };

    static {
        try {
            backgroundImage = ImageIO.read(new File(imagePaths[currentImageIndex]));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading background image. Ensure 'sources/download.jpeg' is in the correct directory.");
        }
    }

    static class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private static void showMenu() {
        // create the main frame
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // frame Size
        frame.setLocationRelativeTo(null);

        // sse BackgroundPanel instead of a regular JPanel
        BackgroundPanel panel = new BackgroundPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

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

        // add inner panel to the main panel
        panel.add(innerPanel, BorderLayout.CENTER);

        // add panel to the frame
        frame.add(panel);

        // make the frame visible
        frame.setVisible(true);

        // play background music
        AudioPlayer.playBackgroundMusic();

        // start background image timer
        startBackgroundImageTimer(frame);
    }

    // title function
    private static void addTitle(JPanel panel) {
        titleLabel = new JLabel(TITLE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36)); // set font
        titleLabel.setForeground(new Color(0, 0, 128)); // add color to match theme
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // align center
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30))); // spacer
    }

    // change background image
    private static void startBackgroundImageTimer(JFrame frame) {
        // timer to switch background image
        Timer timer = new Timer(5000, e -> {
            // toggle between images
            currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
            try {
                backgroundImage = ImageIO.read(new File(imagePaths[currentImageIndex]));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // update the frame
            frame.repaint();

             // change title color based on current image
            if (currentImageIndex == 0) {
                titleLabel.setForeground(new Color(0, 0, 128)); // blue for first image
            } else {
                titleLabel.setForeground(new Color(255, 215, 0)); // gold for second image
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SettingManager.loadSetting();
        showMenu();
    }
}