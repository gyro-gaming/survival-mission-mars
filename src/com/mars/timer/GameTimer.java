package com.mars.timer;

public class GameTimer {
    private long delay;

    public GameTimer(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}