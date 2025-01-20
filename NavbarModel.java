import javax.swing.JOptionPane;


public class NavbarModel 
{
    private boolean soundEnabled;
    private GameBoard gameBoard;

    public NavbarModel(GameBoard gameBoard) 
    {
        soundEnabled = true;
        this.gameBoard = gameBoard;
    }

    public void newGame(int choice) 
    {
        if (choice == JOptionPane.YES_OPTION) 
        {
            gameBoard.newGame();
        }
    }

    public void saveGame() 
    {
        gameBoard.saveGame();
    }   
    
    public void exitGame()
    {
        
    }

    public void toggleSound() 
    {
        soundEnabled = !soundEnabled;
    }

    public boolean isSoundEnabled() 
    {
        return soundEnabled;
    }
}
