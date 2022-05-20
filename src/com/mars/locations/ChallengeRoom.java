package com.mars.locations;

import com.mars.client.Display;
import com.mars.client.Game;
import com.mars.client.Puzzle;
import com.mars.gui.PlayScreen;
import com.mars.items.Item;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;

public class ChallengeRoom extends Room {
    private static Game game;
    private static Map<String, Map<String, Boolean>> solved;
    private static List<Puzzle> puzzleList;
    private static Map<String, Map<String, Item>> puzzleItemMap;
    private static List<Item> inventory;
    private static ChallengeRoom instance = new ChallengeRoom();

    private ChallengeRoom() {
    }

    public static ChallengeRoom getInstance(Game game, Map<String, Boolean> solved, List<Puzzle> puzzles, List<Item> inventory) {
        instance.setGame(game);
        instance.convertToLocalSolved(solved);
        instance.setPuzzleList(puzzles);
        instance.setInventory(inventory);
        return instance;
    }

    public static void setSolved(Map<String, Map<String, Boolean>> solved) {
        ChallengeRoom.solved = solved;
    }

    public static Map<String, Map<String, Boolean>> getSolved() {
        return solved;
    }

    public static void setPuzzleList(List<Puzzle> puzzleList) {
        ChallengeRoom.puzzleList = puzzleList;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static Game getGame() {
        return game;
    }

    public static void setPuzzleItemMap() {
        List<Item> items = Game.getItems();
        Map<String, Item> temp = new HashMap<>();
        Map<String, Map<String, Item>> required = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("nozzle")) {
                temp.put("a1", item);
            } else if (item.getName().equals("fertilizer")) {
                temp.put("a2", item);
            } else if (item.getName().equals("potato")) {
                temp.put("a3", item);
            }
        }
        required.put("Green House", temp);
        temp = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("water level probe")) {
                temp.put("a1", item);
            } else if (item.getName().equals("ph meter")) {
                temp.put("a2", item);
            } else if (item.getName().equals("chlorine tablets")) {
                temp.put("a3", item);
            }
        }
        required.put("Hydro Control Room", temp);
        temp = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("pressure gauge")) {
                temp.put("a1", item);
            } else if (item.getName().equals("voltage meter")) {
                temp.put("a2", item);
            } else if (item.getName().equals("tool kit")) {
                temp.put("a3", item);
            }
        }
        required.put("Environmental Control Room", temp);
        temp = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("dosimeter")) {
                temp.put("a1", item);
            } else if (item.getName().equals("key")) {
                temp.put("a2", item);
            } else if (item.getName().equals("checklist")) {
                temp.put("a3", item);
            }
        }
        required.put("Reactor", temp);
        temp = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("batteries")) {
                temp.put("a1", item);
            } else if (item.getName().equals("gps")) {
                temp.put("a2", item);
            } else if (item.getName().equals("cable")) {
                temp.put("a3", item);
            }
        }
        required.put("Solar Array", temp);
        ChallengeRoom.puzzleItemMap = required;
    }

    public static Map<String, Map<String, Item>> getPuzzleItemMap() {
        return puzzleItemMap;
    }

    public static void setInventory(List<Item> inventory) {
        ChallengeRoom.inventory = inventory;
    }

    public static List<Item> getInventory() {
        return inventory;
    }

    public static void convertToLocalSolved(Map<String, Boolean> solved) {
        Map<String, Map<String, Boolean>> newMap = new HashMap<>();
        for (Map.Entry<String, Boolean> solvd : solved.entrySet()) {
            if (solvd.getValue()) {
                Map<String, Boolean> temp = new HashMap<>();
                temp.put("a", true);
                temp.put("b", true);
                newMap.put(solvd.getKey(), temp);
            }
        }
        ChallengeRoom.solved = newMap;
    }

    public static Map<String, Boolean> convertToSuperSolved(Map<String, Map<String, Boolean>> solved) {
        Map<String, Boolean> newMap = Game.getSolved();
        for (Map.Entry<String, Map<String, Boolean>> solvd : solved.entrySet()) {
            if (solvd.getValue().get("a") && solvd.getValue().get("a")) {
                newMap.put(solvd.getKey(), true);
            }
        }
        return newMap;
    }
