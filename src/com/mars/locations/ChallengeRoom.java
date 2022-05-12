package com.mars.locations;

import com.mars.client.Game;
import java.util.HashMap;
import java.util.Map;

class ChallengeRoom extends Room {
    private Game game;
    private boolean isSolved = false;
    private Map<String, Boolean> solved = new HashMap<>();
    private static ChallengeRoom instance = new ChallengeRoom();

    private ChallengeRoom() {}

    public static ChallengeRoom getInstance(Game game, String option, Map<String, Boolean> solved) {
        instance.setGame(game);
        instance.setSolved(solved);
        instance.runPuzzle(game, option, solved);
        return instance;
    }

    public void showIntro(String path) {
        // Display.displayText(path);
    }

    public void runPuzzle(Game game, String option, Map<String, Boolean> solved) {
        System.out.println(solved);
        if (solved.containsKey(option) && solved.get(option)) {
            System.out.println("This challenge has been solved");
            return;
        }
        switch (option) {
            case "Solar Array":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(Puzzles.solarPuzzle(game, option, solved));
                break;
            case "Reactor":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(Puzzles.reactorPuzzle(game, option, solved));
                break;
            case "Environmental Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(Puzzles.environmentalPuzzle(game, option, solved));
                break;
            case "Green House":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(Puzzles.greenHousePuzzle(game, option, solved));
                break;
            case "Hydro Control Room":
                showIntro("path"); // need to get path from Room object based on option
                setSolved(Puzzles.hydroPuzzle(game, option, solved));
                break;
            default:
                System.out.println("Something went wrong!\nThis room does not have a challenge.");
                return;
        }
        solved.put(option, isSolved);
        game.setSolved(solved);
    }

    public void setIsSolved(boolean isSolved) {
        this.isSolved = isSolved;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(Map<String, Boolean> solved) {
        this.solved = solved;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}