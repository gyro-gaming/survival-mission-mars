package com.mars.players;

class Stamina {
    private static int stamina;
    private static Stamina instance = new Stamina();

    public static Stamina getInstance() {
        instance.setStamina(100);
        return instance;
    }
    // getters and setters
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public static int getStamina() {
        return stamina;
    }
    // end getters and setters
}
