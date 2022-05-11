package com.mars.locations;

import com.mars.client.Display;
import com.mars.client.Game;
import com.mars.items.PuzzleItem;

import java.util.HashMap;

class ChallengeRoomImpl extends ChallengeRoom {
    private boolean isSolved = false;
    private HashMap<String, Boolean> solved;
    private static ChallengeRoomImpl instance = new ChallengeRoomImpl();

    private ChallengeRoomImpl() {
    }

    public static ChallengeRoomImpl getInstance() {
        return instance;
    }

    @Override
    public void showIntro(String path) {
        Display.displayText(path);
    }

    @Override
    public void runPuzzle(Game game, String option) {
        if (solved.containsKey(option) && solved.get(option)) {
            System.out.println("This challenge has been solved");
            return;
        }
        switch (option) {
            case "Solar Array":
                PuzzleItem compass;
                PuzzleItem cable;
                showIntro("path"); // need to get path from Room object based on option
                // must have compass and cable in inventory
                //if (game.getPlayer().getInventory().contains(compass) && game.getPlayer().getInventory().contains(cable)) {
                    setIsSolved(Puzzles.solarPuzzle());
                //} else {
                    System.out.println("You do not have the proper items to complete this task.");
                //}
                break;
            case "Reactor":
                showIntro("path"); // need to get path from Room object based on option
                setIsSolved(Puzzles.reactorPuzzle());
                break;
            case "Environmental Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setIsSolved(Puzzles.environmentalPuzzle());
                break;
            case "Green House":
                showIntro("path"); // need to get path from Room object based on option
                setIsSolved(Puzzles.greenHousePuzzle());
                break;
            case "Hydro Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setIsSolved(Puzzles.hydroPuzzle());
                break;
            default:
                System.out.println("Something went wrong!\nThis room does not have a challenge.");
                return;
        }
        solved.put(option, isSolved);
    }

    @Override
    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    @Override
    public boolean isSolved() {
        return isSolved;
    }
}