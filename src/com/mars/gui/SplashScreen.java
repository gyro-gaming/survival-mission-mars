package com.mars.gui;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;

public class SplashScreen {

    JFrame window;
    ImageIcon backgroundImage;
    Container container;
    JPanel titleNamePanel, startButtonPanel, instructionsButtonPanel;
    JLabel titleNameLabel, myLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, instructionsButton;

    TitleScreenHandler tsHandler = new TitleScreenHandler();

    public SplashScreen() {

        backgroundImage = new ImageIcon("data/images/mars1.png");
        myLabel = new JLabel(backgroundImage);
        myLabel.setSize(800, 550);
        window = new JFrame("Survival Mission Mars");
        window.setSize(800, 550);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(100, 100, 600, 65);
        titleNamePanel.setBackground(Color.gray);
        titleNameLabel = new JLabel("Survival Mission Mars");
        titleNameLabel.setForeground(Color.red);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(310, 400, 140, 48);
        startButtonPanel.setBackground(Color.red);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        container.add(titleNamePanel);
        container.add(startButtonPanel);
        container.add(myLabel);
    }

    public void PlayScreen() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new PlayScreen();
        window.setVisible(false);
    }

    public class TitleScreenHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                PlayScreen();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
