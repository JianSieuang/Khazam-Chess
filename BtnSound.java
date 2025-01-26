import java.awt.event.*;

/*
 * class to handle button sound effect for hover and click function
 */
public class BtnSound implements ActionListener {
    private String actionType; // action type ("hover", "click")
    private SettingController settingController; // controller to check if button sound is enabled

    /* 
     * constructor for button sound
     * actionType = type of action ("hover", "click")
     * controller = setting controller to check if button sound is enabled
     */
    public BtnSound(String actionType, SettingController controller) {
        this.actionType = actionType;
        this.settingController = controller;
    }

    // set the action btn sound
    @Override
    public void actionPerformed(ActionEvent e) {
        //play sound if button sound is enabled in setting
        if (settingController.getIsButtonSoundEnabled()) {
            // play sound based on the action type
            if (actionType == "hover") {
                playSound("sources/hover_btn_sound.wav");
            } else if (actionType == "click") {
                playSound("sources/click_btn_sound.wav");
            }
        }
    }

    // method to play the sound
    // filePath = path to the sound file
    private void playSound(String filePath) {
        try {
            // load sound file
            java.io.File soundFile = new java.io.File(filePath);
            javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(soundFile);

            // create clip object
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();

            clip.open(audioStream); // open the audio stream
            clip.start(); // start play the sound
        } catch (Exception ex) {
            ex.printStackTrace(); // print error message
        }
    }
}