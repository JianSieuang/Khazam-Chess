import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AudioPlayer {
    private static Clip currentClip; // keep track of the currently playing clip

    // play music
    public static void playBackgroundMusic() {
        try {
            if (currentClip == null || !currentClip.isRunning()) {
                stopBackgroundMusic(); // stop any currently playing music
                File musicFile = new File("sources/background_music.wav");
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                currentClip = AudioSystem.getClip(); // assign the new clip to currentClip
                currentClip.open(audioStream);
                currentClip.loop(Clip.LOOP_CONTINUOUSLY); // loop the music
                currentClip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // stop the current music
    public static void stopBackgroundMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
            currentClip = null; // clear clip
        }
    }
}
