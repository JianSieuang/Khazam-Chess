public class SettingModel 
{
    private static boolean soundEnabled = true;

    public static boolean isSoundEnabled() 
    {
        return soundEnabled;
    }

    public static void toggleSound() 
    {
        soundEnabled = !soundEnabled;
    }
}