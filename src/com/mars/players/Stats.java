package com.mars.players;

import java.util.HashMap;
import java.util.Map;


public class Stats {
    private Map<String,Integer> stats;
    private Hunger hunger;
    private Stamina stamina;
    private Oxygen oxygen;
    private static Stats instance = new Stats();

    private Stats() {}

    public static Stats getInstance(){
        instance.setHunger(Hunger.getInstance());
        instance.setStamina(Stamina.getInstance());
        instance.setOxygen(Oxygen.getInstance());
        instance.setStats(new HashMap<>());
        return instance;
    }
    // getters and setters
    public void setStats(Map<String,Integer> stats) {
        stats.put("Hunger", Hunger.getHunger());
        stats.put("Stamina", Stamina.getStamina());
        stats.put("Oxygen", Oxygen.getOxygen());
        this.stats = stats;
    }

    public Map<String,Integer> getStats(){
        return stats;
    }

    public void setHunger(Hunger hunger) {
        this.hunger = hunger;
    }

    public void setStamina(Stamina stamina) {
        this.stamina = stamina;
    }

    public void setOxygen(Oxygen oxygen) {
        this.oxygen = oxygen;
    }
    // end getters and setters
}
