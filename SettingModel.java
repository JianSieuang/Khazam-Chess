public class SettingModel 
{
    private boolean soundEnabled;

    public SettingModel() 
    {
        soundEnabled = true;
    }

    public void newGame() 
    {
        System.out.println("[Model] Starting a new game...");
    }

    public void saveGame() 
    {
        System.out.println("[Model] Saving the game...");
    }
    
    public void exitGame()
    {
        System.out.println("Bye bye");
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
