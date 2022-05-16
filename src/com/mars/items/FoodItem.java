package com.mars.items;

import com.mars.locations.Room;

public class FoodItem implements Item {
    private String name;
    private String image;
    private String description;
    private Room location;
    private int modifier;

    // getters and setters
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setLocation(Room location) {
        this.location = location;
    }

    @Override
    public Room getLocation() {
        return location;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getModifier() {
        return modifier;
    }
    // end getters and setters

    /**
     * overridden equals()
     * @param object
     * @return boolean
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FoodItem)) {
            return false;
        }
        FoodItem foodItem = (FoodItem) object;
        return this.getName().equalsIgnoreCase(foodItem.getName());
    }

    /**
     * overridden toString()
     * @return String
     */
    @Override
    public String toString() {
        return "You are looking at " + name +
                ", which is a food item to stave off your hunger.\n" +
                description;
    }
}
