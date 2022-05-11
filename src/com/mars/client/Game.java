package com.mars.client;

import com.mars.items.Item;
import com.mars.objects.NPC;
import com.mars.locations.Room;
import com.mars.items.PuzzleItem;
import com.mars.objects.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Item> items;
    private List<Room> rooms;
    private Player player;
    private static Game instance = new Game();

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

    public static void quit() {
        System.exit(0);
    }

    private List<Room> getLocationList() {
        Map<String, Object> jsonMap = JsonParser.parseJson("data/json/rooms.json");
        List<Map<String, Object>> locations = (List) jsonMap.get("locations");
        return getRoomsList(locations);
    }

    private List<Map<String, Object>> getItemList() {
        Map<String, Object> jsonMap = JsonParser.parseJson("data/json/items.json");
        List<Map<String, Object>> items = (List) jsonMap.get("items");
        return items;
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
        List<Map<String, Object>> jsonItems = getItemList();
        for (int i = 0; i < names.size(); i++) {
            for (Map<String, Object> jsonItem : jsonItems) {
                if (names.get(i).get("name").toString().equalsIgnoreCase(jsonItem.get("name").toString())) {
                    PuzzleItem item = new PuzzleItem();
                    item.setName(jsonItem.get("name").toString());
                    item.setImage(jsonItem.get("image").toString());
                    item.setDescription(jsonItem.get("description").toString());
                    item.setLocation(getLocationHelper(getLocationList(), jsonItem.get("location").toString()));
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
