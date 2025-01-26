import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/* 
 * model class for the nevigation bar in the gamem responsible to manage setting
 * game action (new game, save game, exit game) and sound setting
 */
public class NavbarModel {
    private boolean musicEnabled; // track music setting
    private boolean soundEnabled; // track button sound setting
    private GameBoard gameBoard; // reference game board model

    // constructor for navbar model
    public NavbarModel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        loadSettings("setting.txt"); // load setting.txt
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
        gameBoard.saveGame(); // call game board save game
    }

    // handle exit game, save progress if necessary
    // exitValue = value from dialog box
    // view = game page view
    public void exitGame(int exitValue, GamePageView view) {
        new BtnSound("click", SettingController.getController()).actionPerformed(null); // play button sound

        if (exitValue != -1) { // if player didn't close the dialog
            if (exitValue == 0) { // if player choose to save the game
                this.saveGame(); // save the game
            }

            view.dispose(); // close the game page
            HomePageController.getController(); // navigate to home page
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
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) { // read setting.txt file
            String line;
            while ((line = reader.readLine()) != null) { // read line by line
                if (line.startsWith("Button Sound")) { // check for button sound setting
                    soundEnabled = line.split(" : ")[1].trim().equalsIgnoreCase("On");
                } else if (line.startsWith("Music Sound")) { // check for music sound setting
                    musicEnabled = line.split(":")[1].trim().equalsIgnoreCase("On");
                }
            }
        } catch (IOException e) { // catch exception if file not found
            System.err.println("Error reading settings file: " + e.getMessage()); // print error message
        }
    }
}