/*
    public static String runPuzzle(String option) {
        StringBuilder sb = new StringBuilder();
        setPuzzleItemMap();
        sb.append(Display.showTextFile(option));
        sb.append(eligiblePuzzle(option));
        game.setSolved(convertToSuperSolved(solved));
        return sb.toString();
    }

    private static String eligiblePuzzle(String option) {
        StringBuilder sb = new StringBuilder();
        convertToLocalSolved(Game.getSolved());
        if (!solved.containsKey(option)) {
            Map<String, Boolean> temp = new HashMap<>();
            temp.put("a", false);
            temp.put("b", false);
            solved.put(option, temp);
            Map<String, Boolean> temp2 = new HashMap<>();
            temp2.put(option, false);
            game.setSolved(temp2);
        }
        if (!Game.getSolved().get(option) && "Solar Array".equals(option)) {
            if (!solved.get(option).get("a")) {
                askQuestionA1(option);
            } else if (solved.get(option).get("a") && !solved.get(option).get("b")) {
                askQuestionA2(option);
            }
        } else if ("Reactor".equals(option) && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")) {
            if (!solved.get("Reactor").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Reactor").get("a")
                    && !solved.get("Reactor").get("b")) {
               askQuestionA2(option);
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && "Environmental Control Room".equals(option)) {
            if (!solved.get("Environmental Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Environmental Control Room").get("a")
                    && !solved.get("Environmental Control Room").get("b")) {
                askQuestionA2(option);
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && "Hydro Control Room".equals(option)) {
            if (!solved.get("Hydro Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Hydro Control Room").get("a")
                    && !solved.get("Hydro Control Room").get("b")) {
                askQuestionA2(option);
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && Game.getSolved().get("Hydro Control Room")
                && "Green House".equals(option)) {
            if (!solved.get("Green House").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Green House").get("a")
                    && !solved.get("Green House").get("b")) {
                askQuestionA2(option);
            }
        }
        return sb.toString();
    }

    private static void askQuestionA1(String option) {
        PlayScreen playScreen = null;
        try {
            playScreen = new PlayScreen();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getInventory().contains(getPuzzleItemMap().get(option).get("a1"))
                && inventory.contains(getPuzzleItemMap().get(option).get("a2"))
                && !getSolved().get(option).get("a")) {
            Puzzle puzzle = playScreen.getQuestions();
            playScreen.getCorrect(puzzle, option, 0);
        }
    }

    private static void askQuestionA1(String option, int result) {
        PlayScreen playScreen = null;
        try {
            playScreen = new PlayScreen();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Puzzle puzzle = playScreen.getQuestions();
        playScreen.getCorrect(puzzle, option, result + 1);
    }

    private static void askQuestionA2(String option) {
        PlayScreen playScreen = null;
        try {
            playScreen = new PlayScreen();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getInventory().contains(getPuzzleItemMap().get(option).get("a3"))
                && !getSolved().get(option).get("b")) {
            Puzzle puzzle = playScreen.getQuestions();
            playScreen.getCorrect(puzzle, option, 2);
        }
    }

    public static void answerResponse(Puzzle puzzle, String answer, String option, int result) {
        System.out.println(answer);
        if (puzzle.checkAnswer(answer) && result == 0) {
            askQuestionA1(option, result);
        } else if (puzzle.checkAnswer(answer) && result == 1) {
            Map<String, Map<String, Boolean>> tempSolved = getSolved();
            Map<String, Boolean> temp = new HashMap<>();
            temp.put("a", true);
            tempSolved.put(option, temp);
            setSolved(tempSolved);
        } else if (puzzle.checkAnswer(answer) && result == 2) {
            Map<String, Map<String, Boolean>> tempSolved = getSolved();
            Map<String, Boolean> temp = tempSolved.get(option);
            temp.put("b", true);
            tempSolved.put(option, temp);
            setSolved(tempSolved);
        }
        System.out.println(getSolved());
    }
    */
}