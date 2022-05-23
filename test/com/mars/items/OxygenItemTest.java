package com.mars.items;

import com.mars.client.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OxygenItemTest {
    Item item = null;
    Game game = Game.getInstance();

    @Test
    public void testEquals() {
        for (Item i : Game.getItems()){
            if (i.getName().equals("oxygen canister")){
                item = i;
            }
        }
        for (Item i : Game.getItems()){
            if (i.getName().equals("oxygen canister")){
                assertTrue(i.equals(item));
            }
        }
    }
}
