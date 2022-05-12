package com.mars.locations;

import java.util.List;
import java.util.Objects;

class Base implements Location {
    private String name;
    private String image;
    private String description;
    private List<Objects> items;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
