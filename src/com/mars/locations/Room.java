package com.mars.locations;

import com.mars.items.Item;
import com.mars.players.NPC;

import java.util.List;
import java.util.Map;

public class Room extends Base {
    private String name;
    private String image;
    private String description;
    private Map<String, String> directions;
    private NPC npc;
    private List<Item> items;

    public Room() {}

    public Room(String name, String image, String description, Map<String, String> directions, NPC npc, List<Item> items) {
        setName(name);
        setImage(image);
        setDirections(directions);
        setDescription(description);
        setItems(items);
        setNpc(npc);
        setItems(items);
    }

    // getters and setters
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

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public NPC getNPC() {
        return npc;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
    // end getters and setters

    /**
     * method to remove an item from the room's inventory when picked up by a player
     * @param item
     */
    public boolean removeItem(Item item) {
        System.out.println(item);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(item)) {
                return items.remove(items.get(i));
            }
        }
        return false;
    }

    /**
     * method to add an item to the room's inventory when dropped by a player
     * @param item
     */
    public void addItem(Item item) {
        items.add(item);
    }

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
        if (!(object instanceof Room)) {
            return false;
        }
        Room room = (Room) object;
        return this.getName().equalsIgnoreCase(room.getName());
    }

    /**
     * overridden toString()
     * @return String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("You are in " + name + ".\n");
        sb.append(description + ".\n");
        sb.append("You see the following items in the room: \n");
        try {
            for (int i = 0; i < items.size(); i++) {
                if (i == items.size() - 1) {
                    sb.append(items.get(i).getName());
                } else {
                    sb.append(items.get(i).getName() + ", ");
                }
            }
        } catch (Exception e) {
            sb.append("There are no items in this room.");
        }
        return sb.toString();
    }
}
