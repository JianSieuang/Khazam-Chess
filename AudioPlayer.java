import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Model, utility class for playing background music and controlling audio
 * settings.
 */
public class AudioPlayer {
    private static Clip currentClip; // keep track of the currently playing clip
    private static final Random RANDOM = new Random(); // random number generator
    private static SettingController settingController; // reference to the SettingController

    // array of 2 background music files
    private static final String[] MUSIC_FILES = {
            "sources/background_music_1.wav",
            "sources/background_music_2.wav"
    };

    // registers the SettingController so the audio player can check if music is
    // enabled.
    public static void setSettingController(SettingController controller) {
        settingController = controller; // set the SettingController
    }

    // play the background music
    public static void playBackgroundMusic() {
        // start with a random track
        int randomIndex = getRandomIndex();
        playTrack(randomIndex);
    }

    private static int getRandomIndex() {
        // returns a random number from 0 to MUSIC_FILES.length - 1
        return RANDOM.nextInt(MUSIC_FILES.length);
    }

    private static void playTrack(int index) {
        try {
            // if music is enabled, play the track
            if (settingController.getIsMusicSoundEnabled()) {
                if (currentClip != null && currentClip.isRunning()) {
                    return;
                }

                // open the audio file and play it
                File musicFile = new File(MUSIC_FILES[index]);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);

                // when the track stops, pick the next random track and play it
                currentClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        currentClip.close();
                        int nextIndex;
                        do {
                            nextIndex = getRandomIndex();
                        } while (nextIndex == index);
                        playTrack(nextIndex);
                    }
                });
                // start playing this clip
                currentClip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
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