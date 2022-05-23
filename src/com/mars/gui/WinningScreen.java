package com.mars.gui;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class WinningScreen {
    JFrame window;
    ImageIcon backgroundImage;
    Container container;
    JPanel titleNamePanel, startButtonPanel,quitButtonPanel, instructionsButtonPanel;
    JLabel titleNameLabel, myLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton,quitButton, instructionsButton;

    WinningScreen.TitleScreenHandler tsHandler = new WinningScreen.TitleScreenHandler();
    WinningScreen.QuitButtonHandler quitButtonHandler = new WinningScreen.QuitButtonHandler();
    public WinningScreen() {

        backgroundImage = new ImageIcon("data/images/moon.png");
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
        titleNameLabel = new JLabel("YOU WON");
        titleNameLabel.setForeground(Color.red);
        titleNameLabel.setFont(titleFont);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(200, 400, 140, 48);
        startButtonPanel.setBackground(Color.red);

        startButton = new JButton("PLAY AGAIN");
        startButton.setBackground(Color.red);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);

        quitButtonPanel = new JPanel();
        quitButtonPanel.setBounds(400, 400, 140, 48);
        quitButtonPanel.setBackground(Color.red);

        quitButton = new JButton("QUIT");
        quitButton.setBackground(Color.red);
        quitButton.setForeground(Color.black);
        quitButton.setFont(normalFont);
        quitButton.addMouseListener(quitButtonHandler);
        quitButton.setFocusPainted(false);

        quitButtonPanel.add(quitButton);


        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        container.add(titleNamePanel);
        container.add(startButtonPanel);
        container.add(quitButtonPanel);
        container.add(myLabel);
    }

    public void PlayScreen() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new PlayScreen();
        window.setVisible(false);
    }
    public class QuitButtonHandler implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.exit(0);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
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
