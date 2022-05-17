package com.mars.players;

import com.mars.client.Game;
import com.mars.items.Item;
import com.mars.items.OxygenItem;
import com.mars.locations.Room;
import com.mars.timer.OxygenTimer;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private String name;
    private Room location;
    private Inventory inventory;
    private static Stats stats;
    private static Timer timer;
    private static int oxygen;
    private static Player instance = new Player();

    private Player() {}

    public static Player getInstance() {
        instance.setInventory(Inventory.getInstance());
        instance.setStats(Stats.getInstance());
        return instance;
    }
    // getters and setters
    public void setLocation(Room location) {
        this.location = location;
    }

    public Room getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public static void setStats(Stats stats) {
        Player.stats = stats;
    }

    public static Stats getStats() {
        return stats;
    }
    // end getters and setters

    public static String addOxygen(Item item) {
        if (item instanceof OxygenItem) {
            int value = ((OxygenItem) item).getModifier();
            value = (value * 100);
            oxygen = value + oxygen;
            if (oxygen > 300) {
                oxygen = 300;
                return "You have maxed out your 02!";
            } else {
                return "You added " + value + " oxygen and now have a total of " + oxygen + " oxygen.";
            }
        } else {
            return item.getName() + " is not of type oxygen.";
        }
    }

    public static void calculateOxygen() {
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        oxygen = (int)(new OxygenTimer(5 * 60).getDelay());
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Map<String, Integer> statsMap = getStats().getStats();
                statsMap.put("Oxygen", setInterval() / 3);
                Game.setStats(statsMap);
                System.out.println(Game.getStats().get("Oxygen"));
            }
        }, delay, period);
    }

    private static final int setInterval() {
        if (oxygen == 1) {
            timer.cancel();
            Game.quit();
        }
        return --oxygen;
    }
}
