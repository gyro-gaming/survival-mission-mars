package com.mars.objects;

import com.mars.stats.Stats;

import java.util.HashMap;

public class Player {
    private String name;
    private Inventory inventory = new Inventory();
    private Stats stat = new Stats();

    public Player(String name, Inventory inventory, Stats stat) {
        this.name = name;
        this.inventory = inventory;
        this.stat = stat;
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
