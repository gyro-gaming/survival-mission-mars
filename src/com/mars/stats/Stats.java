package com.mars.stats;

import java.util.HashMap;


public class Stats {
    //fields

    HashMap<String,Integer> stats; //hashmap to sore k:v pairs for stats
    private boolean isHungry = false;
    private int stamina = 100;
    private int oxygenLevel = 100;
    BoneDensity bD = new BoneDensity();
    Health playerHealth = new Health();

    //constructor
    public Stats(){
        stats = new HashMap<>();
        this.isHungry = isHungry();
        this.oxygenLevel = getOxygenLevel();
        this.stamina = getStamina();
    }
    //method to populate current stats with their respective values (currently hard coded)
    public HashMap<String,Integer> getStats(){

        stats.put("Bone Density", bD.getBoneDensity());
        stats.put("Health", playerHealth.getHealth());
        stats.put("Oxygen Level", getOxygenLevel());
        stats.put("Stamina", getStamina());
        return stats;
    }


    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getOxygenLevel() {
        return oxygenLevel;
    }

    public void setOxygenLevel(int oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }


    //for help in debugging
    @Override
    public String toString() {
        return "Stats{" +
                "stats=" + stats +
                ", bD=" + bD +
                ", playerHealth=" + playerHealth +
                '}';
    }
}
