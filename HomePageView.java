import javax.swing.*;
import java.awt.*;

public class HomePageView extends JFrame {
    private ContentPanel contentPanel;
    private BackgroundPanel backgroundPanel;
    private int height = 600;
    private int width = 600;

    public HomePageView() {
        super("Kwazam Chess"); // set title

        backgroundPanel = new BackgroundPanel("home_page"); // set background image
        contentPanel = new ContentPanel(height, width); // set content panel size

        backgroundPanel.add(contentPanel, BorderLayout.CENTER); // add content panel to background panel center
        add(backgroundPanel);

        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center the frame
        setVisible(true); // show the frame
        setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png")); // set application icon
    }

    public ContentPanel getHomePanel() {
        return contentPanel;
    }

    // show dialog when saved game not found
    public void showLoadGameErrorDialog() {
        JOptionPane.showMessageDialog(this, "Saved Game Not Found!!", "Attention", JOptionPane.ERROR_MESSAGE);
    }

    // show dialog to confirm new game
    public int showAttentionDialog() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Do you want to start a new game? Your current game may lost.",
                "Attention",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        return result;
    }
}
