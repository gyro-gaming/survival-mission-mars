package com.mars.locations;

import com.mars.client.Game;
import com.mars.locations.Room;

 abstract class ChallengeRoom extends Room {
    abstract void showIntro(String a);
    abstract void runPuzzle(Game b, String c);
    abstract void setIsSolved(boolean d);
    abstract boolean isSolved();
}