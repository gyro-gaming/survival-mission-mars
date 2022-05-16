package com.mars.timer;

import com.mars.items.Item;
import com.mars.items.SleepItem;

import java.time.Duration;

public class StaminaTimer extends GameTimer{

    public StaminaTimer(long delay) {
        super(delay);
    }

    public static Duration addSleep(Duration duration, Item item) {
        return duration.ofMillis(getValue(item));
    }

    private static long getValue(Item item) {
        return (((SleepItem)item).getModifier() * 60000L>300000L)?300000L:((SleepItem)item).getModifier() * 60000L;
    }
}