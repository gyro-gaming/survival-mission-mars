package com.mars.players;

class Hunger {
    private static int hunger;
    private static Hunger instance = new Hunger();

    public static Hunger getInstance() {
        instance.setHunger(100);
        return instance;
    }
    // getters and setters
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public static int getHunger() {
        return hunger;
    }
    // end getters and setters
}
