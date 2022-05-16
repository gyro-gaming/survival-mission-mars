package com.mars.timer;

import com.mars.items.Item;
import com.mars.items.OxygenItem;
import java.time.Duration;

public class OxygenTimer extends GameTimer{

    public OxygenTimer(long delay) {
        super(delay);
    }

    public static Duration addOxygen(Duration duration, Item item) {
        return duration.ofMillis(getValue(item));
    }

    private static long getValue(Item item) {
        return (((OxygenItem)item).getModifier() * 60000L>300000L)?300000L:((OxygenItem)item).getModifier() * 60000L;
    }
}