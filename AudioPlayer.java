import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class AudioPlayer {
    private static Clip currentClip; // keep track of the currently playing clip
    private static final Random RANDOM = new Random();
    private static SettingController settingController;

    // array of your 3 background music files
    private static final String[] MUSIC_FILES = {
            "sources/background_music_1.wav",
            "sources/background_music_2.wav",
            "sources/background_music_3.wav"
    };

    // add THIS method so SettingController can register itself ▼
    public static void setSettingController(SettingController controller) {
        settingController = controller;
    }

    public static void playBackgroundMusic() {
        // Start with a random track
        int randomIndex = getRandomIndex();
        playTrack(randomIndex);
    }

    private static int getRandomIndex() {
        // Returns a random number from 0 to MUSIC_FILES.length - 1
        return RANDOM.nextInt(MUSIC_FILES.length);
    }

    private static void playTrack(int index) {
        // stopBackgroundMusic(); // stop if any clip is currently running
        try {
            if (settingController.getIsSoundEnabled()) {
                if (currentClip != null && currentClip.isRunning()) {
                    return;
                }
                // Open a new clip
                File musicFile = new File(MUSIC_FILES[index]);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);

                // When the track stops, pick the next random track and play it
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
                // Start playing this clip
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