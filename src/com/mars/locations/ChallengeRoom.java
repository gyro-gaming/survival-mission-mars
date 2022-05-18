package com.mars.locations;

import com.mars.client.Display;
import com.mars.client.Game;
import com.mars.client.Puzzle;
import com.mars.items.Item;

import java.util.*;

public class ChallengeRoom extends Room {
    private static Game game;
    private static Map<String, Map<String, Boolean>> solved;
    private static List<Puzzle> puzzleList;
    private static Map<String, Map<String, Item>> puzzleItemMap;
    private static ChallengeRoom instance = new ChallengeRoom();

    private static Scanner input = new Scanner(System.in);

    private ChallengeRoom() {
    }

    public static ChallengeRoom getInstance(Game game, Map<String, Boolean> solved, List<Puzzle> puzzle) {
        instance.setGame(game);
        instance.convertToLocalSolved(solved);
        instance.setPuzzleList(puzzle);
        return instance;
    }

    public static void setSolved(Map<String, Map<String, Boolean>> solved) {
        ChallengeRoom.solved = solved;
    }

    public static Map<String, Map<String, Boolean>> getSolved() {
        return solved;
    }

    public void setPuzzleList(List<Puzzle> puzzleList) {
        this.puzzleList = puzzleList;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static void setPuzzleItemMap() {
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
        ChallengeRoom.puzzleItemMap = required;
    }

    public static Map<String, Map<String, Item>> getPuzzleItemMap() {
        return puzzleItemMap;
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
        if ("Solar Array" == option && !Game.getSolved().get(option)) {
            if (!solved.get(option).get("a")) {
                System.out.println("Trying to solve problem");
                sb.append(askQuestionA1(option));
            } else if (solved.get(option).get("a") && !solved.get(option).get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if ("Reactor" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")) {
            if (!solved.get("Reactor").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Reactor").get("a")
                    && !solved.get("Reactor").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if ("Environmental Control Room" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")) {
            if (!solved.get("Environmental Control Room").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Environmental Control Room").get("a")
                    && !solved.get("Environmental Control Room").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if ("Hydro Control Room" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")) {
            if (!solved.get("Hydro Control Room").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Hydro Control Room").get("a")
                    && !solved.get("Hydro Control Room").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if ("Green House" == option && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && Game.getSolved().get("Hydro Control Room")) {
            if (!solved.get("Green House").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Green House").get("a")
                    && !solved.get("Green House").get("b")) {
                sb.append(askQuestionA2(option));
            }
        }
        return sb.toString();
    }

    private static String askQuestionA1(String option) {
        StringBuilder sb = new StringBuilder();
        List<Item> inventory = game.getPlayer().getInventory().getInventory();
        if (inventory.contains(getPuzzleItemMap().get(option).get("a1"))
                && inventory.contains(getPuzzleItemMap().get(option).get("a2"))
                && !getSolved().get(option).get("a")) {
            sb.append(Display.displayText(getPuzzleQuestion(option + "-a")));
            sb.append(Display.displayText("[y/n]?"));
            int result = 0;
            result += getQuestions();
            result += getQuestions();
            if (result == 2) {
                Map<String, Map<String, Boolean>> tempSolved = getSolved();
                Map<String, Boolean> temp = new HashMap<>();
                tempSolved.put(option, temp);
                setSolved(tempSolved);
                sb.append(Display.displayText("you solved this part"));
            } else {
                sb.append(Display.displayText("you did not solve this part"));
            }
        }
        return sb.toString();
    }

    private static String askQuestionA2(String option) {
        StringBuilder sb = new StringBuilder();
        List<Item> inventory = game.getPlayer().getInventory().getInventory();
        if (inventory.contains(getPuzzleItemMap().get(option).get("a3"))
                && !getSolved().get(option).get("b")) {
            sb.append(getPuzzleQuestion(option + "-b"));
            sb.append(Display.displayText("[y/n]?"));
            int result = 0;
            result += getQuestions();
            if (result == 1) {
                Map<String, Map<String, Boolean>> tempSolved = getSolved();
                Map<String, Boolean> temp = new HashMap<>();
                tempSolved.put(option, temp);
                setSolved(tempSolved);
                sb.append("you solved the second part");
            } else {
                sb.append("you did not solve this one either");
            }
        }
        return sb.toString();
    }

    private static String getPuzzleQuestion(String option) {
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

    private static int getQuestions() {
        // String input1 = input.nextLine();
        // if (input1.equalsIgnoreCase("y")) {
            boolean correct = false;
            correct = getPuzzle(puzzleList);
            return (correct) ? 1 : 0;
        //} else {
            //String output2 = "OK, then...";
            //return 0;
        //}
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
        game.setPuzzles(puzzleList);
        return false;
    }
}