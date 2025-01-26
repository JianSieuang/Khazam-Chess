import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.event.MouseAdapter;

/* 
 * controller for home page, responsible for manage interaction between view and model
 * from the home page to other part of the application
 */
public class HomePageController {
    private static HomePageController controller; // singleton instance
    private HomePageView view; // view for home page
    private SettingController settingController; // controller for setting page
    private GamePageController gameController; // controller for game page

    /*
     * private constructor for singleton design pattern
     * initializes the Home Page and its listeners, and starts background music if
     * enabled.
     */
    private HomePageController() {
        view = new HomePageView(); // home page UI
        settingController = SettingController.getController(); // get setting controller

        // checking for music setting
        if (settingController.getIsMusicSoundEnabled()) {
            AudioPlayer.playBackgroundMusic(); // call audio player play music
        }
        initializeListeners(); // add listeners to buttons
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
            controller.view = new HomePageView();
            controller.initializeListeners();
        }
        return controller;
    }

    // set up listeners for buttons
    private void initializeListeners() {
        buttonSound(view.getHomePanel().getNewGameButton()); // add sound effect new game button
        view.getHomePanel().getNewGameButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { // action listener for new game button
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
                        view.dispose(); // close the home page
                        gameController = GamePageController.getController("New Game"); // start new game
                    }
                }
            }
        });

        buttonSound(view.getHomePanel().getLoadGameButton()); // add sound effect to button
        view.getHomePanel().getLoadGameButton().addActionListener(new ActionListener() { // action listener for load game button
            @Override
            public void actionPerformed(ActionEvent e) {
                // if game.txt exist then it will load the game, else show error dialog
                if (new File("game.txt").exists()) {
                    view.dispose(); // close the home page
                    gameController = GamePageController.getController("Load Game"); // load game

                } else {
                    view.showLoadGameErrorDialog(); // show error dialog
                }
            }
        });

        buttonSound(view.getHomePanel().getSettingsButton()); // add sound effect for setting button
        view.getHomePanel().getSettingsButton().addActionListener(new ActionListener() { // action listener for setting button
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose(); // close the home page
                settingController.navigateToSettingPage(view); // navigate to setting page
            }
        });

        buttonSound(view.getHomePanel().getQuitGameButton()); // add sound effect for quiz game button
        
        view.getHomePanel().getQuitGameButton().addActionListener(new ActionListener() { // action listener for quit game button
            @Override
            public void actionPerformed(ActionEvent e) {
                settingController.checkSetting(); // check setting before exit
                System.exit(0); // exit the game with code 0 mean no error
            }
        });
    }

    // add sound effect to button
    private void buttonSound(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { // mouse hover to the button
                button.setBackground(button.getBackground().darker()); //  button backgorund be darker when hover
                button.setForeground(Color.WHITE); // button text be white when hower
                new BtnSound("hover", settingController).actionPerformed(null); // play hover sound
            }

            @Override
            public void mouseExited(MouseEvent e) { // mouse exit from the button
                button.setBackground(button.getBackground().brighter()); // button background be brighter when exit
                button.setForeground(Color.BLACK); // text be back color back when the mouse exit the button
            }
        });

        button.addActionListener(e -> new BtnSound("click", settingController).actionPerformed(null)); // play click sound
    }
}
