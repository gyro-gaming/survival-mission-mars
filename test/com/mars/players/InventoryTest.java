package com.mars.players;

import com.mars.client.Game;
import com.mars.items.FoodItem;
import com.mars.items.Item;

import org.junit.Test;
import static org.junit.Assert.*;

public class InventoryTest {
    Game game = Game.getInstance();
    Item item = new FoodItem();
    Inventory inventory = Inventory.getInstance();
    @Test
    public void add() {
        assertEquals(0,inventory.getInventory().size());

        for (Item item : Game.getItems()){
            inventory.add(item);
        }
        assertEquals(33,inventory.getInventory().size());
    }

    @Test
    public void drop() {
        for (Item item : Game.getItems()){
            inventory.add(item);
        }
        inventory.drop("blanket");
        assertEquals(32,inventory.getInventory().size());
    }
}