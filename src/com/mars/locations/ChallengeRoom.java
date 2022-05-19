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
        if (!Game.getSolved().get(option) && "Solar Array".equals(option)) {
            if (!solved.get(option).get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get(option).get("a") && !solved.get(option).get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if ("Reactor".equals(option) && !Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")) {
            if (!solved.get("Reactor").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Reactor").get("a")
                    && !solved.get("Reactor").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && "Environmental Control Room".equals(option)) {
            if (!solved.get("Environmental Control Room").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Environmental Control Room").get("a")
                    && !solved.get("Environmental Control Room").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && "Hydro Control Room".equals(option)) {
            if (!solved.get("Hydro Control Room").get("a")) {
                sb.append(askQuestionA1(option));
            } else if (solved.get("Hydro Control Room").get("a")
                    && !solved.get("Hydro Control Room").get("b")) {
                sb.append(askQuestionA2(option));
            }
        } else if (!Game.getSolved().get(option)
                && Game.getSolved().get("Solar Array")
                && Game.getSolved().get("Reactor")
                && Game.getSolved().get("Environmental Control Room")
                && Game.getSolved().get("Hydro Control Room")
                && "Green House".equals(option)) {
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
            PlayScreen.getPuzzleQuestion(option + "-a");
            int result = 0;
            if (PlayScreen.checkPuzzleQuestion()) {
                try {
                    result += PlayScreen.getQuestions();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    result += PlayScreen.getQuestions();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                sb.append("you chose not to fix the problem.");
            }
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
            PlayScreen.getPuzzleQuestion(option + "-b");
            int result = 0;
            if (PlayScreen.checkPuzzleQuestion()) {
                try {
                    result += PlayScreen.getQuestions();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        return sb.toString();
    }
}