package com.mars.locations;

import com.mars.client.Game;
import com.mars.items.Item;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class RoomTest {
    Room room = null;
    Game game = Game.getInstance();

    @Before
    public void init() {
    }

    @Test
    public void removeItem() {
        for (Room i : Game.getRooms()){
            if (i.getName().equals("Gym")){
                room = i;
            }
        }
        Item i = null;
        assertEquals(4,room.getItems().size());
        room.removeItem(room.getItems().get(0));
        assertEquals(3,room.getItems().size());
    }

    @Test
    public void addItem() {
        for (Room i : Game.getRooms()){
            if (i.getName().equals("Gym")){
                room = i;
            }
        }
        assertEquals(4,room.getItems().size());
        for (Item item : Game.getItems()){
            if(item.getName().equals("gps")){
                room.addItem(item);
                assertEquals(5, room.getItems().size());
            }
        }
    }

    @Test
    public void testEquals() {
        for (Room i : Game.getRooms()){
            if (i.getName().equals("Gym")){
                room = i;
            }
        }
        for (Room i : Game.getRooms()){
            if (i.getName().equals("Gym")){
                assertTrue(i.equals(room));
            }
        }
    }
}