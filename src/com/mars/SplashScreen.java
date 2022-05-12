package com.mars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mars.PlayScreen;

public class SplashScreen {

    JFrame window;
    ImageIcon backgroundImage;
    Container container;
    JPanel titleNamePanel, startButtonPanel, instructionsButtonPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    JLabel titleNameLabel, hpLabel, hpLabelNumber, inventoryLabel, inventoryLabelName, myLabel;
    Font titleFont = new Font("Times New Roman", Font.PLAIN, 60);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton, instructionsButton, choice1, choice2, choice3, choice4;
    JTextArea mainTextArea;
    int playerHP;
    String inventory;

    TitleScreenHandler tsHandler = new TitleScreenHandler();



    public SplashScreen() {

        backgroundImage = new ImageIcon("data/images/mars1.png");
        myLabel = new JLabel(backgroundImage);
        myLabel.setSize(800, 600);
        window = new JFrame("Survival Mission Mars");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setVisible(true);
        container = window.getContentPane();

        titleNamePanel = new JPanel();
        titleNamePanel.setBounds(150, 100, 500, 100);
        titleNamePanel.setBackground(Color.black);
        titleNameLabel = new JLabel("Survival Mission Mars");
        titleNameLabel.setForeground(Color.white);
        titleNameLabel.setFont(titleFont);

        instructionsButtonPanel = new JPanel();
        instructionsButtonPanel.setBounds(250, 350, 280, 100);
        instructionsButtonPanel.setBackground(Color.black);

        instructionsButton = new JButton("INSTRUCTIONS");
        instructionsButton.setBackground(Color.black);
        instructionsButton.setForeground(Color.black);
        instructionsButton.setFont(normalFont);
//        instructionsButton.addActionListener(tsHandler);

        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(350, 400, 150, 50);
        startButtonPanel.setBackground(Color.black);

        startButton = new JButton("START");
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.addActionListener(tsHandler);
        startButton.setFocusPainted(false);

        titleNamePanel.add(titleNameLabel);
        instructionsButtonPanel.add(instructionsButton);
        startButtonPanel.add(startButton);

        container.add(titleNamePanel);
        container.add(instructionsButtonPanel);
        container.add(startButtonPanel);
        container.add(myLabel);
    }


    public void PlayScreen() {
        new PlayScreen();
    }



    public class TitleScreenHandler implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            PlayScreen();
        }
    }

}