package com.mars.timer;

import com.mars.items.FoodItem;
import com.mars.items.Item;

import java.time.Duration;

public class FoodTimer extends GameTimer{

    public FoodTimer(long delay) {
        super(delay);
    }

    public static Duration addFood(Duration duration, Item item) {
        return duration.ofMillis(getValue(item));
    }

    private static long getValue(Item item) {
        return (((FoodItem)item).getModifier() * 60000L>300000L)?300000L:((FoodItem)item).getModifier() * 60000L;
    }
}