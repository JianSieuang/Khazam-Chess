import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* 
 * model class for the nevigation bar in the gamem responsible to manage setting
 * game action (new game, save game, exit game) and sound setting
 */
public class NavbarModel {
    // track music setting
    private boolean musicEnabled;
    
    // track button sound setting
    private boolean soundEnabled;
    
    // reference game board model
    private GameBoard gameBoard;

    // constructor for navbar model
    public NavbarModel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        
        // load setting.txt
        loadSettings("setting.txt");
    }

    // start new game based on player choise
    public void newGame(int choice) {
        // if player choose yes then start new game
        if (choice == JOptionPane.YES_OPTION) {
            gameBoard.newGame();
        }
    }

    // save the current game to a game.txt file
    public void saveGame() {
        // call game board save game
        gameBoard.saveGame();
    }

    // handle exit game, save progress if necessary
    // exitValue = value from dialog box
    // view = game page view
    public void exitGame(int exitValue, GamePageView view) {
        // play button sound
        new BtnSound("click", SettingController.getController()).actionPerformed(null);

        // if player didn't close the dialog
        if (exitValue != -1) {
            // if player choose to save the game
            if (exitValue == 0) {
                // save the game
                this.saveGame();
            }

            // close the game page
            view.dispose();
            
            // navigate to home page
            HomePageController.getController();
        }
    }

    // toggle button sound setting
    public void toogleButtonSound() {
        soundEnabled = !soundEnabled;
    }

    // toggle music sound setting
    public void toggleSound() {
        musicEnabled = !musicEnabled;
    }

    public boolean getIsButtonSoundEnabled() {
        return soundEnabled;
    }

    public boolean getIsMusicSoundEnabled() {
        return musicEnabled;
    }

    // load setting from setting.txt file
    public void loadSettings(String filePath) {
        // read setting.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { 
            String line;
            
            // read line by line
            while ((line = reader.readLine()) != null) { 
                // check for button sound setting
                if (line.startsWith("Button Sound")) { 
                    soundEnabled = line.split(" : ")[1].trim().equalsIgnoreCase("On");

                    // check for music sound setting
                } else if (line.startsWith("Music Sound")) { 
                    musicEnabled = line.split(":")[1].trim().equalsIgnoreCase("On");
                }
            }
        } catch (IOException e) { 
            // print error message
            System.err.println("Error reading settings file: " + e.getMessage()); 
        }
    }
}
