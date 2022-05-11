package com.mars.items;

import com.mars.locations.Room;

public interface Item {
    void setName(String a);
    String getName();
    void setImage(String b);
    String getImage();
    void setDescription(String c);
    String getDescription();
    void setLocation(Room d);
    Room getLocation();
    boolean equals(Object f);
    String toString();
}
