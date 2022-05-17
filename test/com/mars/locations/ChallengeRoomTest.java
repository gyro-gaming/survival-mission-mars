package com.mars.locations;

import com.mars.client.Game;
import com.mars.client.Puzzle;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class ChallengeRoomTest {
    ChallengeRoom room;
    Game game;
    String option;

    @Before
    public void init() {
        game = Game.getInstance();
        List<Puzzle> puzzles = Game.getPuzzles();
        option = "Solar Array";
        room = ChallengeRoom.getInstance(game, option, new HashMap<>(), puzzles);

    }

    @Test
    public void showIntro() {
    }

    @Test
    public void runPuzzle() {
        room.runPuzzle("Solar Array");
        // System.out.println(Game.getRooms());
    }

    @Test
    public void setIsSolved() {
    }

    @Test
    public void isSolved() {
    }
}