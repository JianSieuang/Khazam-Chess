import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {
    private JLabel titleLabel;
    private JButton newGameButton;
    private JButton loadGameButton;
    private JButton settingsButton;
    private JButton quitGameButton;

    public ContentPanel(int h, int w) {
        super();
        setBorder(BorderFactory.createEmptyBorder(h / 6, w / 6, h / 6, w / 6));
        setLayout(new GridLayout(0, 1, 10, 10));
        setOpaque(false);

        titleLabel = new JLabel("Kwazam Chess", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(new Color(255, 255, 255));

        newGameButton = new JButton("New Game");
        newGameButton.setBackground(new Color(144, 238, 144));

        loadGameButton = new JButton("Load Game");
        loadGameButton.setBackground(new Color(173, 216, 230));

        settingsButton = new JButton("Settings");
        settingsButton.setBackground(new Color(211, 211, 211));

        quitGameButton = new JButton("Quit Game");
        quitGameButton.setBackground(new Color(240, 128, 128));

        buttonDesign(newGameButton);
        buttonDesign(loadGameButton);
        buttonDesign(settingsButton);
        buttonDesign(quitGameButton);

        add(titleLabel);
        add(newGameButton);
        add(loadGameButton);

        JPanel tempPanel = new JPanel();
        tempPanel.setOpaque(false);
        tempPanel.setLayout(new GridLayout(0, 2, 10, 10));
        tempPanel.add(settingsButton);
        tempPanel.add(quitGameButton);

        add(tempPanel);
    }

    private void buttonDesign(JButton button) {
        button.setUI(new RoundedButtonUI()); // set the button's UI to the custom UI
        button.setFocusPainted(false); // not show focus
        button.setOpaque(false); // not show background color
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // border
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getLoadGameButton() {
        return loadGameButton;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public JButton getQuitGameButton() {
        return quitGameButton;
    }
}
