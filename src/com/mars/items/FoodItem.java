package com.mars.items;

import com.mars.locations.Room;

public class FoodItem implements Item {
    private String name;
    private String image;
    private String description;
    private Room location;
    private int modifier;

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


    // TODO write logic
    @Override
    public boolean equals(Object o) {
        return false;
    }

    // TODO write logic
    @Override
    public String toString() {
        return "string";
    }
}

