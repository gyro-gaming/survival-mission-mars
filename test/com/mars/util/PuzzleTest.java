package com.mars.util;

import com.mars.puzzle.*;
import org.junit.Assert;
import org.junit.Test;

public class PuzzleTest {
    GhPuzzle ghPuzzle = new GhPuzzle();
    HydroPuzzle hydroPuzzle = new HydroPuzzle();
    SolarPuzzle solarPuzzle = new SolarPuzzle();
    ReactorPuzzle reactorPuzzle = new ReactorPuzzle();

    @Test
    public void ghPuzzle_shouldReturnFalse_whenIsSolvedIsCalled(){
        Assert.assertFalse(ghPuzzle.isSolved());
    }
    @Test
    public void hydroPuzzle_shouldReturnFalse_whenIsSolvedIsCalled(){
        Assert.assertFalse(hydroPuzzle.isSolved());
    }
    @Test
    public void solarPuzzle_shouldReturnFalse_whenIsSolvedIsCalled(){
        Assert.assertFalse(solarPuzzle.isSolved());
    }
    @Test
    public void reactorPuzzle_shouldReturnFalse_whenIsSolvedIsCalled(){
        Assert.assertFalse(reactorPuzzle.isSolved());
    }




}
