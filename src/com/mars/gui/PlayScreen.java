package com.mars.gui;

import com.mars.client.Audio;
import com.mars.client.CommandProcessor;
import com.mars.client.Display;
import com.mars.client.Game;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.Duration;
import java.time.Instant;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Vector;

public class PlayScreen extends JFrame implements ActionListener, ChangeListener, ItemListener, MouseListener, PropertyChangeListener {
    private JPanel mainPanel, topLeftPanel, bottomLeftPanel, noClockAndMapPanel, mapAndInventoryPanel, healthLevelsPanel, descriptionsPanel, directionPanel, utilitiesPanel, goNorthPanel, goSouthPanel, goWestPanel, goEastPanel, mapPanel, inventoryPanel;
    private JButton northButton, westButton, eastButton, southButton;
    private JComboBox itemsBox, menuDropDownBox;
    private JProgressBar progressO2Bar, progressHungerBar, progressStaminaBar;
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
    private JTextArea textField2;
    private JComboBox puzzleChoiceBox;
    private JButton submitPuzzleButton;
    private JPanel puzzlePanel;
    private JTextField targetHours;
    private JTextField targetMins;
    private JTextField targetSeconds;
    private Vector<String> items;
    private Font normalFont = new Font("Times New Roman", Font.ITALIC, 30);
    private CommandProcessor processor = new CommandProcessor();
    private DefaultListModel demoList = new DefaultListModel();;
    private Clip clip;
    private Instant futureTime;
    private Timer timer;
    private JLabel countDown;

    public PlayScreen(Instant instant) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setContentPane(mainPanel);
        setTitle("Survival Mission Mars");
        setSize(1250, 700);
        mainPanel.setBackground(Color.gray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        textField2.setText(Display.showTextFile("Intro"));
        clip = Audio.playAudio();

        // this is the timer
        setLayout(new GridBagLayout());

        JPanel targetPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(8, 8, 8, 8);

        add(targetPanel, gbc);

        JButton btn = new JButton("Start");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                futureTime = LocalDateTime.now()
                        .plusHours(1)
                        .plusMinutes(0)
                        .plusSeconds(0)
                        .atZone(ZoneId.systemDefault()).toInstant();

                if (timer != null) {
                    timer.stop();
                }

                countDown.setText("---");
                timer = new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Duration duration = Duration.between(Instant.now(), futureTime);
                        if (duration.isNegative()) {
                            timer.stop();
                            timer = null;
                            countDown.setText("00:00:00");
                        } else {
                            String formatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
                            countDown.setText(formatted);
                        }
                    }
                });
                timer.start();
            }
        });
        add(btn, gbc);

        countDown = new JLabel("---");
        add(countDown, gbc);
        // the end of timer

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
        useButton.addMouseListener(this);

        radioButtonMute.addMouseListener(this);

        volumeSlider.addChangeListener(this);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(10);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintTrack(true);

        progressO2Bar.addPropertyChangeListener(this);
        progressStaminaBar.addPropertyChangeListener(this);
        progressHungerBar.addPropertyChangeListener(this);
        menuDropDownBox.addActionListener(this);
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
        if (menuDropDownBox.getSelectedItem().equals("Instructions")){
            textField2.setText(Display.showTextFile("Help"));
        }
        if (menuDropDownBox.getSelectedItem().equals("Quit")){
            System.exit(0);
        }
        if (menuDropDownBox.getSelectedItem().equals("Restart")){
            clip.stop();
            clip.close();
            setVisible(false);
            new SplashScreen();
        }
        if (menuDropDownBox.getSelectedItem().equals("Save")){
            Game.save();
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        audioSlider();
    }

    private void audioSlider(){
        if (volumeSlider.getValue() == 0){
            clip.stop();
        }
        if(volumeSlider.getValue() == 10) {
            Audio.volumeDown3(clip);
        }
        if(volumeSlider.getValue() == 20) {
            Audio.volumeDown2(clip);
        }
        if(volumeSlider.getValue() == 30) {
            Audio.volumeDown1(clip);
        }
        if(volumeSlider.getValue() == 40) {
            Audio.volumeDown(clip);
        }
        if(volumeSlider.getValue() == 50) {
            Audio.volumeUp(clip);
        }
        if(volumeSlider.getValue() == 60) {
            Audio.volumeUp1(clip);
        }
        if(volumeSlider.getValue() == 70) {
            Audio.volumeUp2(clip);
        }
        if(volumeSlider.getValue() == 80) {
            Audio.volumeUp3(clip);
        }
        if(volumeSlider.getValue() == 90) {
            Audio.volumeUp4(clip);
        }
        if(volumeSlider.getValue() == 100) {
            Audio.volumeUp5(clip);
        }

    }
    private void directionButton(String e1) {
        itemsBox.removeAllItems();
        List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
        String result = processor.forGo(nextCommand);
        roomLabel.setText(processor.getCurrentLocation().getName());
        items = processor.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemsBox.addItem(items.get(i));
        }
        textField2.setText(result);
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
        try {
            List<String> nextCommand = processor.getCommand(e1);
            String get = processor.forGet(nextCommand);

            if (itemsBox.getSelectedItem().equals(get.replace(" ", "_"))) {
                itemsBox.removeItem(get.replace(" ", "_"));
            }

            if (get.equalsIgnoreCase("Your bag is full.")) {
                textField2.setText(get);
                return;
            }
            demoList.addElement(get.replace(" ", "_"));
            inventoryList.setModel(demoList);
            itemsBox.revalidate();
            itemsBox.repaint();
            textField2.setText(get);
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    private void useItem(){
        String useItem = inventoryList.getSelectedValue().toString();
        List<String> nextCommand = processor.getCommand("use " + useItem);
        String text = processor.forUse(nextCommand);
        int index = inventoryList.getSelectedIndex();
        if (index >= 0) {
            demoList.remove(index);
        }
        inventoryList.setModel(demoList);
        textField2.setText(text);
    }

    private void dropItem() {
        try {
            String dropItem = inventoryList.getSelectedValue().toString();
            List<String> nextCommand = processor.getCommand("drop " + dropItem);
            String drop = processor.forDrop(nextCommand);
            int index = inventoryList.getSelectedIndex();
            itemsBox.addItem(inventoryList.getSelectedValue().toString().replace(" ", "_"));
            itemsBox.revalidate();
            itemsBox.repaint();
            if (index >= 0) {
                demoList.remove(index);
            }
            inventoryList.setModel(demoList);
            textField2.setText(drop);

        } catch (Exception e) {}
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (dropButton.isSelected()) {
            dropItem();
        }
        if (useButton.isSelected()){
            useItem();
        }
        if(radioButtonMute.isSelected()) {
            clip.stop();
        }
        if(!radioButtonMute.isSelected()) {
            clip.start();
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            SwingUtilities.invokeLater(() -> progressO2Bar.setValue(Game.getStats().get("Oxygen")));
            java.lang.Thread.sleep(100);
        } catch (InterruptedException event) {}
        try {
            SwingUtilities.invokeLater(() -> progressStaminaBar.setValue(Game.getStats().get("Stamina")));
            java.lang.Thread.sleep(100);
        } catch (InterruptedException event) {}
        try {
            SwingUtilities.invokeLater(() -> progressHungerBar.setValue(Game.getStats().get("Hunger")));
            java.lang.Thread.sleep(100);
        } catch (InterruptedException event) {}
    }
}

