package com.mars.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mars.gui.PlayScreen;
import com.mars.items.*;
import com.mars.players.Inventory;
import com.mars.players.NPC;
import com.mars.locations.Room;
import com.mars.players.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static List<Item> items;
    private static List<Room> rooms;
    private static List<Puzzle> puzzles;
    private static Map<String, Boolean> solved;
    private static Map<String, Integer> stats;
    private static List<Item> inventory;
    private static Game instance = new Game();
    private static Player player;

    private Game() {}

    public static Game getInstance() {
        player = Player.getInstance();
        player.setName("Default");
        Puzzle.getInstance();
        instance.setPlayer(player);
        instance.setRooms();
        instance.setPuzzles(Puzzle.getPuzzleList());
        instance.setItems();
        instance.setSolved(new HashMap<>());
        instance.setStats(player.getStats().getStats());
        instance.setInventory(player.getInventory().getInventory());
        return instance;
    }
    // getters and setters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public static Player getPlayer() {
        return player;
    }

    public void setRooms() {
        this.rooms = getLocationList();
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    public void setItems() {
        this.items = getThingsList();
    }

    public static List<Item> getItems() {
        return items;
    }

    public static void setPuzzles(List<Puzzle> puzzles) {
        Game.puzzles = puzzles;
    }

    public static List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public static void setSolved(Map<String, Boolean> solved) {
        Game.solved = solved;
    }

    public static Map<String, Boolean> getSolved() {
        return solved;
    }

    public static void setStats(Map<String, Integer> stats) {
        Game.stats = stats;
    }

    public static Map<String, Integer> getStats() {
        return stats;
    }

    public static void setInventory(List<Item> inventory) {
        Game.inventory = inventory;
    }

    public static List<Item> getInventory() {
        return inventory;
    }

    // end getters and setters

    public static void save() {

        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            // convert map to JSON file
            writer.writeValue(new File("data/savedGames/savedGame.json"),instance);

        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Your game was not saved!!");
        }

    }

    public static Player retrieveSave() {
        Room newRoom = null;
        int counter = 0;
        Map<String,Object> savedMap = JsonParser.parseJson("data/savedGames/savedGame.json");
        Map<String,Object> playerMap = (Map<String, Object>) savedMap.get("player");
        Map<String,Object> locMap = (Map<String, Object>) playerMap.get("location");
        Map<String,Object> inventoryMap = (Map<String, Object>) playerMap.get("inventory");
        ArrayList inventoryItem = (ArrayList) inventoryMap.get("inventory");
        Map<String,Object> inv;
        for (Item i : Game.getItems()) {
            for (counter = 0; counter < inventoryItem.size();counter++) {
                inv = (Map<String, Object>) inventoryItem.get(counter);
                if (i.getName().equals(inv.get("name"))) {
                    player.getInventory().add(i);
                    inv = (Map<String, Object>) inventoryItem.get(counter);
                }
            }
        }
        player.setName(playerMap.get("name").toString());
        for (Room r : Game.getRooms()){
            if(r.getName().equals(locMap.get("name"))){
                newRoom = r;
            }
        }
        player.setLocation(newRoom);
        player.setDuration(player.getDuration());
        return player;
    }

    public static void quit() {
        System.exit(0);
    }

    private List<Room> getLocationList() {
        Map<String, Object> jsonMap = JsonParser.parseJson("data/json/rooms.json");
        List<Map<String, Object>> locations = (List) jsonMap.get("locations");
        return getRoomsList(locations);
    }

    public List<Item> getThingsList() {
        Map<String, Object> jsonMap = JsonParser.parseJson("data/json/items.json");
        List<Map<String, Object>> items = (List) jsonMap.get("items");
        return getItemsList(items);
    }

    private List<Room> getRoomsList(List<Map<String, Object>> locations) {
        rooms = new ArrayList<>();
        for (Map<String, Object> location : locations) {
            Room room = new Room();
            room.setName(location.get("name").toString());

            try {
                room.setImage(location.get("image").toString());
            } catch (Exception e) {

            }

            try {
                room.setPicture(location.get("picture").toString());
            } catch (Exception e) {

            }

            room.setDescription(location.get("description").toString());
            room.setPuzzle(Boolean.parseBoolean(location.get("puzzle").toString()));
            room.setDirections(convertJsonDirections((Map<String, Object>) location.get("directions")));

            try {
                room.setNpc(getNpcHelper(location.get("npc").toString()));
            } catch (Exception e) {

            }

            try {
                List<String> itemMap = (List<String>) location.get("items");
                List<String> items = new ArrayList<>();

                for (String item : itemMap) {
                    items.add(item);
                }
                room.setItems(getItemsListForRooms(items));
            } catch (Exception e) {
                System.out.println("items at locations not being added");
            }
            rooms.add(room);
        }
        return rooms;
    }

    private List<Item> getItemsList(List<Map<String, Object>> items) {
        List<Item> itemsList = new ArrayList<>();
        PuzzleItem puzzleItem;
        OxygenItem oxygenItem;
        SleepItem sleepItem;
        FoodItem foodItem;
        for (Map<String, Object> item : items) {
            if (item.get("type").toString().equalsIgnoreCase("puzzle")) {
                puzzleItem = new PuzzleItem();
                puzzleItem.setName(item.get("name").toString());
                puzzleItem.setImage(item.get("image").toString());
                puzzleItem.setDescription(item.get("description").toString());
                Room room = new Room();
                room.setName(item.get("location").toString());
                puzzleItem.setLocation(room);
                puzzleItem.setPuzzle(item.get("use").toString());
                itemsList.add(puzzleItem);
            } else if (item.get("type").toString().equalsIgnoreCase("oxygen")) {
                oxygenItem = new OxygenItem();
                oxygenItem.setName(item.get("name").toString());
                oxygenItem.setImage(item.get("image").toString());
                oxygenItem.setDescription(item.get("description").toString());
                Room room = new Room();
                room.setName(item.get("location").toString());
                oxygenItem.setLocation(room);
                oxygenItem.setModifier(Integer.parseInt(item.get("modifier").toString()));
                itemsList.add(oxygenItem);
            } else if (item.get("type").toString().equalsIgnoreCase("food")) {
                foodItem = new FoodItem();
                foodItem.setName(item.get("name").toString());
                foodItem.setImage(item.get("image").toString());
                foodItem.setDescription(item.get("description").toString());
                Room room = new Room();
                room.setName(item.get("location").toString());
                foodItem.setLocation(room);
                foodItem.setModifier(Integer.parseInt(item.get("modifier").toString()));
                itemsList.add(foodItem);
            } else if (item.get("type").toString().equalsIgnoreCase("sleep")) {
                sleepItem = new SleepItem();
                sleepItem.setName(item.get("name").toString());
                sleepItem.setImage(item.get("image").toString());
                sleepItem.setDescription(item.get("description").toString());
                Room room = new Room();
                room.setName(item.get("location").toString());
                sleepItem.setLocation(room);
                sleepItem.setModifier(Integer.parseInt(item.get("modifier").toString()));
                itemsList.add(sleepItem);
            }
        }
        return itemsList;
    }

    private Map<String, String> convertJsonDirections(Map<String, Object> jsonDirections) {
        Map<String, String> directions = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonDirections.entrySet()) {
            directions.put(entry.getKey(), entry.getValue().toString());
        }
        return directions;
    }

    // TODO method logic
    private NPC getNpcHelper(String name) {
        return new NPC(name);
    }

    private List<Item> getItemsListForRooms(List<String> names) {
        List<Item> itemList = new ArrayList<>();
        List<Item> jsonItems = getThingsList();
        for (int i = 0; i < names.size(); i++) {
            for (Item item : jsonItems) {
                if (names.get(i).equalsIgnoreCase(item.getName())) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }

    private Room getLocationHelper(List<Room> locations, String name) {
        Room room = new Room();
        for (Room location : locations) {
            if (location.getName().equalsIgnoreCase(name)) {
                room.setName(location.getName());
            }
        }
        return room;
    }
}
