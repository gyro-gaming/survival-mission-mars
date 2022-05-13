package com.mars.gui;

import javax.swing.*;
import java.awt.*;

public class PlayScreen extends JFrame {
    private JPanel mainPanel, topLeftPanel, bottomLeftPanel, noClockAndMapPanel, mapAndClockPanel, healthLevelsPanel, descriptionsPanel, directionPanel, utilitiesPanel, goNorthPanel, goSouthPanel, goWestPanel, goEastPanel, mapPanel, clockPanel, roomPanel;
    private JButton northButton, westButton, eastButton, southButton;
    private JComboBox itemsBox, menuDropDownBox;
    private JProgressBar progressO2Bar, progressHealthBar, progressStaminaBar;
    private JLabel o2LevelLabel, healthLabel, staminaLabel;
    private JRadioButton radioButtonLook, radioButtonGo, radioButtonInspect, radioButtonGet, radioButtonUse, radioButtonMute;
    private JSlider volumeSlider;
    private JTextArea infoTextArea;
    private JPanel menuControlPanel;
    private JLabel roomLabel;
    private JLabel volumeLabel;
    private JLabel menuLabel;
    private JLabel muteLabel;
    Font normalFont = new Font("Times New Roman", Font.ITALIC, 30);

    public PlayScreen() {
        setContentPane(mainPanel);
        setTitle("Survival Mars Mission");
        setSize(800, 600);
        mainPanel.setBackground(Color.gray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


//        noClockAndMapPanel = new JPanel();
//        noClockAndMapPanel.setSize(600,600);
//        noClockAndMapPanel.setBackground(Color.blue);
//
//        mapAndClockPanel = new JPanel();
//        mapAndClockPanel.setSize(200, 600);
//        mapAndClockPanel.setBackground(Color.red);
//
//        topLeftPanel = new JPanel();
//        bottomLeftPanel = new JPanel();
//        clockPanel = new JPanel();
//        mapPanel = new JPanel();
//        roomPanel = new JPanel();
//        menuControlPanel = new JPanel();
//        descriptionsPanel = new JPanel();
//        healthLevelsPanel = new JPanel();
//        directionPanel = new JPanel();
//        utilitiesPanel = new JPanel();
//        radioButtonLook = new JRadioButton();
//        radioButtonGo = new JRadioButton();
//        infoTextArea = new JTextArea();
//        progressHealthBar = new JProgressBar();
//        progressO2Bar = new JProgressBar();
//        progressStaminaBar = new JProgressBar();
//        o2LevelLabel = new JLabel();
//        healthLabel = new JLabel();
//        staminaLabel = new JLabel();
//        infoTextArea = new JTextArea();
//        roomLabel = new JLabel();
//        menuDropDownBox = new JComboBox();
//        volumeSlider = new JSlider();
//        radioButtonMute = new JRadioButton();
//        menuLabel = new JLabel();
//        volumeLabel = new JLabel();
//        muteLabel = new JLabel();
//        goNorthPanel = new JPanel();
//        goSouthPanel = new JPanel();
//        goWestPanel = new JPanel();
//        goEastPanel = new JPanel();
//        northButton = new JButton();
//        northButton.setFont(normalFont);
//        northButton.setForeground(Color.pink);
//        southButton = new JButton();
//        westButton = new JButton();
//        eastButton = new JButton();
//        radioButtonInspect = new JRadioButton();
//        radioButtonGet = new JRadioButton();
//        radioButtonUse = new JRadioButton();
//        itemsBox = new JComboBox();
//
//        utilitiesPanel.add(itemsBox);
//        utilitiesPanel.add(radioButtonUse);
//        utilitiesPanel.add(radioButtonGet);
//        utilitiesPanel.add(radioButtonInspect);
//        goEastPanel.add(eastButton);
//        goWestPanel.add(westButton);
//        goSouthPanel.add(southButton);
//        goNorthPanel.add(northButton);
//        directionPanel.add(goEastPanel);
//        directionPanel.add(goNorthPanel);
//        directionPanel.add(goSouthPanel);
//        directionPanel.add(goWestPanel);
//        menuControlPanel.add(menuDropDownBox);
//        menuControlPanel.add(volumeSlider);
//        menuControlPanel.add(radioButtonMute);
//        menuControlPanel.add(menuLabel);
//        menuControlPanel.add(volumeLabel);
//        menuControlPanel.add(muteLabel);
//        roomPanel.add(roomLabel);
//        descriptionsPanel.add(infoTextArea);
//        healthLevelsPanel.add(o2LevelLabel);
//        healthLevelsPanel.add(healthLabel);
//        healthLevelsPanel.add(staminaLabel);
//        healthLevelsPanel.add(progressHealthBar);
//        healthLevelsPanel.add(progressO2Bar);
//        healthLevelsPanel.add(progressStaminaBar);
//        directionPanel.add(infoTextArea);
//        bottomLeftPanel.add(radioButtonGo);
//        bottomLeftPanel.add(radioButtonLook);
//        bottomLeftPanel.add(utilitiesPanel);
//        bottomLeftPanel.add(directionPanel);
//        topLeftPanel.add(roomPanel);
//        topLeftPanel.add(descriptionsPanel);
//        topLeftPanel.add(healthLevelsPanel);
//        topLeftPanel.add(menuControlPanel);
//        mapAndClockPanel.add(clockPanel);
//        mapAndClockPanel.add(mapPanel);
//        noClockAndMapPanel.add(bottomLeftPanel);
//        noClockAndMapPanel.add(topLeftPanel);
//        mainPanel.add(noClockAndMapPanel);
//        mainPanel.add(mapAndClockPanel);

    }



}
