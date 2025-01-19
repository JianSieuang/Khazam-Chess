public class SettingModel 
{
    private boolean soundEnabled;
    private GameBoard gameBoard;

    public SettingModel(GameBoard gameBoard) 
    {
        soundEnabled = true;
        this.gameBoard = gameBoard;
    }

    public void newGame() 
    {

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
        System.out.println("[Model] Sound is now: " + (soundEnabled ? "ON" : "OFF"));
    }

    public boolean isSoundEnabled() 
    {
        return soundEnabled;
    }
}
