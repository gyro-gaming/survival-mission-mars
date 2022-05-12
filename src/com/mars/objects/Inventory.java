package com.mars.objects;

import com.mars.items.Item;
import com.mars.items.PuzzleItem;

import java.util.*;

public class Inventory {
    private List<Item> inventory;
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

    // looking at item and returning description that followed to inventory from location
    public List<String> lookItem() {
        List<String> names = new ArrayList<>();
        for(Item item : inventory) {
            names.add(item.getName());
        }
        return names;
    }

    // retrieving properties from item in location, adding item & properties to inventory
    public String getItemDescription(String name){
        int index = getItemIndex(name);
        return inventory.get(index).getDescription();
    }

    // index'ing through Items for K,V to move to inventory
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

    // dropping item and acquiring the correct item index to remove from inventory
    public Item drop(String item){
        int dropIndex = getItemIndex(item);
        Item dropItem = inventory.get(dropIndex);
        inventory.remove(dropIndex);
        return dropItem;
    }

    public void use(PuzzleItem item){
        System.out.println("You used " + item.getName());
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
