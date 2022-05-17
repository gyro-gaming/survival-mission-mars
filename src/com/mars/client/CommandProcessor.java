package com.mars.client;

import com.mars.items.Item;
import com.mars.locations.ChallengeRoom;
import com.mars.locations.Room;
import com.mars.players.Player;

import java.util.*;

public class CommandProcessor {
    private String nextLocation = "";
    private int newStamina = 0;
    private Game game;
    private Player player;
    private List<Room> rooms;
    private List<Item> locationItems;
    private Map<String, Boolean> solvedPuzzles;
    private Map<String, Integer> stats;
    private List<Item> inventory;
    private Vector<String> items;
    private Room currentLocation;

    public CommandProcessor() {
        this.game = Game.getInstance();
        setPlayer();
        setRooms();
        setLocationItems();
        setSolvedPuzzles();
        setStats();
        setInventory();
        setCurrentLocation();
        Player.calculateOxygen();
    }

    public void setPlayer() {
        this.player = game.getPlayer();
    }

    public void setRooms() {
        this.rooms = Game.getRooms();
    }

    public void setLocationItems() {
        this.locationItems = Game.getItems();
    }

    public void setSolvedPuzzles() {
        this.solvedPuzzles = Game.getSolved();
    }

    public void setStats() {
        this.stats = game.getStats();
    }

    public void setInventory() {
        this.inventory = game.getInventory();
    }

    public void setCurrentLocation() {
        this.currentLocation = Game.getRooms().get(0);
    }

    public void setCurrentLocation(Room currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    public Vector<String> getItems() {
        try {
            items = new Vector<>();
            for (Item i : currentLocation.getItems()) {
                items.add(i.getName().replace(" ", "_"));
            }
        } catch (Exception e) {}
        return items;
    }

    public List<String> getCommand(String userInput) {
        List<String> input = new ArrayList<>(); //empty arraylist to store parsed command
        StringTokenizer cleanInput = new StringTokenizer(userInput); //pass string through function to strip out extra words
        while (cleanInput.hasMoreTokens()) {
            input.add(cleanInput.nextToken().toLowerCase());
        }
        return input;
    }

    public String forGo(List<String> command) {
        StringBuilder sb = new StringBuilder();
        try {
            if (currentLocation.getDirections().containsKey(command.get(1))) {                                           // checking if currentLocation has direction of movement provided by user input as an option
                nextLocation = currentLocation.getDirections().get(command.get(1));                                     // moving to nextLocation
            } else {
                return "Can't go that way";
            }

        } catch (NullPointerException e) {
            return "Can't go that way";
        } catch (ArrayIndexOutOfBoundsException e) {
           return "Go where?";
        }
        Room newRoom = null;
        for (Room r : Game.getRooms()) {
            if (r.getName().equals(nextLocation)) {
                newRoom = r;
            }
        }
        setCurrentLocation(newRoom);
        sb.append(currentLocation.toString() + "\n\n");
        ChallengeRoom.getInstance(game, currentLocation.getName(), Game.getSolved(), Game.getPuzzles());
        return sb.toString();
    }

    public String forGet(List<String> command) {
        if (inventory.size() == 4) {
            return "Your bag is full.";
        }
        String noun = command.get(1).replace("_", " ").toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (Item item : locationItems) {
            if (item.getName().equals(noun) && item.getLocation().getName().equals(currentLocation.getName()) && !(player.getInventory().lookItem().contains(item.getName()))) {
                player.getInventory().add(item);
                currentLocation.removeItem(item);
                sb.append(item.getName());
            }
        }
        setLocationItems();
        return sb.toString();
    }

    public String forLook(List<String> command) {
        String name = "";
        String result = "";
        try {
            for (Map.Entry<String, String> map : currentLocation.getDirections().entrySet()) {
                if (map.getKey().equals(command.get(1))) {
                    name = map.getValue();
                }
            }
            for (Room room : rooms) {
                if (room.getName().equals(name)) {
                    result = room.toString();
                }
            }
        } catch (NullPointerException e) {
        }
        return result;
    }

    public String forInspect(List<String> command) {
        String item = command.get(1).replace("_", " ").toLowerCase();
        StringBuilder sb = new StringBuilder();
        try {
            for (Item i : locationItems) {
                if (i.getName().toLowerCase().equals(item) && i.getLocation().getName().equals(currentLocation.getName())) {
                    sb.append(i);
                }
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    public String forDrop(List<String> command) {
        Item droppedItem = null;
        try {
            droppedItem = game.getPlayer().getInventory().drop(command.get(1));
            currentLocation.addItem(droppedItem);
        } catch (Exception e) {}
        return "You dropped a " + droppedItem.getName();
    }

    public String forUse(List<String> command) {
        try {
            // TODO: what about consumable items? (mealkit) ...or Items that actuate something else? (key -> reactor)
            if (player.getInventory().lookItem().contains(command.get(1))) {
                System.out.println("Item is here");

            } else {
                System.out.println("Use what?");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Use what?");
        }
        return "";
    }
}
