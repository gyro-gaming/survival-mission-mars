package com.mars.gui;

import com.mars.client.CommandProcessor;
import com.mars.locations.Room;
import com.mars.timer.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

public class PlayScreen extends JFrame implements ActionListener, ItemListener, MouseListener {
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
    private JComboBox puzzleChoiceBox;
    private JButton submitPuzzleButton;
    private JPanel puzzlePanel;
    private JButton SUBMITButton;
    private JTextField targetHours;
    private JTextField targetMins;
    private JTextField targetSeconds;
    private Vector<String> items;
    private Font normalFont = new Font("Times New Roman", Font.ITALIC, 30);
    private CommandProcessor processor = new CommandProcessor();
    private DefaultListModel demoList = new DefaultListModel();
    private GameTimer gameTimer;
    private LocalDateTime futureTime;

    public PlayScreen(Instant instant) {
        setContentPane(mainPanel);
        setTitle("Survival Mission Mars");
        setSize(1250, 700);
        mainPanel.setBackground(Color.gray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        targetHours = new JTextField("00", 2);
        targetMins = new JTextField("00", 2);
        targetSeconds = new JTextField("00", 2);

        gameTimer = new GameTimer(60 * 60000L);
        futureTime = LocalDateTime.now()
                .plusHours(Long.parseLong(targetHours.getText()))
                .plusMinutes(Long.parseLong(targetMins.getText()))
                .plusSeconds(Long.parseLong(targetSeconds.getText()));
        Duration duration = Duration.between(instant, futureTime.plusMinutes(gameTimer.getDelay() / 60000L).atZone(ZoneId.systemDefault()).toInstant());



        northButton.addActionListener(this);
        southButton.addActionListener(this);
        westButton.addActionListener(this);
        eastButton.addActionListener(this);
        radioButtonGo.addActionListener(this);
        radioButtonLook.addActionListener(this);
        radioButtonGet.addActionListener(this);
        radioButtonInspect.addActionListener(this);
        itemsBox.addItemListener(this);
        dropButton.addMouseListener(this);
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
                getItem("get " + itemsBox.getSelectedItem().toString());
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
        demoList.addElement(get);
        inventoryList.setModel(demoList);
        //textField2.setText(get);
        itemsBox.revalidate();
        itemsBox.repaint();

    }
    private void dropItem(){
        try {
            String dropItem = inventoryList.getSelectedValue().toString();
            List<String> nextCommand = processor.getCommand("drop " + dropItem);
            processor.forDrop(nextCommand);
            int index = inventoryList.getSelectedIndex();
            if (index >= 0) {
                demoList.remove(index);
            }
            inventoryList.setModel(demoList);
        } catch (Exception e) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (dropButton.isSelected()){
            dropItem();
        }
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

