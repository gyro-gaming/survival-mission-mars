package com.mars;

import com.mars.client.Audio;
import com.mars.gui.SplashScreen;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

class Main {
    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        new SplashScreen();

    }
}