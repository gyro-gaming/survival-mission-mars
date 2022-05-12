package com.mars.locations;

import com.mars.client.Game;
import com.mars.client.Puzzle;
import com.mars.items.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ChallengeRoom extends Room {
    private Game game;
    private Map<String, Boolean> solved;
    private List<Puzzle> puzzleList;
    private static ChallengeRoom instance = new ChallengeRoom();

    private Scanner input = new Scanner(System.in);

    private ChallengeRoom() {}

    public static ChallengeRoom getInstance(Game game, String option, Map<String, Boolean> solved, List<Puzzle> puzzle) {
        instance.setGame(game);
        instance.setSolved(solved);
        instance.setPuzzleList(puzzle);
        instance.runPuzzle(game, option, solved);
        return instance;
    }

    public void showIntro(String path) {
        // Display.displayText(path);
    }

    public void runPuzzle(Game game, String option, Map<String, Boolean> solved) {
        System.out.println(option);
        switch (option) {
            case "Solar Array":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(solarPuzzle(game, option, solved));
                break;
            case "Reactor":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(reactorPuzzle(game, option, solved));
                break;
            case "Environmental Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(environmentalPuzzle(game, option, solved));
                break;
            case "Green House":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(greenHousePuzzle(game, option, solved));
                break;
            case "Hydro Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(hydroPuzzle(game, option, solved));
                break;
            default:
                // System.out.println("Something went wrong!\nThis room does not have a challenge.");
                return;
        }
        game.setSolved(solved);
    }

    public void setSolved(Map<String, Boolean> solved) {
        this.solved = solved;
    }

    public void setPuzzleList(List<Puzzle> puzzleList) {
        this.puzzleList = puzzleList;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    // TODO need method logic
    public Map<String, Boolean> greenHousePuzzle(Game game, String option, Map<String, Boolean> solved) {
        System.out.println("Green House Puzzle");
        return new HashMap<>();
    }

    // TODO need method logic
    public Map<String, Boolean> hydroPuzzle(Game game, String option, Map<String, Boolean> solved) {
        System.out.println("Hydro Puzzle");
        return new HashMap<>();
    }

    // TODO need method logic
    public Map<String, Boolean> environmentalPuzzle(Game game, String option, Map<String, Boolean> solved) {
        System.out.println("Environmental puzzle");
        return new HashMap<>();
    }


    public Map<String, Boolean> reactorPuzzle(Game game, String option, Map<String, Boolean> solved) {
        List<Item> items = Game.getItems();
        Item dosimeter = null;
        Item key = null;
        Item checklist = null;
        for (Item item : items) {
            if (item.getName().equals("dosimeter")) {
                dosimeter = item;
            } else if (item.getName().equals("key")) {
                key = item;
            } else if (item.getName().equals("checklist")) {
                checklist = item;
            }
        }
        if (game.getPlayer().getInventory().getInventory().contains(key) &&
                game.getPlayer().getInventory().getInventory().contains(dosimeter) &&
                !solved.get(option + "-a")) {
            String output1 = "Would you like to attempt to align the panels [y/n]? ";
            solved = getQuestions(option + "a", solved);
        }
        if (game.getPlayer().getInventory().getInventory().contains(checklist) &&
                !solved.get(option + "-b")) {
            String output3 = "You notice that the array is not generating any power, would you like to take a look?";
            solved = getQuestions(option + "b", solved);
        }
        return solved;
    }

    public Map<String, Boolean> solarPuzzle(Game game, String option, Map<String, Boolean> solved) {
        List<Item> items = Game.getItems();
        Item batteries = null;
        Item gps = null;
        Item cable = null;
        for (Item item : items) {
            if (item.getName().equals("batteries")) {
                batteries = item;
            } else if (item.getName().equals("gps")) {
                gps = item;
            } else if (item.getName().equals("cable")) {
                cable = item;
            }
        }

            if (game.getPlayer().getInventory().getInventory().contains(batteries) &&
                    game.getPlayer().getInventory().getInventory().contains(gps) &&
                    !solved.get(option + "-a")) {
                String output1 = "Would you like to attempt to align the panels [y/n]? ";
                solved = getQuestions(option + "a", solved);
            } else {
                String output2 = "You are missing an item!";
            }
            if (game.getPlayer().getInventory().getInventory().contains(cable) &&
                    !solved.get(option + "-b")) {
                String output3 = "You notice that the array is not generating any power, would you like to take a look?";
                solved = getQuestions(option + "b", solved);
            }
        try {
            if (solved.get(option + "a") && solved.get(option + "b")) {
                // mission control npc contacts player with status update and mission details
            }
        } catch (NullPointerException e) {
            System.out.println("You are missing items!");
        }
        return solved;
    }

    private Map<String, Boolean> getQuestions(String option, Map<String, Boolean> solved) {
        String input1 = input.nextLine();
        if (input1.equalsIgnoreCase("y")) {
            boolean correct = false;
            correct = getPuzzle(puzzleList);
            correct = getPuzzle(puzzleList);
            solved.put(option, correct);
        } else {
            String output2 = "OK, then...";
            solved.put(option, false);
        }
        return solved;
    }

    private int getRandomPuzzle(int num) {
        return (int) (Math.random() * num);
    }

    private boolean getPuzzle(List<Puzzle> puzzleList) {
        int index = getRandomPuzzle(puzzleList.size());
        puzzleList.get(index).askQuestion();
        String option = input.nextLine();
        boolean correct;
        correct = puzzleList.get(index).checkAnswer(option);
        if (correct) {
            return true;
        }
        puzzleList.remove(index);
        game.setPuzzles(puzzleList);
        return false;
    }
}