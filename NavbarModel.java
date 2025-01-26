import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NavbarModel {
    private boolean musicEnabled;
    private boolean soundEnabled;
    private GameBoard gameBoard;

    public NavbarModel(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        loadSettings("setting.txt");
    }

    public void newGame(int choice) {
        if (choice == JOptionPane.YES_OPTION) {
            gameBoard.newGame();
        }
    }

    public void saveGame() {
        gameBoard.saveGame();
    }

    public void exitGame(int exitValue, GamePageView view) {
        new BtnSound("click", SettingController.getController()).actionPerformed(null);

        if (exitValue != -1) {
            if (exitValue == 0) {
                this.saveGame();
            }

            view.dispose();
            HomePageController.getController();
        }
    }

    public void toogleButtonSound() {
        soundEnabled = !soundEnabled;
    }

    public void toggleSound() {
        musicEnabled = !musicEnabled;
    }

    public boolean getIsButtonSoundEnabled() {
        return soundEnabled;
    }

    public boolean getIsMusicSoundEnabled() {
        return musicEnabled;
    }

    public void loadSettings(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Button Sound")) {
                    soundEnabled = line.split(" : ")[1].trim().equalsIgnoreCase("On");
                } else if (line.startsWith("Music Sound")) {
                    musicEnabled = line.split(":")[1].trim().equalsIgnoreCase("On");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }
}
