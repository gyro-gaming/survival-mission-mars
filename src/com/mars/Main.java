package com.mars;

import com.mars.client.Audio;
import com.mars.client.CommandProcessor;
import com.mars.client.Game;
import com.mars.gui.SplashScreen;
import com.mars.timer.AbstractTimer;
import com.mars.timer.GameTimer;
import com.mars.timer.OxygenTimer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Date;

class Main {
    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        new SplashScreen();
        Clip clip = AudioSystem.getClip();
        Audio.playAudio(clip);

    }
}