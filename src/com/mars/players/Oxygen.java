package com.mars.players;

class Oxygen {
    private static int oxygen;
    private static Oxygen instance = new Oxygen();

    private Oxygen() {}

    public static Oxygen getInstance() {
        instance.setOxygen(100);
        return instance;
    }
    // getters and setters
    public void setOxygen(int oxygen) {
        this.oxygen = oxygen;
    }

    public static int getOxygen() {
        return oxygen;
    }
    // end getters and setters
}