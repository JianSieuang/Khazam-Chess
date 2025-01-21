import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NavbarModel {
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

    public void exitGame() {

    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public void loadSettings(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Sound")) {
                    soundEnabled = line.split(":")[1].trim().equalsIgnoreCase("On");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading settings file: " + e.getMessage());
        }
    }
}
