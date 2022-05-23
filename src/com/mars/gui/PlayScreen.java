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

public class PlayScreen extends JFrame implements ActionListener, ChangeListener,
        ItemListener, MouseListener, Runnable {
    private JPanel mainPanel, middleRowPanel, bottomRowPanel, outerWrapperPanel,
            mapAndInventoryPanel, healthLevelsPanel, descriptionsPanel,
            roomAndClockPanel, clockPanel, roomPanel, directionPanel, utilitiesPanel,
            goNorthPanel, goSouthPanel, goWestPanel, goEastPanel, mapPanel,
            inventoryPanel, topRowPanel, imagePanel, puzzlePanel, menuControlPanel;
    private JButton northButton, westButton, eastButton, southButton, puzzle1Submit,
            puzzle2Submit, puzzle3Submit;
    private JComboBox itemsBox, menuDropDownBox, puzzleChoice1, puzzleChoice2,
            puzzleChoice3;
    private JProgressBar progressO2Bar, progressHungerBar, progressStaminaBar;
    private JLabel o2LevelLabel, healthLabel, staminaLabel, volumeLabel, menuLabel,
            muteLabel, roomLabel, mapLabel, l, countDown, imageLabel;
    private JRadioButton radioButtonLook, radioButtonGo, radioButtonInspect,
            radioButtonGet, radioButtonMute, dropButton, useButton;
    private JSlider volumeSlider;
    private JList inventoryList;
    private JTextArea puzzleText1, puzzleText2, puzzleText3, textField2;
    private JScrollPane textScrollPane;
    private JTabbedPane puzzleQuestions;

    private Vector<String> items;
    private Font normalFont;
    private CommandProcessor processor;
    private DefaultListModel demoList, answerList;
    private Clip clip;
    private Instant futureTime;
    private Timer timer;
    private Map<String, Map<String, Boolean>> solved;
    private ChallengeRoom challengeRoom;
    private static Duration duration;
    private PlayScreen _this;


    public PlayScreen() {
        _this = this;
        EventQueue.invokeLater(this);

        textField2.setText(Display.showTextFile("Intro"));
        textField2.setBackground(Color.white);
        try {
            clip = Audio.playAudio();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // this is the timer
        showTimer();
    }


    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
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

        progressO2Bar.setValue(Game.getStats().get("Oxygen"));
        progressStaminaBar.setValue(Game.getStats().get("Stamina"));
        progressHungerBar.setValue(Game.getStats().get("Hunger"));
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
                    String formatted = String.format("%02d:%02d:%02d", duration.toHours(),
                            duration.toMinutesPart(), duration.toSecondsPart());
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
        if (Game.getStats().get("Oxygen") < 100) {
            progressO2Bar.setValue(Game.getStats().get("Oxygen"));
            repaint();
        }
        if (Game.getStats().get("Stamina") < 100) {
            progressStaminaBar.setValue(Game.getStats().get("Stamina"));
            repaint();
        }
        if (Game.getStats().get("Hunger") < 100) {
        progressHungerBar.setValue(Game.getStats().get("Hunger"));
            repaint();
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

        PlayScreen p = new PlayScreen();
        p.processor.setCurrentLocation(player.getLocation());
        p.roomLabel.setText(processor.getCurrentLocation().getName());
        for (Item name : player.getInventory().getInventory()) {
            demoList.addElement(name.getName());
        }
        p.inventoryList.setModel(demoList);
        p.inventoryList.repaint();
        p.progressO2Bar.repaint();
        p.progressStaminaBar.repaint();
        p.progressHungerBar.repaint();
        p.inventoryList.revalidate();
        p.showMap(processor.getCurrentLocation().getName());
        p.showRoomImage(processor.getCurrentLocation().getName());
        p.textField2.setText("This is a past game that belongs to:  " + player.getName() + " user");
        String formatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(),
                duration.toSecondsPart());
        p.countDown.setText(remainTime + formatted);
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
        puzzleText1.setText(null);
        puzzleChoice1.removeAllItems();
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
                    this.challengeRoom = processor.getChallengeRoom();
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

    public void runPuzzle(String option) {
        challengeRoom = ChallengeRoom.getInstance(ChallengeRoom.getGame(), Game.getSolved(), Game.getPuzzles(),
                Game.getInventory());
        challengeRoom.setPuzzleItemMap();
        try {
            boolean eligible = eligiblePuzzle(option);
            if (eligible) {
                textField2.setText(null);
                textField2.setText(Display.showTextFile(option));
            }
        } catch (NullPointerException e) {
        }
    }

    private void postQuestion (Puzzle puzzle, String option, int round) {
        puzzle1Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!puzzleChoice1.getSelectedItem().equals("")) {
                    answerResponse(puzzle, puzzleChoice1.getSelectedItem().toString(), option, round);
                }
            }
        });
        puzzle2Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!puzzleChoice2.getSelectedItem().equals("")) {
                    answerResponse(puzzle, puzzleChoice2.getSelectedItem().toString(), option, round);
                }
            }
        });
        puzzle3Submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!puzzleChoice3.getSelectedItem().equals("")) {
                        answerResponse(puzzle, puzzleChoice3.getSelectedItem().toString(), option, round);
                    }
                } catch (NullPointerException ex) {}
            }
        });
    }

    private boolean eligiblePuzzle(String option) {
        if (solved == null) {
            solved = new HashMap<>();
        }
        if (!solved.containsKey(option)) {
            Map<String, Boolean> temp = new HashMap<>();
            temp.put("a", false);
            temp.put("b", false);
            temp.put("c", false);
            solved.put(option, temp);
            challengeRoom.setSolved(solved);

            if (Game.getSolved() == null) {
                Game.setSolved(new HashMap<>());
            }
            Map<String, Boolean> gameSolved = Game.getSolved();
            gameSolved.put(option, false);
            Game.setSolved(gameSolved);
        }

        solved = challengeRoom.getSolved();
        Map<String, Boolean> gameSolved = Game.getSolved();

        if (!gameSolved.get(option) && "Solar Array".equals(option)) {
            getQuestionBank(option);
            return true;
        } else if ("Reactor".equals(option) && !gameSolved.get(option)
                && gameSolved.get("Solar Array")) {
            getQuestionBank(option);
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && "Environmental Control Room".equals(option)) {
            getQuestionBank(option);
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && gameSolved.get("Environmental Control Room")
                && "Hydro Control Room".equals(option)) {
            getQuestionBank(option);
            return true;
        } else if (!gameSolved.get(option)
                && gameSolved.get("Solar Array")
                && gameSolved.get("Reactor")
                && gameSolved.get("Environmental Control Room")
                && gameSolved.get("Hydro Control Room")
                && "Green House".equals(option)) {
            getQuestionBank(option);
            return true;
        }
        return false;
    }

    public void getQuestionBank(String option) {
        if (challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && !solved.get(option).get("a")) {
            Puzzle puzzle = getQuestions(1);
            postQuestion(puzzle, option, 1);
        } else if (challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && solved.get(option).get("a") && !solved.get(option).get("b")) {
            Puzzle puzzle = getQuestions(2);
            postQuestion(puzzle, option, 2);
        } else if (challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && challengeRoom.getInventory().contains(challengeRoom.getPuzzleItemMap().get(option).get("a1"))
                && solved.get(option).get("a") && solved.get(option).get("b") && !solved.get(option).get("c")) {
            Puzzle puzzle = getQuestions(3);
            postQuestion(puzzle, option, 3);
        }
    }

    public Puzzle getQuestions(int round) {
        Puzzle puzzle = getPuzzle(Game.getPuzzles());
        askQuestion(puzzle, round);
        getAnswers(puzzle, round);
        return puzzle;
    }

    private int getRandomPuzzle(int num) {
        return (int) (Math.random() * num);
    }

    public Puzzle getPuzzle(List<Puzzle> puzzleList) {
        int index = getRandomPuzzle(puzzleList.size());
        Puzzle puzzle = puzzleList.get(index);
        puzzleList.remove(index);
        Game.setPuzzles(puzzleList);
        return puzzle;
    }

    public void askQuestion(Puzzle puzzle, int round) {
        puzzleText1.setText(null);
        puzzleText2.setText(null);
        puzzleText3.setText(null);
        switch (round) {
            case 1:
                puzzleText1.setText(puzzle.askQuestion());
                break;
            case 2:
                puzzleText2.setText(puzzle.askQuestion());
                break;
            case 3:
                puzzleText3.setText(puzzle.askQuestion());
                break;
            default:
                break;
        }
    }

    public void getAnswers(Puzzle puzzle, int round) {
        puzzleChoice1.removeAllItems();
        puzzleChoice2.removeAllItems();
        puzzleChoice2.removeAllItems();
        items = puzzle.getAnswers();
        Collections.shuffle(items);
        for (String item : items) {
            switch (round) {
                case 1:
                    puzzleChoice1.addItem(item);
                    break;
                case 2:
                    puzzleChoice2.addItem(item);
                    break;
                case 3:
                    puzzleChoice3.addItem(item);
                    break;
                default:
                    break;
            }
        }
    }

    public static String getPuzzlePreQuestions(String option) {
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

    public void answerResponse(Puzzle puzzle, String answer, String option, int round) {
        if (puzzle.checkAnswer(answer) && round == 1) {
            solved.get(option).put("a", true);
            textField2.setText(null);
            textField2.setText(answer + " is correct!" );
            puzzleText1.setText(null);
            puzzleChoice1.removeAllItems();
            getQuestionBank(option);
        } else if (puzzle.checkAnswer(answer) && round == 2) {
            solved.get(option).put("b", true);
            textField2.setText(null);
            textField2.setText(answer + " is correct!" );
            puzzleText2.setText(null);
            puzzleChoice2.removeAllItems();
            getQuestionBank(option);
        } if (puzzle.checkAnswer(answer) && round == 3) {
            solved.get(option).put("c", true);
            textField2.setText(null);
            textField2.setText(answer + " is correct!" );
            puzzleText3.setText(null);
            puzzleChoice3.removeAllItems();
        }
        ChallengeRoom.setSolved(solved);
        Game.setSolved(ChallengeRoom.convertToSuperSolved(solved));
        if (Game.getSolved().get(option)) {
            textField2.setText(null);
            textField2.setText("You solved the " + option + " challenge, great job!" );
        } else {
            boolean win = false;
            for (Map.Entry<String, Boolean> map : Game.getSolved().entrySet()) {
                win = map.getValue();
            }
            if (win) {
                textField2.setText(Display.showTextFile("Win"));
            }
        }
        System.out.println(solved);
        System.out.println(Game.getSolved());
    }

    public static Duration getDuration() {
        return duration;
    }
}