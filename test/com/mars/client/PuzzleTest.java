package com.mars.client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PuzzleTest {

    Game game = Game.getInstance();


    @Test
    public void askQuestion() {
        Puzzle puzzle = Game.getPuzzles().get(0);
        String x = puzzle.askQuestion();
        assertNotNull(x,puzzle.askQuestion());
    }

    @Test
    public void checkAnswer() {
        Puzzle puzzle = Game.getPuzzles().get(0);
        assertEquals(true,puzzle.checkAnswer("southern hemisphere"));
    }

    @Test
    public void loadPuzzleFromJson() {

        assertNotNull(Puzzle.loadPuzzleFromJson());
    }
}