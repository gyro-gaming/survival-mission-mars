package com.mars.client;

import com.mars.items.Item;
import com.mars.locations.Room;
import com.mars.players.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CommandProcessorTest {
    private CommandProcessor commandProcessor;
    private Game game;
    private Player player;
    private List<Room> rooms;
    private List<Item> locationItems;
    private Map<String, Boolean> solvedPuzzles;
    private Map<String, Integer> stats;
    private List<Item> inventory;

    List<String> command;

    @Before
    public void init() {
        commandProcessor = new CommandProcessor();
        command = new ArrayList<>();
    }

    @Test
    public void forGo() {
    }

    @Test
    public void forGetCheckIfCanGetItemAssertEquals() {
        command.add("get");
        command.add("gps");
        assertEquals(commandProcessor.forGet(command), "gps");
    }

    @Test
    public void forGetCheckIfInventoryCanHoldTwoOfTheSameItemAssertsEqual() {
        command.add("get");
        command.add("gps");
        commandProcessor.forGet(command);
        command.add("get");
        command.add("gps");
        assertEquals(commandProcessor.forGet(command), "gps is already in your bag.");
    }

    @Test
    public void forLook() {
    }

    @Test
    public void forInspect() {
    }

    @Test
    public void forDrop() {
    }

    @Test
    public void forUse() {
    }
}