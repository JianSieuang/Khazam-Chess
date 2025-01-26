import javax.swing.*;
import java.awt.*;

/*
 * custom navigation bar for the kwazam chess application
 * responsible for managing the navigation bar in the game
 * include menus for game action, setting, and help with rules dialog
 */
public class NavigationBar extends JMenuBar {
    private JMenuItem newGameItem; // new game menu item
    private JMenuItem saveGameItem; // save game menu item
    private JMenuItem exitItem; // exit game menu item
    private JMenuItem rulesItem; // rules menu item
    private JCheckBoxMenuItem soundMenuItem; // sound menu item
    private JCheckBoxMenuItem musicMenuItem; // music menu item

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
        soundMenuItem.setState(initialSoundState); // set initialSoundState for sound
        musicMenuItem = new JCheckBoxMenuItem("Music");
        musicMenuItem.setState(initialMusicState); // set initialMusicState for music
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
        JDialog rulesDialog = new JDialog(); // create dialog for rules
        rulesDialog.setTitle("Kwazam Chess Rules");
        rulesDialog.setSize(400, 550); // set dialog size
        rulesDialog.setLocationRelativeTo(null); // center the dialog
        rulesDialog.setModal(true);
        rulesDialog.setIconImage(Toolkit.getDefaultToolkit().getImage("Picture/application_icon.png")); // set
                                                                                                        // application
                                                                                                        // icon
        rulesDialog.setResizable(false); // disable resize

        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new GridLayout(0, 1, 10, 10));
        rulesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); // add padding to the panel

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
        generalRules.setLineWrap(true); // wrap text

        // add image and rules to the panel
        rulesPanel.add(imageAndRules(ramImage, ramRules));
        rulesPanel.add(imageAndRules(bizImage, bizRules));
        rulesPanel.add(imageAndRules(torImage, torRules));
        rulesPanel.add(imageAndRules(xorImage, xorRules));
        rulesPanel.add(imageAndRules(sauImage, sauRules));
        rulesPanel.add(generalRules);

        rulesPanel.setBackground(Color.WHITE); // background color
        rulesDialog.add(rulesPanel);
        rulesDialog.setVisible(true); // show dialog
        rulesDialog.setResizable(false); // disable resize
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
        rule.setLineWrap(true); // wrap text
        panel.add(image, BorderLayout.WEST); // add image to the left
        panel.add(rule, BorderLayout.CENTER); // add rules text to center
        return panel;
    }

    // resize image to the given width and height
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath); // load original image
        Image originalImage = originalIcon.getImage(); // get image
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // resize image
        return new ImageIcon(resizedImage);
    }

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
        return soundMenuItem; // Ensure this returns the correct button sound menu item
    }

    public JCheckBoxMenuItem getSoundMenuItem() {
        return musicMenuItem;
    }
}
