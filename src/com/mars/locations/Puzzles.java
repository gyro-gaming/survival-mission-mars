package com.mars.locations;

import com.mars.client.Game;
import com.mars.items.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Puzzles {
    private static Scanner input = new Scanner(System.in);
    private static List<Puzzle> puzzleList = Puzzle.getPuzzleList();

    // TODO need method logic
    public static Map<String, Boolean> greenHousePuzzle(Game game, String option, Map<String, Boolean> solved) {
        return new HashMap<>();
    }

    // TODO need method logic
    public static Map<String, Boolean> hydroPuzzle(Game game, String option, Map<String, Boolean> solved) {
        return new HashMap<>();
    }

    // TODO need method logic
    public static Map<String, Boolean> environmentalPuzzle(Game game, String option, Map<String, Boolean> solved) {
        return new HashMap<>();
    }

    // TODO need method logic
    public static Map<String, Boolean> reactorPuzzle(Game game, String option, Map<String, Boolean> solved) {
        return new HashMap<>();
    }

    public static Map<String, Boolean> solarPuzzle(Game game, String option, Map<String, Boolean> solved) {
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
            String input1 = input.nextLine();
            if (input1.equalsIgnoreCase("y")) {
                boolean correct = false;
                correct = getPuzzle(puzzleList);
                correct = getPuzzle(puzzleList);
                solved.put(option + "-a", correct);
            } else {
                String output2 = "OK, then...";
                solved.put(option + "-a", false);
            }
        }
        if (game.getPlayer().getInventory().getInventory().contains(cable) &&
                !solved.get(option + "-b")) {
            String output3 = "You notice that the array is not generating any power, would you like to take a look?";
            String input3 = input.nextLine();
            if (option.equalsIgnoreCase("y")) {
                boolean correct = false;
                correct = getPuzzle(puzzleList);
                correct = getPuzzle(puzzleList);
                solved.put(option + "-b", correct);
            } else {
                String output2 = "OK, then...";
                solved.put(option + "-b", false);
            }
        }
        return solved;
    }

    private static int getRandomPuzzle(int num) {
        return (int) (Math.random() * num);
    }

    private static boolean getPuzzle(List<Puzzle> puzzleList) {
        int index = getRandomPuzzle(puzzleList.size());
        puzzleList.get(index).askQuestion();
        String option = input.nextLine();
        boolean correct;
        correct = puzzleList.get(index).checkAnswer(option);
        if (correct) {
            return true;
        }
        puzzleList.remove(index);
        return false;
    }
}
