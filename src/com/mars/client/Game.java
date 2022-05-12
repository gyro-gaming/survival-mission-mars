package com.mars.client;

import com.mars.items.FoodItem;
import com.mars.items.Item;
import com.mars.items.OxygenItem;
import com.mars.objects.NPC;
import com.mars.locations.Room;
import com.mars.items.PuzzleItem;
import com.mars.objects.Player;

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

    private Game() {
    }

    public static Game getInstance() {
        player = Player.getInstance();
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

    public Player getPlayer() {
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

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }

    public static List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setSolved(Map<String, Boolean> solved) {
        this.solved = solved;
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

    // TODO method logic
    public static void save() {
    }

    // TODO method logic
    public Game retrieveSave() {
        return new Game();
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

            room.setDescription(location.get("description").toString());
            room.setDirections(convertJsonDirections((Map<String, Object>) location.get("directions")));

            try {
                room.setNpc(getNpcHelper(location.get("npc").toString()));
            } catch (Exception e) {

            }

            try {
                List<Map<String, Object>> itemMap = (List<Map<String, Object>>) location.get("items");
                List<String> items = new ArrayList<>();
                for (Map<String, Object> item : itemMap) {
                    for (Map.Entry<String, Object> entry : item.entrySet()) {
                        items.add(entry.getKey());
                    }
                }
                room.setItems(getItemsListForRooms(items));
            } catch (Exception e) {

            }
            rooms.add(room);
        }
        return rooms;
    }

    private List<Item> getItemsList(List<Map<String, Object>> items) {
        List<Item> itemsList = new ArrayList<>();
        PuzzleItem puzzleItem;
        OxygenItem oxygenItem;
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
                puzzleItem.setNeeds(item.get("needs").toString());
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
