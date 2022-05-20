package com.mars.players;

import com.mars.items.Item;

import java.util.*;

public class Inventory {
    private static List<Item> inventory;
    private static Inventory instance = new Inventory();

    private Inventory() {}

    public static Inventory getInstance() {
        instance.setInventory(new ArrayList<>());
        return instance;
    }

    // getters and setters
    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public List<Item> getInventory() {
        return inventory;
    }
    // end getters and setters

    private int getItemIndex(String itemName){
        int index = 0;
        int counter = 0;
        for(Item item: inventory){
            if (item.getName().equals(itemName)){
                index = counter;
            }
            counter++;
        }
        return index;
    }

    public void add(Item item) {
        inventory.add(item);
    }

    public Item drop(String item){
        int dropIndex = getItemIndex(item);
        Item dropItem = inventory.get(dropIndex);
        inventory.remove(dropIndex);
        return dropItem;
    }

    public static void use(Item item){
        try {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).equals(item)) {
                    inventory.remove(i);
                }
            }
        } catch (Exception e) {}
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < inventory.size(); i++) {
            if (i == inventory.size() - 1) {
                sb.append(inventory.get(i).getName());
            } else {
                sb.append(inventory.get(i).getName() + ", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
