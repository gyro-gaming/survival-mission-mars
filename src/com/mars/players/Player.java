package com.mars.players;

import com.mars.client.Game;
import com.mars.items.FoodItem;
import com.mars.items.Item;
import com.mars.items.OxygenItem;
import com.mars.items.SleepItem;
import com.mars.locations.Room;
import com.mars.timer.FoodTimer;
import com.mars.timer.OxygenTimer;
import com.mars.timer.StaminaTimer;

import java.time.Duration;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Player {
    private String name;
    private Room location;
    private Inventory inventory;
    private Duration duration;
    private static Stats stats;
    private static Timer timer;
    private static int oxygen;
    private static int stamina;
    private static int hunger;
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

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
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

    public void removeInventory(Item item) {

    }

    public static String addOxygen(Item item) {
        if (item instanceof OxygenItem) {
            int value = ((OxygenItem) item).getModifier();
            Inventory.use(item);
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
                statsMap.put("Oxygen", setOxygenInterval() / 3);
                Game.setStats(statsMap);
            }
        }, delay, period);
    }

    private static final int setOxygenInterval() {
        if (oxygen == 1) {
            timer.cancel();
        }
        return --oxygen;
    }

    public static String addStamina(Item item) {
        if (item instanceof SleepItem) {
            int value = ((SleepItem) item).getModifier();
            value = (value * 100);
            Inventory.use(item);
            stamina = value + stamina;
            if (stamina > 300) {
                stamina = 300;
                return "You have maxed out your sleep time!";
            } else {
                return "You added " + value + " stamina and now have a total of " + stamina + " stamina.";
            }
        } else {
            return item.getName() + " is not of type stamina.";
        }
    }

    public static void calculateStamina() {
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        stamina = (int)(new StaminaTimer(5 * 60).getDelay());
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                Map<String, Integer> statsMap = getStats().getStats();
                statsMap.put("Stamina", setStaminaInterval() / 3);
                Game.setStats(statsMap);
            }
        }, delay, period);
    }

    private static final int setStaminaInterval() {
        if (stamina == 1) {
            timer.cancel();
        }
        return --stamina;
    }

    public static String addFood(Item item) {
        if (item instanceof FoodItem) {
            int value = ((FoodItem) item).getModifier();
            value = (value * 100);
            Inventory.use(item);
            hunger = value + hunger;
            if (hunger > 300) {
                hunger = 300;
                return "You have maxed out your stomach!";
            } else {
                return "You added " + value + " food and now have a total of " + hunger + " food.";
            }
        } else {
            return item.getName() + " is not of type food.";
        }
    }

    public static void calculateFood() {
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        hunger = (int)(new FoodTimer(5 * 60).getDelay());
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Map<String, Integer> statsMap = getStats().getStats();
                statsMap.put("Hunger", setHungerInterval() / 3);
                Game.setStats(statsMap);
            }
        }, delay, period);
    }

    private static final int setHungerInterval() {
        if (hunger == 1) {
            timer.cancel();
        }
        return --hunger;
    }
}
