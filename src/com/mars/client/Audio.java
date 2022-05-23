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

    public static void stopAudio(Clip clip) {
        // stops audio file and close it
        clip.stop();
        clip.close();
    }

    public static void volumeDown(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-5); // Reduce volume by 10 decibels.
        clip.start();
    }

    public static void volumeDown1(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-10); // Reduce volume by 10 decibels.
        clip.start();
    }

    public static void volumeDown2(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-20); // Reduce volume by 20 decibels.
        clip.start();
    }

    public static void volumeDown3(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-30); // Reduce volume by 30 decibels.
        clip.start();
    }

    public static void volumeUp(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+1); // increase volume by 1 decibels.
        clip.start();
    }

    public static void volumeUp1(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+2); // increase volume by 2 decibels.
        clip.start();
    }

    public static void volumeUp2(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+3); // Increase volume by 3 decibels.
        clip.start();
    }

    public static void volumeUp3(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+4); // Increase volume by 4 decibels.
        clip.start();
    }

    public static void volumeUp4(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+5); // Increase volume by 5 decibels.
        clip.start();
    }

    public static void volumeUp5(Clip clip) {
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+6); // Increase volume by 6 decibels.
        clip.start();
    }
}
