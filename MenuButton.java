import javax.swing.*;
import java.awt.*;

public class MenuButton
{
    // menu button function
    public static void addMenuButtons(JPanel panel, JFrame frame) {
        
        addNewGameButton(panel, frame);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacer
        
        addLoadGameButton(panel, frame);
        
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacer
        
        addSettingButton(panel, frame);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacer
        
        addQuitGameButton(panel, frame);
    }
    
    // new game btn funtion
    private static void addNewGameButton(JPanel panel, JFrame frame) {
        // new game btn backgrond color
        Color newGameBtnBgColor = new Color(144, 238, 144); // light green
    
        // new game btn funtion
        JButton newGameButton = ButtonDesign.createMenuButton("New Game", newGameBtnBgColor, frame, panel, () -> {
            JOptionPane.showMessageDialog(frame, "Start a new game!");
        });

        panel.add(newGameButton);
    }
    
    // load game btn function
    private static void addLoadGameButton(JPanel panel, JFrame frame) {
        // load game btn backgrond color
        Color loadGameBtnBgColor = new Color(173, 216, 230);    // light blue
        
        // load game button
        JButton loadGameButton = ButtonDesign.createMenuButton("Load Game", loadGameBtnBgColor, frame, panel, () -> {
            JOptionPane.showMessageDialog(frame, "Load an existing game!");
        });
        
        panel.add(loadGameButton);
    }
    
    // setting btn function
    private static void addSettingButton(JPanel panel, JFrame frame) {
        // setting btn backgrond color
        Color settingBtnBgColor = new Color(211, 211, 211); // light gray
        
        // setting button
        JButton settingButton = ButtonDesign.createMenuButton("Setting", settingBtnBgColor, frame, panel, () -> {
            SettingPage.showSetting(frame); // navigate to SettingsPage
        });

        panel.add(settingButton);
    }

    // quit game btn function
    private static void addQuitGameButton(JPanel panel, JFrame frame) {
        // quit game btn backgrond color
        Color quitGameBtnBgColor = new Color(240, 128, 128);    // light red
        
        // quit game button
        JButton quitGameButton = ButtonDesign.createMenuButton("Quit Game", quitGameBtnBgColor, frame,  panel,() -> {
            System.exit(0);
        });

        panel.add(quitGameButton);
    }
}
