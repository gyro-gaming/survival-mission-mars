package com.mars.client;

import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;

import static org.junit.Assert.*;

public class AudioTest {
    Clip clip = Audio.playAudio();

    public AudioTest() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    }

    @Before
    public void init() {

    }

    @Test
    public void playAudio_shouldBeOpen_WhenPlayAudioIsCalled() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        assertTrue(clip.isOpen());
    }

    @Test
    public void stopAudio_shouldBeClosed_WhenStopAudioIsCalled() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.stopAudio(clip);
        assertFalse(clip.isOpen());
        assertFalse(clip.isRunning());
        assertFalse(clip.isActive());
    }

    @Test
    public void volumeDown() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Audio.volumeDown(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == -5);
        assertTrue(clip.isOpen());

    }

    @Test
    public void volumeDown1() {
        Audio.volumeDown1(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == -10);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeDown2() {
        Audio.volumeDown2(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == -20);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeDown3() {
        Audio.volumeDown3(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertEquals(-30.0, gainControl.getValue(), 0.0);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp() {
        Audio.volumeUp(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == +1);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp1() {
        Audio.volumeUp1(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertEquals(+2.000000238418579, gainControl.getValue(), 0.0);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp2() {
        Audio.volumeUp2(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertEquals(+3.000000238418579, gainControl.getValue(), 0.00);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp3() {
        Audio.volumeUp3(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == +4);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp4() {
        Audio.volumeUp4(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == +5);
        assertTrue(clip.isOpen());
    }

    @Test
    public void volumeUp5() {
        Audio.volumeUp5(clip);
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        assertTrue(gainControl.getValue() == +6);
        assertTrue(clip.isOpen());
    }
}