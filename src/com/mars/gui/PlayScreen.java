package com.mars.gui;

import com.mars.client.*;
import com.mars.items.Item;
import com.mars.locations.ChallengeRoom;
import com.mars.locations.Room;
import com.mars.players.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PlayScreen extends JFrame implements ActionListener, ChangeListener, ItemListener, MouseListener, PropertyChangeListener {
    private JPanel mainPanel, middleRowPanel, bottomRowPanel, outerWrapperPanel, mapAndInventoryPanel, healthLevelsPanel, descriptionsPanel, roomAndClockPanel, clockPanel, roomPanel, directionPanel, utilitiesPanel, goNorthPanel, goSouthPanel, goWestPanel, goEastPanel, mapPanel, inventoryPanel, topRowPanel, imagePanel, puzzlePanel, menuControlPanel;
    private JButton northButton, westButton, eastButton, southButton, submitPuzzleButton;
    private JComboBox itemsBox, menuDropDownBox;
    private JProgressBar progressO2Bar, progressHungerBar, progressStaminaBar;
    private JLabel o2LevelLabel, healthLabel, staminaLabel, volumeLabel, menuLabel, muteLabel, roomLabel, mapLabel, l, countDown, imageLabel;
    private JRadioButton radioButtonLook, radioButtonGo, radioButtonInspect, radioButtonGet, radioButtonMute, dropButton, useButton;
    private JSlider volumeSlider;
    private JList inventoryList;
    private JTextArea textArea1, textField2;
    private JComboBox puzzleChoiceBox;
    private JScrollPane textScrollPane;
    private Vector<String> items;
    private Font normalFont;
    private CommandProcessor processor;
    private DefaultListModel demoList, answerList;
    private Clip clip;
    private Instant futureTime;
    private Timer timer;
    private Map<String, Map<String, Boolean>> solved;
    private static Duration duration;

    public PlayScreen() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        normalFont = new Font("Times New Roman", Font.ITALIC, 30);
        processor = new CommandProcessor();
        demoList = new DefaultListModel();
        answerList = new DefaultListModel();
        setContentPane(mainPanel);
        setTitle("Survival Mission Mars");
        setSize(1800, 1000);
        mainPanel.setBackground(Color.gray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        textField2.setText(Display.showTextFile("Intro"));
        textField2.setBackground(Color.white);
        clip = Audio.playAudio();
        // this is the timer
        showTimer();

        roomLabel.setText(processor.getCurrentLocation().getName());
        showMap(processor.getCurrentLocation().getName());
        showRoomImage(processor.getCurrentLocation().getName());
        items = processor.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemsBox.addItem(items.get(i));
        }
        textField2.setText(Display.showTextFile("Intro"));

        northButton.addActionListener(this);
        southButton.addActionListener(this);
        westButton.addActionListener(this);
        eastButton.addActionListener(this);

        radioButtonGo.addActionListener(this);
        radioButtonLook.addActionListener(this);

        radioButtonGet.addActionListener(this);
        radioButtonInspect.addActionListener(this);

        itemsBox.addItemListener(this);
        menuDropDownBox.addActionListener(this);

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
    }

    // show countdown time
    public void showTimer() {
        futureTime = LocalDateTime.now()
                .plusHours(1)
                .plusMinutes(0)
                .plusSeconds(0)
                .atZone(ZoneId.systemDefault()).toInstant();

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String remainTime = "Remaining Time: ";
                duration = Duration.between(Instant.now(), futureTime);
                if (duration.isNegative()) {
                    timer.stop();
                    timer = null;
                    countDown.setText(remainTime + "00:00:00");
                    setVisible(false);
                    Audio.stopAudio(clip);
                    new LosingScreen();
                } else {
                    String formatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
                    countDown.setText(remainTime + formatted);
                }
            }
        });
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String e1 = "";

        if (radioButtonMute.isSelected()) {
            clip.stop();
        }
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

        try {
            if (radioButtonGet.isSelected() && !itemsBox.getSelectedItem().equals(" ")) {
                getItem("get " + itemsBox.getSelectedItem().toString());
            }
        } catch (NullPointerException ex) {
        }

        if (menuDropDownBox.getSelectedItem().equals("Instructions")) {
            textField2.setText(Display.showTextFile("Help"));
            menuDropDownBox.setSelectedIndex(0);
        }

        if (menuDropDownBox.getSelectedItem().equals("Quit")) {
            Game.quit();
        }

        if (menuDropDownBox.getSelectedItem().equals("Restart")) {
            clip.stop();
            clip.close();
            setVisible(false);
            new SplashScreen();
        }

        if (menuDropDownBox.getSelectedItem().equals("Save")) {
            Game.save();
        }

        if (menuDropDownBox.getSelectedItem().equals("Save/Quit")) {
            Game.save();
            Game.quit();
        }

        if (menuDropDownBox.getSelectedItem().equals("Retrieve Game")) {
            Player player = Game.retrieveSave();
            showLastScreen(player);
        }

        showMap(roomLabel.getText());
        showRoomImage(roomLabel.getText());
    }

    private void showLastScreen(Player player) {
        String remainTime = "Remaining Time: ";
        setVisible(false);
        try {
            PlayScreen p = new PlayScreen();
            p.processor.setCurrentLocation(player.getLocation());
            p.roomLabel.setText(processor.getCurrentLocation().getName());
            for (Item name : player.getInventory().getInventory()) {
                demoList.addElement(name.getName());
            }
            p.inventoryList.setModel(demoList);
            p.inventoryList.repaint();
            p.inventoryList.revalidate();
            p.showMap(processor.getCurrentLocation().getName());
            p.showRoomImage(processor.getCurrentLocation().getName());
            p.textField2.setText("This is a past game that belongs to:  " + player.getName() + " user");
            String formatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
            p.countDown.setText(remainTime + formatted);

        } catch (UnsupportedAudioFileException ex) {
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        audioSlider();
    }

    private void audioSlider() {
        radioButtonMute.setSelected(false);
        if (volumeSlider.getValue() == 0) {
            clip.stop();
        }
        if (volumeSlider.getValue() == 10) {
            Audio.volumeDown3(clip);
        }
        if (volumeSlider.getValue() == 20) {
            Audio.volumeDown2(clip);
        }
        if (volumeSlider.getValue() == 30) {
            Audio.volumeDown1(clip);
        }
        if (volumeSlider.getValue() == 40) {
            Audio.volumeDown(clip);
        }
        if (volumeSlider.getValue() == 50) {
            Audio.volumeUp(clip);
        }
        if (volumeSlider.getValue() == 60) {
            Audio.volumeUp1(clip);
        }
        if (volumeSlider.getValue() == 70) {
            Audio.volumeUp2(clip);
        }
        if (volumeSlider.getValue() == 80) {
            Audio.volumeUp3(clip);
        }
        if (volumeSlider.getValue() == 90) {
            Audio.volumeUp4(clip);
        }
        if (volumeSlider.getValue() == 100) {
            Audio.volumeUp5(clip);
        }

    }

    private void directionButton(String e1) {
        itemsBox.removeAllItems();
        textArea1.setText(null);
        puzzleChoiceBox.removeAllItems();
        List<String> nextCommand = processor.getCommand(e1);            // calling upon Parser to begin parse process
        String result = processor.forGo(nextCommand);
        roomLabel.setText(processor.getCurrentLocation().getName());
        items = processor.getItems();
        for (int i = 0; i < items.size(); i++) {
            itemsBox.addItem(items.get(i));
        }
        radioButtonGo.setSelected(false);
        textField2.setText(result);
        for (Room room : Game.getRooms()) {
            if (room.getName().equalsIgnoreCase(processor.getCurrentLocation().getName())) {
                if (room.isPuzzle()) {
                    runPuzzle(room.getName());
                }
            }
        }
    }

    private void showMap(String room) {
        Room room1 = new Room(room);
        int index = Game.getRooms().indexOf(room1);
        try {
            BufferedImage img = ImageIO.read(new File(Game.getRooms().get(index).getImage()));
            ImageIcon icon = new ImageIcon(img);
            mapLabel.setIcon(icon);
            mapLabel.repaint();
        } catch (IndexOutOfBoundsException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showRoomImage(String room) {
        Room room1 = new Room(room);
        int index = Game.getRooms().indexOf(room1);
        try {
            BufferedImage img = ImageIO.read(new File(Game.getRooms().get(index).getPicture()));
            ImageIcon icon = new ImageIcon(img);
            imageLabel.setIcon(icon);
            imageLabel.repaint();
        } catch (IndexOutOfBoundsException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            }

        } catch (NullPointerException ex) {
        }
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
            if (get.equalsIgnoreCase("Your bag is full.")) {
                textField2.setText(get);
                return;
            }
            itemsBox.removeItem(get.replace(" ", "_"));
            demoList.addElement(get.replace(" ", "_"));
            inventoryList.setModel(demoList);
            itemsBox.revalidate();
            itemsBox.repaint();
            textField2.setText(get);

        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    private void useItem() {
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

        } catch (Exception e) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (dropButton.isSelected()) {
            dropItem();
        }
        if (useButton.isSelected()) {
            useItem();
        }
        if (radioButtonMute.isSelected()) {
            clip.stop();
        }
        if (!radioButtonMute.isSelected()) {
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
        } catch (InterruptedException event) {
        }
        try {
            SwingUtilities.invokeLater(() -> progressStaminaBar.setValue(Game.getStats().get("Stamina")));
            java.lang.Thread.sleep(100);
        } catch (InterruptedException event) {
        }
        try {
            SwingUtilities.invokeLater(() -> progressHungerBar.setValue(Game.getStats().get("Hunger")));
            java.lang.Thread.sleep(100);
        } catch (InterruptedException event) {
        }
    }

    public void runPuzzle(String option) {
        ChallengeRoom.setPuzzleItemMap();
        try {
            boolean eligible = eligiblePuzzle(option);
            if (eligible) {
                textField2.setText(null);
                textField2.setText(Display.showTextFile(option));
            }
        } catch (NullPointerException e) {
        }
    }

    private boolean eligiblePuzzle(String option) {
        if (solved == null) {
            solved = new HashMap<>();
        }
        if (!solved.containsKey(option)) {
            Map<String, Boolean> temp = new HashMap<>();
            temp.put("a", false);
            temp.put("b", false);
            solved.put(option, temp);
            ChallengeRoom.setSolved(solved);

            if (Game.getSolved() == null) {
                Game.setSolved(new HashMap<>());
            }
            Map<String, Boolean> gameSolved = Game.getSolved();
            gameSolved.put(option, false);
            Game.setSolved(gameSolved);
        }

        solved = ChallengeRoom.getSolved();
        Map<String, Boolean> gameSolved = Game.getSolved();

        if (!gameSolved.get(option) && "Solar Array".equals(option)) {
            if (!solved.get(option).get("a")) {
                askQuestionA1(option);
            } else if (solved.get(option).get("a") && !solved.get(option).get("b")) {
                System.out.println("eligiblePuzzle() to askQuestionA2");
                askQuestionA2(option);
            }
            return true;
        } else if ("Reactor".equals(option) && !gameSolved.get(option)
                && gameSolved.get("Solar Array")) {
            if (!solved.get("Reactor").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Reactor").get("a")
                    && !solved.get("Reactor").get("b")) {
                askQuestionA2(option);
            }
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && "Environmental Control Room".equals(option)) {
            if (!solved.get("Environmental Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Environmental Control Room").get("a")
                    && !solved.get("Environmental Control Room").get("b")) {
                askQuestionA2(option);
            }
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && gameSolved.get("Environmental Control Room")
                && "Hydro Control Room".equals(option)) {
            if (!solved.get("Hydro Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Hydro Control Room").get("a")
                    && !solved.get("Hydro Control Room").get("b")) {
                askQuestionA2(option);
            }
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && gameSolved.get("Environmental Control Room")
                && gameSolved.get("Hydro Control Room")
                && "Green House".equals(option)) {
            if (!solved.get("Green House").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Green House").get("a")
                    && !solved.get("Green House").get("b")) {
                askQuestionA2(option);
            }
            return true;
        }
        return false;
    }

    private static int getRandomPuzzle(int num) {
        return (int) (Math.random() * num);
    }

    public static Puzzle getPuzzle(List<Puzzle> puzzleList) {
        int index = getRandomPuzzle(puzzleList.size());
        Puzzle puzzle = puzzleList.get(index);
        puzzleList.remove(index);
        Game.setPuzzles(puzzleList);
        return puzzle;
    }

    public void askQuestion(Puzzle puzzle) {
        textArea1.setText(puzzle.askQuestion());
    }

    public void getAnswers(Puzzle puzzle) {
        puzzleChoiceBox.removeAllItems();
        items = puzzle.getAnswers();
        Collections.shuffle(items);
        for (String item : items) {
            puzzleChoiceBox.addItem(item);
        }
    }

    public void getCorrect(Puzzle puzzle, String option, int result) {
        submitPuzzleButton.setMultiClickThreshhold(3000l);
        submitPuzzleButton.setEnabled(true);
        submitPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(puzzleChoiceBox.getSelectedItem().toString());
                answerResponse(puzzle, puzzleChoiceBox.getSelectedItem().toString(), option, result);
            }
        });
    }

    public Puzzle getQuestions() {
        Puzzle puzzle = PlayScreen.getPuzzle(Game.getPuzzles());
        askQuestion(puzzle);
        getAnswers(puzzle);
        return puzzle;
    }

    public static String getPuzzleQuestion(String option) {
        String question = "";
        switch (option) {
            case "Solar Array-a":
                question = "Would you like to attempt to bring the solar array online?";
                break;
            case "Solar Array-b":
                question = "Would you like to closely inspect the solar array";
                break;
            case "Reactor-a":
                question = "Would you like to attempt to bring the reactor online?";
                break;
            case "Reactor-b":
                question = "Would you like to closely inspect the reactor?";
                break;
            case "Environmental Control Room-a":
                question = "Would you like to attempt to bring the environmental controls online?";
                break;
            case "Environmental Control Room-b":
                question = "Would you like to closely inspect the environmental controls?";
                break;
            case "Hydro Control Room-a":
                question = "Would you like to attempt to bring the water controls online?";
                break;
            case "Hydro Control Room-b":
                question = "Would you like to closely inspect the water controls?";
                break;
            case "Green House-a":
                question = "Would you like to attempt to bring the green house online?";
                break;
            case "GreenHouse-b":
                question = "Would you like to closely inspect the soil?";
                break;
            default:
                break;
        }
        return question;
    }

    private void askQuestionA1(String option) {
        System.out.println("askQuestionA1 - option");
        if (ChallengeRoom.getInventory().contains(ChallengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && ChallengeRoom.getInventory().contains(ChallengeRoom.getPuzzleItemMap().get(option).get("a2"))
                && !solved.get(option).get("a")) {
            Puzzle puzzle = getQuestions();
            getCorrect(puzzle, option, 0);
        }
    }

    private void askQuestionA1(String option, int result) {
        System.out.println("askQuestionA1 - option, result");
        Puzzle puzzle = getQuestions();
        getCorrect(puzzle, option, result + 1);
    }

    private void askQuestionA2(String option) {
        System.out.println("askQuestionA2");
        if (ChallengeRoom.getInventory().contains(ChallengeRoom.getPuzzleItemMap().get(option).get("a3"))
                && !solved.get(option).get("b")) {
            Puzzle puzzle = getQuestions();
            getCorrect(puzzle, option, 2);
        }
    }

    public void answerResponse(Puzzle puzzle, String answer, String option, int result) {

        System.out.println(answer);


        submitPuzzleButton.setEnabled(false);
        textField2.setText(null);
        if (puzzle.checkAnswer(answer)) {
            textField2.setText(answer + " was the correct answer!");
        } else {
            textField2.setText(answer + " was incorrect.");
        }
        if (puzzle.checkAnswer(answer) && result == 0 && !solved.get(option).get("a")) {
            askQuestionA1(option, result);
        } else if (puzzle.checkAnswer(answer) && result == 1) {
            solved.get(option).put("a", true);
        } else if (puzzle.checkAnswer(answer) && result == 2) {
            solved.get(option).put("b", true);
        } else {
            askQuestionA1(option);
        }
    }

    public static Duration getDuration() {
        return duration;
    }
}