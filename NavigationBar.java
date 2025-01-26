import javax.swing.*;
import java.awt.*;

/*
 * custom navigation bar for the kwazam chess application
 * responsible for managing the navigation bar in the game
 * include menus for game action, setting, and help with rules dialog
 */
public class NavigationBar extends JMenuBar {
    // new game menu item
    private JMenuItem newGameItem;

    // save game menu item
    private JMenuItem saveGameItem;

    // exit game menu item
    private JMenuItem exitItem;

    // rules menu item
    private JMenuItem rulesItem;

    // sound menu item
    private JCheckBoxMenuItem soundMenuItem;

    // music menu item
    private JCheckBoxMenuItem musicMenuItem;

    // constructor for navigation bar
    // initialSoundState = initial state of button sound setting
    // initialMusicState = initial state of music setting
    public NavigationBar(boolean initialSoundState, boolean initialMusicState) {
        JMenu fileMenu = new JMenu("File");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");
        newGameItem = new JMenuItem("New Game");
        saveGameItem = new JMenuItem("Save Game");
        exitItem = new JMenuItem("Exit");
        soundMenuItem = new JCheckBoxMenuItem("Sound");

        // set initialSoundState for sound
        soundMenuItem.setState(initialSoundState);
        musicMenuItem = new JCheckBoxMenuItem("Music");

        // set initialMusicState for music
        musicMenuItem.setState(initialMusicState);
        rulesItem = new JMenuItem("Rules");

        // add item to menu
        fileMenu.add(newGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(exitItem);
        settingsMenu.add(soundMenuItem);
        settingsMenu.add(musicMenuItem);
        helpMenu.add(rulesItem);

        // add menu to navigation bar
        add(fileMenu);
        add(settingsMenu);
        add(helpMenu);
    }

    // display rules dialog
    public void showRulesDialog() {
        // create dialog for rules
        JDialog rulesDialog = new JDialog();
        rulesDialog.setTitle("Kwazam Chess Rules");

        // set dialog size
        rulesDialog.setSize(400, 550);

        // center the dialog
        rulesDialog.setLocationRelativeTo(null);
        rulesDialog.setModal(true);

        // set application icon
        rulesDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png"));

        // disable resize
        rulesDialog.setResizable(false);

        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new GridLayout(0, 1, 10, 10));

        // add padding to the panel border (top, left, bottom, right)
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // create image and rules for each piece
        JLabel ramImage = new JLabel(resizeImage("Picture/Ram_Blue.png", 50, 50));
        JLabel bizImage = new JLabel(resizeImage("Picture/Biz_Blue.png", 50, 50));
        JLabel torImage = new JLabel(resizeImage("Picture/Tor_Blue.png", 50, 50));
        JLabel xorImage = new JLabel(resizeImage("Picture/Xor_Blue.png", 50, 50));
        JLabel sauImage = new JLabel(resizeImage("Picture/Sau_Blue.png", 50, 50));

        // rules description for each piece
        JTextArea ramRules = new JTextArea(
                "The Ram piece can only move forward, 1 step. If it reaches the end of the board, it turns around and starts heading back the other way. It cannot skip over other pieces.");
        JTextArea bizRules = new JTextArea(
                "The Biz piece moves in a 3x2 L shape in any orientation (kind of like the knight in standard chess). This is the only piece that can skip over other pieces.");
        JTextArea torRules = new JTextArea(
                "The Tor piece can move orthogonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Xor piece.");
        JTextArea xorRules = new JTextArea(
                "The Xor piece can move diagonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Tor piece.");
        JTextArea sauRules = new JTextArea(
                "The Sau piece can move only one step in any direction. The game ends when the Sau is captured by the other side.");
        JTextArea generalRules = new JTextArea(
                "General Rule: None of the pieces are allowed to skip over other pieces, except for Biz.");

        // wrap text
        generalRules.setLineWrap(true);

        // add image and rules to the panel
        rulesPanel.add(imageAndRules(ramImage, ramRules));
        rulesPanel.add(imageAndRules(bizImage, bizRules));
        rulesPanel.add(imageAndRules(torImage, torRules));
        rulesPanel.add(imageAndRules(xorImage, xorRules));
        rulesPanel.add(imageAndRules(sauImage, sauRules));
        rulesPanel.add(generalRules);

        // background color
        rulesPanel.setBackground(Color.WHITE);
        rulesDialog.add(rulesPanel);

        // show dialog
        rulesDialog.setVisible(true);

        // disable resize
        rulesDialog.setResizable(false);
    }

    // show dialog to confirm new game
    public int showConfirmNewGameDialog() {
        return JOptionPane.showConfirmDialog(
                null,
                "Starting a new game will erase the current progress. Do you want to proceed?",
                "Confirm New Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
    }

    // combine image and corresponding rules into a single JPanel
    private JPanel imageAndRules(JLabel image, JTextArea rule) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // wrap text
        rule.setLineWrap(true);
        
        // add image to the left
        panel.add(image, BorderLayout.WEST);
        
        // add rules text to center
        panel.add(rule, BorderLayout.CENTER);
        return panel;
    }

    // resize image to the given width and height
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        // load original image
        ImageIcon originalIcon = new ImageIcon(imagePath);

        // get image
        Image originalImage = originalIcon.getImage();

        // resize image
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resizedImage);
    }

    // All Encapsulation below
    public JMenuItem getNewGameItem() {
        return newGameItem;
    }

    public JMenuItem getSaveGameItem() {
        return saveGameItem;
    }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public JMenuItem getRulesItem() {
        return rulesItem;
    }

    public JCheckBoxMenuItem getButtonSoundMenuItem() {
        return soundMenuItem;
    }

    public JCheckBoxMenuItem getSoundMenuItem() {
        return musicMenuItem;
    }
}
