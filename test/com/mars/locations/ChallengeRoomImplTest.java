package com.mars.locations;

import com.mars.client.Game;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChallengeRoomImplTest {
    ChallengeRoomImpl room;
    Game game;
    String option;

    @Before
    public void init() {
        room = new ChallengeRoomImpl();
        game = Game.getInstance();
        option = "Solar Array";
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