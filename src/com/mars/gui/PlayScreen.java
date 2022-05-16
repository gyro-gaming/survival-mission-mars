package com.mars.gui;

import com.mars.client.CommandProcessor;
import com.mars.locations.Room;
import com.mars.timer.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class PlayScreen extends JFrame implements ActionListener, ItemListener {
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
    private JPanel roomAndClockPanel;
    private JPanel clockPanel;
    private JPanel roomPanel;
    private JList inventoryList;
    private JRadioButton dropButton;
    private JRadioButton useButton;
    private JPanel imagePanel;
    private JLabel roomLabel;
    private JLabel textField1;
    private JTextArea textField2;
    private Vector<String> items;
    private Font normalFont = new Font("Times New Roman", Font.ITALIC, 30);
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

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        westButton.addActionListener(this);
        eastButton.addActionListener(this);
        radioButtonGo.addActionListener(this);
        radioButtonLook.addActionListener(this);

        itemsBox.addItemListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String e1 = "";
        if (eastButton.equals(e.getSource()) && radioButtonGo.isSelected()) {
            directionButton("go east");
        } else if (westButton.equals(e.getSource()) && radioButtonGo.isSelected()) {
            directionButton("go west");
        } else if (northButton.equals(e.getSource()) && radioButtonGo.isSelected()) {
            directionButton("go north");
        } else if (southButton.equals(e.getSource()) && radioButtonGo.isSelected()) {
            directionButton("go south");
        } else if (eastButton.equals(e.getSource()) && radioButtonLook.isSelected()) {
            lookButton("look east");
        } else if (westButton.equals(e.getSource()) && radioButtonLook.isSelected()) {
            lookButton("look west");
        } else if (northButton.equals(e.getSource()) && radioButtonLook.isSelected()) {
            lookButton("look north");
        } else if (southButton.equals(e.getSource()) && radioButtonLook.isSelected()) {
            lookButton("look south");
        }
    }

    private void directionButton(String e1) {
        itemsBox.removeAllItems();
        List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
        Room room = processor.processCommand(nextCommand);
        roomLabel.setText(room.getName());
        items = processor.forItem(roomLabel.getText());
        for (int i = 0; i < items.size(); i++){
            itemsBox.addItem(items.get(i));
        }
        textField2.setText(room.toString());
    }

    private void lookButton(String e1) {
        List<String> nextCommand = processor.getCommand(e1);
        String look = processor.forLook(nextCommand);
        textField2.setText(look);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        try {
            if (itemsBox.equals(e.getSource()) && radioButtonInspect.isSelected()) {
                lookItem("inspect " + itemsBox.getSelectedItem().toString());
            } else if (itemsBox.equals(e.getSource()) && radioButtonGet.isSelected()) {
                getItem("inspect " + itemsBox.getSelectedItem().toString());
            }
        } catch (NullPointerException ex) {}
    }

    private void lookItem(String e1) {
        List<String> nextCommand = processor.getCommand(e1);
        String inspect = processor.forInspect(nextCommand);
        textField2.setText(inspect);
    }

    private void getItem(String e1) {
        List<String> nextCommand = processor.getCommand(e1);
        String get = processor.forGet(nextCommand);
        if (itemsBox.getSelectedItem().equals(get)) {
            itemsBox.removeItem(get);
        }
        textField2.setText(get);
    }
}

