package com.mars.locations;

import com.mars.client.Game;
import com.mars.client.Puzzle;
import com.mars.items.Item;

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
}