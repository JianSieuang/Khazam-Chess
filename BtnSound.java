import java.awt.event.*;

public class BtnSound implements ActionListener {
    private String actionType;
    private SettingController settingController;

    // set action type
    public BtnSound(String actionType, SettingController controller) {
        this.actionType = actionType;
        this.settingController = controller;
    }

    // set the action btn sound
    @Override
    public void actionPerformed(ActionEvent e) {
        // play sound
        if (settingController.getIsSoundEnabled()) {
            if (actionType == "hover") {
                playSound("sources/hover_btn_sound.wav");
            } else if (actionType == "click") {
                playSound("sources/click_btn_sound.wav");
            }
        }
    }

    // method to play the sound
    private void playSound(String filePath) {
        try {
            java.io.File soundFile = new java.io.File(filePath);
            javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem
                    .getAudioInputStream(soundFile);

            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();

            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}