package com.mars.players;

import com.mars.locations.Room;

public class Player {
    private String name;
    private Room location;
    private Inventory inventory;
    private Stats stats;
    private static Player instance = new Player();

    private Player() {}

    public static Player getInstance() {
        instance.setInventory(Inventory.getInstance());
        instance.setStats(Stats.getInstance());
        return instance;
    }
    // getters and setters
    public void setLocation(Room location) {
        this.location = location;
    }

    public Room getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }
    // end getters and setters
}
