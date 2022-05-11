package com.mars.objects;

import com.mars.locations.Room;

public class Player {
    private String name;
    private Room location;
    private Inventory inventory = new Inventory();
    private Stats stat = new Stats();

    public Player() {}

    public Player(String name, Inventory inventory, Stats stat) {
        this.name = name;
        this.inventory = inventory;
        this.stat = stat;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Stats getStat() {
        return stat;
    }

    public void setStat(Stats stat) {
        this.stat = stat;
    }
}
