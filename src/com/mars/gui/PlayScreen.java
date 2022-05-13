package com.mars.gui;

import com.mars.client.CommandProcessor;
import com.mars.locations.Room;
import com.mars.timer.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

public class PlayScreen extends JFrame {
    private JPanel mainPanel, topLeftPanel, bottomLeftPanel, noClockAndMapPanel, mapAndInventoryPanel, healthLevelsPanel, descriptionsPanel, directionPanel, utilitiesPanel, goNorthPanel, goSouthPanel, goWestPanel, goEastPanel, mapPanel, inventoryPanel;
    private JButton northButton, westButton, eastButton, southButton;
    private JComboBox itemsBox, menuDropDownBox;
    private JProgressBar progressO2Bar, progressHealthBar, progressStaminaBar;
    private JLabel o2LevelLabel, healthLabel, staminaLabel;
    private JRadioButton radioButtonLook, radioButtonGo, radioButtonInspect, radioButtonGet, radioButtonMute;
    private JSlider volumeSlider;
    private JPanel menuControlPanel;
    private JLabel volumeLabel;
    private JLabel menuLabel;
    private JLabel muteLabel;
    private JScrollPane informationPanel;
    private JPanel roomAndClockPanel;
    private JPanel clockPanel;
    private JPanel roomPanel;
    private JList inventoryList;
    private JRadioButton dropButton;
    private JRadioButton useButton;
    private JPanel imagePanel;
    private JLabel roomLabel;
    private JLabel textField1;
    Font normalFont = new Font("Times New Roman", Font.ITALIC, 30);
    private CommandProcessor processor = new CommandProcessor();
    public PlayScreen() {
        setContentPane(mainPanel);
        setTitle("Survival Mission Mars");
        setSize(1250, 700);
        mainPanel.setBackground(Color.gray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


            clockPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    GameTimer gt = new GameTimer();
                    long timer = (gt.printCurrentTime());
                    Date date = new Date(timer);
                    String stringFormCurrentTime = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timer * 1000);
                    textField1.setText("Current Time: " + stringFormCurrentTime);
                }
            });
        northButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String e1 = "go north";
                List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
                Room room = processor.processCommand(nextCommand);
                roomLabel.setText(room.getName());
            }
        });
        southButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String e1 = "go south";
                List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
                Room room = processor.processCommand(nextCommand);
                roomLabel.setText(room.getName());
            }
        });
        westButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String e1 = "go west";
                List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
                Room room = processor.processCommand(nextCommand);
                roomLabel.setText(room.getName());
            }
        });
        eastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String e1 = "go east";
                List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
                Room room = processor.processCommand(nextCommand);
                roomLabel.setText(room.getName());
            }
        });
    }

        private void createUIComponents() {
            // TODO: place custom component creation code here
        }
    }

