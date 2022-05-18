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
    public void forGo_shouldReturnErrorMessage_whenGoingWrongDirection() {
        command.add("go");
        command.add("north");
        assertEquals(commandProcessor.forGo(command),"Can't go that way");

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
       // assertEquals("gps is already in your bag", commandProcessor.forGet(command));
    }

    @Test
    public void forLook() {
        command.add("look");
        command.add("west");
        commandProcessor.forLook(command);
        assertEquals(commandProcessor.forLook(command),"You are in Hydro Control Room.\nIt appears to be an old hydro station-an essential part of restoring water to the outpost.\n There appears to be a helpful placard on the tank..\nYou see the following items in the room: \nbatteries, sleep mask, nozzle");
    }

    @Test
    public void forInspect() {

        command.add("inspect");
        command.add("gps");
        assertEquals(commandProcessor.forInspect(command),"");
    }

    @Test
    public void forDrop() {
        forGetCheckIfCanGetItemAssertEquals();
        command.add("drop");
        command.add("gps");
        assertEquals(commandProcessor.forDrop(command),"You dropped a gps");
    }

    @Test
    public void forUse() {
    }
}