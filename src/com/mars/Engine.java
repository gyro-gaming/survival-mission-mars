package com.mars;

import com.mars.display.Display;
import com.mars.objects.Location;
import com.mars.puzzle.GhPuzzle;
import com.mars.puzzle.HydroPuzzle;
import com.mars.puzzle.ReactorPuzzle;
import com.mars.puzzle.SolarPuzzle;
import com.mars.stats.Stats;
import com.mars.util.*;

import java.util.*;


// main engine for execution of program
public class Engine  {
    // instantiation of necessary variables required
    private boolean isGhSolved = false;
    private boolean isHydroSolved = false;
    private boolean isReactorSolved = false;
    private boolean isSolarSolved = false;

    //end method runApp
    public void checkPuzzles(Location currentLocation){
        if (currentLocation.getTypePuzzle() instanceof GhPuzzle){
            if (currentLocation.isSolved()){
                isGhSolved = currentLocation.isSolved();
            }
        }
        else if(currentLocation.getTypePuzzle() instanceof HydroPuzzle){
            if (currentLocation.isSolved()){
                isHydroSolved = currentLocation.isSolved();
            }
        }
        else if(currentLocation.getTypePuzzle() instanceof ReactorPuzzle){
            if (currentLocation.isSolved()){
                isReactorSolved = currentLocation.isSolved();
            }
        }
        else if (currentLocation.getTypePuzzle() instanceof SolarPuzzle){
            if (currentLocation.isSolved()){
                isSolarSolved = currentLocation.isSolved();
            }
        }
    }
}//end class engine
