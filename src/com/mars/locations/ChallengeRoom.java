package com.mars.locations;

import com.mars.client.Display;
import com.mars.client.Game;
import com.mars.client.Puzzle;
import com.mars.items.Item;

import java.util.*;

public class ChallengeRoom extends Room {
    private Game game;
    private Map<String, Map<String, Boolean>> solved;
    private List<Puzzle> puzzleList;
    Map<String, Map<String, Item>> puzzleItemMap;
    private static ChallengeRoom instance = new ChallengeRoom();

    private Scanner input = new Scanner(System.in);

    private ChallengeRoom() {
    }

    public static ChallengeRoom getInstance(Game game, String option, Map<String, Boolean> solved, List<Puzzle> puzzle) {
        instance.setGame(game);
        instance.convertToLocalSolved(solved);
        instance.setPuzzleList(puzzle);
        instance.runPuzzle(option);
        return instance;
    }

    public void setSolved(Map<String, Map<String, Boolean>> solved) {
        this.solved = solved;
    }

    public Map<String, Map<String, Boolean>> getSolved() {
        return solved;
    }

    public void setPuzzleList(List<Puzzle> puzzleList) {
        this.puzzleList = puzzleList;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setPuzzleItemMap() {
        List<Item> items = Game.getItems();
        Map<String, Map<String, Item>> required = new HashMap<>();
        for (Item item : items) {
            if (item.getName().equals("nozzle")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a1", item);
                required.put("Green House", temp);
            } else if (item.getName().equals("fertilizer")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a2", item);
                required.put("Green House", temp);
            } else if (item.getName().equals("potato")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a3", item);
                required.put("Green House", temp);
            } else if (item.getName().equals("water level probe")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a1", item);
                required.put("Hydro Control Room", temp);
            } else if (item.getName().equals("ph meter")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a2", item);
                required.put("Hydro Control Room", temp);
            } else if (item.getName().equals("chlorine tablets")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a3", item);
                required.put("Hydro Control Room", temp);
            } else if (item.getName().equals("pressure gauge")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a1", item);
                required.put("Environmental Control Room", temp);
            } else if (item.getName().equals("voltage meter")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a2", item);
                required.put("Environmental Control Room", temp);
            } else if (item.getName().equals("tool kit")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a3", item);
                required.put("Environmental Control Room", temp);
            } else if (item.getName().equals("dosimeter")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a1", item);
                required.put("Reactor", temp);
            } else if (item.getName().equals("key")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a2", item);
                required.put("Reactor", temp);
            } else if (item.getName().equals("checklist")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a3", item);
                required.put("Reactor", temp);
            } else if (item.getName().equals("batteries")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a1", item);
                required.put("Solar Array", temp);
            } else if (item.getName().equals("gps")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a2", item);
                required.put("Solar Array", temp);
            } else if (item.getName().equals("cable")) {
                Map<String, Item> temp = new HashMap<>();
                temp.put("a3", item);
                required.put("Solar Array", temp);
            }
        }
        this.puzzleItemMap = required;
    }

    public Map<String, Map<String, Item>> getPuzzleItemMap() {
        return puzzleItemMap;
    }

    public void convertToLocalSolved(Map<String, Boolean> solved) {
        Map<String, Map<String, Boolean>> newMap = new HashMap<>();
        for (Map.Entry<String, Boolean> solvd : solved.entrySet()) {
            if (solvd.getValue()) {
                Map<String, Boolean> temp = new HashMap<>();
                temp.put("a", true);
                temp.put("b", true);
                newMap.put(solvd.getKey(), temp);
            }
        }

        this.solved = newMap;
    }

    public Map<String, Boolean> convertToSuperSolved(Map<String, Map<String, Boolean>> solved) {
        Map<String, Boolean> newMap = Game.getSolved();
        for (Map.Entry<String, Map<String, Boolean>> solvd : solved.entrySet()) {
            if (solvd.getValue().get("a") && solvd.getValue().get("a")) {
                newMap.put(solvd.getKey(), true);
            }
        }
        return newMap;
    }

    public void runPuzzle(String option) {
        setPuzzleItemMap();
        Display.showTextFile(option);
        eligiblePuzzle(option);
        game.setSolved(convertToSuperSolved(solved));
    }

    private void eligiblePuzzle(String option) {
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
        if ("Solar Array" == option && !Game.getSolved().get(option)) {
            if (!solved.get(option).get("a")) {
                askQuestionA1(option);
            } else if (solved.get(option).get("a") && !solved.get(option).get("b")) {
                askQuestionA2(option);
            }
        } else if ("Reactor" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")) {
            if (!solved.get("Reactor").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Reactor").get("a")
                    && !solved.get("Reactor").get("b")) {
                askQuestionA2(option);
            }
        } else if ("Environmental Control Room" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")) {
            if (!solved.get("Environmental Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Environmental Control Room").get("a")
                    && !solved.get("Environmental Control Room").get("b")) {
                askQuestionA2(option);
            }
        } else if ("Hydro Control Room" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")) {
            if (!solved.get("Hydro Control Room").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Hydro Control Room").get("a")
                    && !solved.get("Hydro Control Room").get("b")) {
                askQuestionA2(option);
            }
        } else if ("Green House" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && Game.getSolved().get("Hydro Control Room")) {
            if (!solved.get("Green House").get("a")) {
                askQuestionA1(option);
            } else if (solved.get("Green House").get("a")
                    && !solved.get("Green House").get("b")) {
                askQuestionA2(option);
            }
        }
    }

    private void askQuestionA1(String option) {
        List<Item> inventory = game.getPlayer().getInventory().getInventory();
        if (inventory.contains(getPuzzleItemMap().get(option).get("a1"))
                && inventory.contains(getPuzzleItemMap().get(option).get("a2"))
                && !getSolved().get(option).get("a")) {
            Display.displayText(getPuzzleQuestion(option + "-a"));
            Display.displayText("[y/n]?");
            int result = 0;
            result += getQuestions();
            result += getQuestions();
            if (result == 2) {
                Map<String, Map<String, Boolean>> tempSolved = getSolved();
                Map<String, Boolean> temp = new HashMap<>();
                tempSolved.put(option, temp);
                setSolved(tempSolved);
                Display.displayText("you solved this part");
            } else {
                Display.displayText("you did not solve this part");
            }
        }
    }

    private void askQuestionA2(String option) {
        List<Item> inventory = game.getPlayer().getInventory().getInventory();
        if (inventory.contains(getPuzzleItemMap().get(option).get("a3"))
                && !getSolved().get(option).get("b")) {
            String output5 = getPuzzleQuestion(option + "-b");
            Display.displayText("[y/n]?");
            int result = 0;
            result += getQuestions();
            if (result == 1) {
                Map<String, Map<String, Boolean>> tempSolved = getSolved();
                Map<String, Boolean> temp = new HashMap<>();
                tempSolved.put(option, temp);
                setSolved(tempSolved);
                String output6 = "you solved the second part";
            } else {
                String output7 = "you did not solve this one either";
            }
        }
    }

    private String getPuzzleQuestion(String option) {
        String question = "";
        switch (option) {
            case "Solar Array-a":
                question = "Would you like to attempt to bring the solar array online?";
                break;
            case "Solar Array-b":
                question = "Would you like to closely inspect the solar array?";
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

    private int getQuestions() {
        String input1 = input.nextLine();
        if (input1.equalsIgnoreCase("y")) {
            boolean correct = false;
            correct = getPuzzle(puzzleList);
            return (correct) ? 1 : 0;
        } else {
            String output2 = "OK, then...";
            return 0;
        }
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