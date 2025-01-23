import javax.swing.*;
import java.awt.*;

public class HomePageView extends JFrame {
    private ContentPanel contentPanel;
    private BackgroundPanel backgroundPanel;
    private int height = 600;
    private int width = 600;

    public HomePageView() {
        super("Kwazam Chess");

        backgroundPanel = new BackgroundPanel("home_page");
        contentPanel = new ContentPanel(height, width);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        add(backgroundPanel);

        setSize(height, width);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ContentPanel getHomePanel() {
        return contentPanel;
    }

    public void showLoadGameErrorDialog() {
        JOptionPane.showMessageDialog(this, "No saved game found!", "Attention", JOptionPane.ERROR_MESSAGE);
    }
}
