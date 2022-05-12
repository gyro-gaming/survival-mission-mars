package com.mars.locations;

import com.mars.client.Game;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ChallengeRoomTest {
    ChallengeRoom room;
    Game game;
    String option;

    @Before
    public void init() {
        game = Game.getInstance();
        option = "Solar Array";
        room = ChallengeRoom.getInstance(game, option, new HashMap<>());
    }

    @Test
    public void showIntro() {
    }

    @Test
    public void runPuzzle() {
        //room.runPuzzle(game, option);
        System.out.println(Game.getRooms());
    }

    @Test
    public void setIsSolved() {
    }

    @Test
    public void isSolved() {
    }
}