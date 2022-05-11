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
    private static Game instance = new Game();
    private Player player;


    private Game() {}

    public static Game getInstance() {
        return instance;
    }

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

    // TODO method logic
    public static void save() {}

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

    private List<Item> getThingsList() {
        Map<String, Object> jsonMap = JsonParser.parseJson("data/json/items.json");
        List<Map<String, Object>> items = (List) jsonMap.get("items");
        return getItemsList(items);
    }

    private List<Room> getRoomsList(List<Map<String, Object>> locations) {
        rooms = new ArrayList<>();
        for (Map<String, Object> location : locations) {
            Room room = new Room();
            room.setName(location.get("name").toString());
            room.setImage(location.get("image").toString());
            room.setDescription(location.get("description").toString());
            room.setDirections(convertJsonDirections((Map<String, Object>) location.get("directions")));
            room.setNpc(getNpcHelper(location.get("npc").toString()));
            room.setItems(getItemsListForRooms((List) location.get("items")));
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
                puzzleItem.setPuzzle(item.get("puzzle").toString());
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
        return new NPC();
    }

    private List<Item> getItemsListForRooms(List<Map<String, Object>> names) {
        List<Item> itemList = new ArrayList<>();
        List<Item> jsonItems = getThingsList();
        for (int i = 0; i < names.size(); i++) {
            for (Item item : jsonItems) {
                if (names.get(i).get("name").toString().equalsIgnoreCase(item.getName())) {
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
