import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.MouseAdapter;

// Sim Boon Xun
/* 
 * controller for home page, responsible for manage interaction between view and model
 * from the home page to other part of the application
 */
public class HomePageController {
    // singleton instance
    private static HomePageController controller;
    
    // view for home page
    private HomePageView view;

    // controller for setting page
    private SettingController settingController;

    /*
     * private constructor for singleton design pattern
     * initializes the Home Page and its listeners, and starts background music if
     * enabled.
     */
    private HomePageController() {
        // home page UI
        view = new HomePageView();

        // get setting controller
        settingController = SettingController.getController();

        // checking for music setting
        if (settingController.getIsMusicSoundEnabled()) {
            // call audio player play music
            AudioPlayer.playBackgroundMusic();
        }
        // add listeners to buttons
        initializeListeners();
    }

    /*
     * singleton method to get controller instance
     * if controller don't exist, create new instance
     * if controller exist, reset view and initialize listeners
     */
    public static HomePageController getController() {
        if (controller == null) {
            controller = new HomePageController();
        } else {
            controller.view.setVisible(true);
        }
        return controller;
    }

    // set up listeners for buttons
    private void initializeListeners() {
        // add sound effect new game button
        buttonSound(view.getHomePanel().getNewGameButton());
        view.getHomePanel().getNewGameButton().addActionListener(new ActionListener() {
            // action listener for new game button
            @Override
            public void actionPerformed(ActionEvent e) {
                int value = 0;

                // if the game.txt exist then it will show the dialog message to ask player
                if (new File("game.txt").exists()) {
                    value = view.showAttentionDialog();
                }

                // if the player click no or close the window then it will not start new game
                // if the player click yes then it will start new game
                // if didn't find the game.txt then it will start new game
                if (value != -1) {
                    if (value == 0) {
                        // close the home page
                        view.dispose();
                        // start new game
                        GamePageController.getController("New Game");
                    }
                }
            }
        });

        // add sound effect to button
        buttonSound(view.getHomePanel().getLoadGameButton());
        
        // action listener for load game button
        view.getHomePanel().getLoadGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if game.txt exist then it will load the game, else show error dialog
                if (new File("game.txt").exists()) {
                    // close the home page
                    view.dispose();
                    // load game
                    GamePageController.getController("Load Game");
                } else {
                    // show error dialog
                    view.showLoadGameErrorDialog();
                }
            }
        });

        // add sound effect for setting button
        buttonSound(view.getHomePanel().getSettingsButton());

        // action listener for setting button
        view.getHomePanel().getSettingsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // close the home page
                view.dispose();
                
                // navigate to setting page
                settingController.navigateToSettingPage(view);
            }
        });

        // add sound effect for quiz game button
        buttonSound(view.getHomePanel().getQuitGameButton());

        // action listener for quit game button
        view.getHomePanel().getQuitGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check setting before exit
                settingController.checkSetting();

                // exit the game with code 0 mean no error
                System.exit(0); 
            }
        });
    }

    // add sound effect to button
    private void buttonSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            // mouse hover to the button
            @Override
            public void mouseEntered(MouseEvent e) {
                // button backgorund be darker when hover
                button.setBackground(button.getBackground().darker());
                
                // button text be white when hower
                button.setForeground(Color.WHITE);

                // play hover sound
                new BtnSound("hover", settingController).actionPerformed(null);
            }

            // mouse release the button
            @Override
            public void mouseReleased(MouseEvent e) {
                // button backgorund be darker when realease
                button.setBackground(button.getBackground().brighter());
                
                // button text be black when release
                button.setForeground(Color.BLACK);
            }

            // mouse exit from the button
            @Override
            public void mouseExited(MouseEvent e) { 

                // button background be brighter when exit
                button.setBackground(button.getBackground().brighter());
                
                // text be back color back when the mouse exit the button
                button.setForeground(Color.BLACK);
            }
        });
        // play click sound
        button.addActionListener(e -> new BtnSound("click", settingController).actionPerformed(null));
    }
}