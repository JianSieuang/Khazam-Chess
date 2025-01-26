import javax.swing.*;
import java.awt.*;

// Sim Boon Xun
/* 
 * View, reperesent the home page view
 * this class extend JFrame and include UI component (background panel, content panel, and dialogs)
 */
public class HomePageView extends JFrame {
    private ContentPanel contentPanel;
    private BackgroundPanel backgroundPanel;
    private int height = 600;
    private int width = 600;

    // constructor for home page view
    public HomePageView() {
        // set title
        super("Kwazam Chess");

        // set background image
        backgroundPanel = new BackgroundPanel("home_page");
        
        // set content panel size
        contentPanel = new ContentPanel(height, width);
        
        // add content panel to background panel center
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);

        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // center the frame
        setLocationRelativeTo(null);
        
        // show the frame
        setVisible(true);
        
        // set application icon
        setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png"));
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
