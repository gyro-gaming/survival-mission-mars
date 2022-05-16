package com.mars.client;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {

    public static Clip playAudio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        // create new file and passed into getAudioInputAStream
        File f = new File("data/sounds/outer-space-air.wav");
        AudioInputStream as = AudioSystem.getAudioInputStream(f);
        Clip clip = AudioSystem.getClip();
        // open the audio file
        clip.open(as);
        // start the audio file
        clip.start();
        // makes an infinite loop for the clip
        clip.loop(-1);
        return clip;
    }
    public static void stopAudio(Clip clip){
        // stops audio file and close it
        clip.stop();
        clip.close();
    }
    public static void volume(Clip clip){
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(gainControl.getValue()); // Reduce volume by 10 decibels.
        clip.start();
    }


}
