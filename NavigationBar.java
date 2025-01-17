import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NavigationBar extends JMenuBar {

    public NavigationBar() {
        // Create menus
        JMenu fileMenu = new JMenu("File");
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");

        // file menu items
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem loadGameItem = new JMenuItem("Load Game");
        JMenuItem exitItem = new JMenuItem("Exit");

        // game menu items
        JMenuItem undoMoveItem = new JMenuItem("Undo Move");
        JMenuItem redoMoveItem = new JMenuItem("Redo Move");

        // settings menu items
        JCheckBoxMenuItem soundMenuItem = new JCheckBoxMenuItem("Sound", SettingManager.isEnabledSound());

        // help menu items
        JMenuItem rulesItem = new JMenuItem("Rules");

        // add to file menu
        fileMenu.add(newGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(loadGameItem);
        fileMenu.add(exitItem);

        // add to game menu
        gameMenu.add(undoMoveItem);
        gameMenu.add(redoMoveItem);

        // add to settings menu
        settingsMenu.add(soundMenuItem);

        // add to help menu
        helpMenu.add(rulesItem);

        // add to menus bar
        add(fileMenu);
        add(gameMenu);
        add(settingsMenu);
        add(helpMenu);

        // add action listeners for menu items
        newGameItem.addActionListener(e -> {
            boolean response = getRespones("start a new game?", "Start New Game");

            if (response) {
                disposeFrame();

                // start a new game
                new GamePage("new");
            }
        });

        saveGameItem.addActionListener(e -> {
            boolean response = getRespones("save the game ?", "Save Game");

            if (response) {
                // add call save game function here
            }
        });

        loadGameItem.addActionListener(e -> {
            // call road game function here
        });

        exitItem.addActionListener(e -> {
            boolean response = getRespones("save the game before exit ?", "Save Game Before Exit");

            if (response) {
                // add call save game function here
            }

            // close the game
            disposeFrame();

            // return to the landing page
            new SettingController();
            new HomePageController();
        });

        undoMoveItem.addActionListener(e -> {
            // call undo move function here
        });

        redoMoveItem.addActionListener(e -> {
            // call redo move function here
        });

        //soundMenuItem.addActionListener(SettingController.createSoundButtonListener(null));

        rulesItem.addActionListener(e -> showRulesDialog());

        // add hover and click sound effects to all menu items
        addHoverAndClickSound(newGameItem);
        addHoverAndClickSound(saveGameItem);
        addHoverAndClickSound(loadGameItem);
        addHoverAndClickSound(exitItem);
        addHoverAndClickSound(undoMoveItem);
        addHoverAndClickSound(redoMoveItem);
        addHoverAndClickSound(rulesItem);
    }

    // add hover and click sound effects to menu items
    private void addHoverAndClickSound(JMenuItem menuItem) {
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new BtnSound("hover").actionPerformed(null);
            }
        });

        // click sound
        menuItem.addActionListener(e -> {
            new BtnSound("click").actionPerformed(null);
        });
    }

    // show rules dialog
    private void showRulesDialog() {
        JPanel rulesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // padding (top, left, bottom, right)
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // load images
        Image ramImage = loadImage("/Picture/Ram_Blue.png");
        Image bizImage = loadImage("/Picture/Biz_Blue.png");
        Image torImage = loadImage("/Picture/Tor_Blue.png");
        Image xorImage = loadImage("/Picture/Xor_Blue.png");
        Image sauImage = loadImage("/Picture/Sau_Blue.png");

        // custom the image size
        int imgWidth = 50;
        int imgHeight = 50;

        // add rules with images to panel with scaled images
        addRuleWithImage(rulesPanel, gbc, ramImage, imgWidth, imgHeight,
                "The Ram piece can only move forward, 1 step. If it reaches the end of the board, it turns around and starts heading back the other way. It cannot skip over other pieces.");
        addRuleWithImage(rulesPanel, gbc, bizImage, imgWidth, imgHeight,
                "The Biz piece moves in a 3x2 L shape in any orientation (kind of like the knight in standard chess). This is the only piece that can skip over other pieces.");
        addRuleWithImage(rulesPanel, gbc, torImage, imgWidth, imgHeight,
                "The Tor piece can move orthogonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Xor piece.");
        addRuleWithImage(rulesPanel, gbc, xorImage, imgWidth, imgHeight,
                "The Xor piece can move diagonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Tor piece.");
        addRuleWithImage(rulesPanel, gbc, sauImage, imgWidth, imgHeight,
                "The Sau piece can move only one step in any direction. The game ends when the Sau is captured by the other side.");

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        rulesPanel.add(new JLabel(
                "<html><div style='width: 430px;'><b>General Rule:</b> None of the pieces are allowed to skip over other pieces, except for Biz.</div></html>"),
                gbc);

        // create a dialog to show the rules
        JDialog rulesDialog = new JDialog();
        rulesDialog.setTitle("Kwazam Chess Rules");
        rulesDialog.setSize(600, 450);
        rulesDialog.setLocationRelativeTo(null);
        rulesDialog.setModal(true);

        // add rulesPanel to the dialog
        rulesDialog.add(rulesPanel);
        rulesDialog.setVisible(true);
    }

    // fetch image
    private Image loadImage(String path) {
        try {
            Image img = ImageIO.read(getClass().getResource(path));
            return img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading image: " + path);
            return null;
        }
    }

    // add rule with image
    private void addRuleWithImage(JPanel panel, GridBagConstraints gbc, Image image, int width, int height,
            String text) {
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH))), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("<html><div style='text-align: justify;'>" + text + "</div></html>"), gbc);
    }

    private boolean getRespones(String mss, String title) {
        return JOptionPane.showConfirmDialog(
                this, // parent component
                "Are you sure you want to " + mss, // message
                title, // title
                JOptionPane.YES_NO_OPTION, // option type
                JOptionPane.QUESTION_MESSAGE // message type
        ) == JOptionPane.YES_OPTION;
    }

    // dispose the frame
    private void disposeFrame() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.dispose();
    }
}