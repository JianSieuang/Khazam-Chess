import javax.swing.*;
import java.awt.*;


public class NavigationBar extends JMenuBar 
{
    private JMenuItem newGameItem;
    private JMenuItem saveGameItem;
    private JMenuItem exitItem;
    private JMenuItem rulesItem;
    private JCheckBoxMenuItem soundMenuItem;
    
    public NavigationBar() 
    {
        JMenu fileMenu = new JMenu("File");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");
        newGameItem = new JMenuItem("New Game");
        saveGameItem = new JMenuItem("Save Game");
        exitItem = new JMenuItem("Exit");
        soundMenuItem = new JCheckBoxMenuItem("Sound");
        rulesItem = new JMenuItem("Rules");

        fileMenu.add(newGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(exitItem);
        settingsMenu.add(soundMenuItem);
        helpMenu.add(rulesItem);
        add(fileMenu);
        add(settingsMenu);
        add(helpMenu);
    }
    
    public void showRulesDialog() 
    {
        JDialog rulesDialog = new JDialog();
        rulesDialog.setTitle("Kwazam Chess Rules");
        rulesDialog.setSize(600, 450);
        rulesDialog.setLocationRelativeTo(null);
        rulesDialog.setModal(true);
        
        JPanel rulesPanel = new JPanel();
        rulesPanel.setLayout(new GridLayout(0, 1, 10, 10));
        
        JLabel ramImage = new JLabel(resizeImage("Picture/Ram_Blue.png", 50, 50));
        JLabel bizImage = new JLabel(resizeImage("Picture/Biz_Blue.png", 50, 50));
        JLabel torImage = new JLabel(resizeImage("Picture/Tor_Blue.png", 50, 50));
        JLabel xorImage = new JLabel(resizeImage("Picture/Xor_Blue.png", 50, 50));
        JLabel sauImage = new JLabel(resizeImage("Picture/Sau_Blue.png", 50, 50));

        JTextArea ramRules = new JTextArea("The Ram piece can only move forward, 1 step. If it reaches the end of the board, it turns around and starts heading back the other way. It cannot skip over other pieces.");
        JTextArea bizRules = new JTextArea("The Biz piece moves in a 3x2 L shape in any orientation (kind of like the knight in standard chess). This is the only piece that can skip over other pieces.");
        JTextArea torRules = new JTextArea("The Tor piece can move orthogonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Xor piece.");
        JTextArea xorRules = new JTextArea("The Xor piece can move diagonally only but can go any distance. It cannot skip over other pieces. However, after 2 turns, it transforms into the Tor piece.");
        JTextArea sauRules = new JTextArea("The Sau piece can move only one step in any direction. The game ends when the Sau is captured by the other side.");
        JTextArea generalRules = new JTextArea("General Rule: None of the pieces are allowed to skip over other pieces, except for Biz.");

        rulesPanel.add(imageAndRules(ramImage, ramRules));
        rulesPanel.add(imageAndRules(bizImage, bizRules));
        rulesPanel.add(imageAndRules(torImage, torRules));
        rulesPanel.add(imageAndRules(xorImage, xorRules));
        rulesPanel.add(imageAndRules(sauImage, sauRules));

        rulesPanel.setBackground(Color.WHITE);
        rulesDialog.add(rulesPanel);
        rulesDialog.setVisible(true);
        rulesDialog.setResizable(false);
    }
    
    private JPanel imageAndRules(JLabel image, JTextArea  rule)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        rule.setLineWrap(true);
        panel.add(image, BorderLayout.WEST);
        panel.add(rule, BorderLayout.CENTER);
        return panel;
    }
    
    private ImageIcon resizeImage(String imagePath, int width, int height)
    {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
    
    public JMenuItem getNewGameItem()
    {
        return newGameItem;
    }
    
    public JMenuItem getSaveGameItem()
    {
        return saveGameItem;
    }
    
    public JMenuItem getExitItem()
    {
        return exitItem;
    }
    
    public JMenuItem getRulesItem()
    {
        return rulesItem;
    }
    
    public JCheckBoxMenuItem getSoundMenuItem()
    {
        return soundMenuItem;
    }
}
