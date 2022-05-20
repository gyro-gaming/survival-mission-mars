package com.mars.items;

import com.mars.client.Game;
import com.mars.locations.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodItemTest {
    Item item = null;
    Game game = Game.getInstance();

    @Test
    public void testEquals() {
        for (Item i : Game.getItems()){
            if (i.getName().equals("Bread")){
                item = i;
            }
        }
        for (Item i : Game.getItems()){
            if (i.getName().equals("Bread")){
                assertTrue(i.equals(item));
            }
        }
    }
}