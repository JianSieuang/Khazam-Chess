import javax.swing.*;

public class NavigationBar extends JMenuBar 
{
    public NavigationBar() 
    {
        JMenu fileMenu = new JMenu("File");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem saveGameItem = new JMenuItem("Save Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        JCheckBoxMenuItem soundMenuItem = new JCheckBoxMenuItem("Sound");
        JMenuItem rulesItem = new JMenuItem("Rules");

        fileMenu.add(newGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.add(exitItem);
        settingsMenu.add(soundMenuItem);
        helpMenu.add(rulesItem);
        add(fileMenu);
        add(settingsMenu);
        add(helpMenu);
    }
}
