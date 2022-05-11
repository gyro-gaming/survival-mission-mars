package com.mars.locations;


import com.mars.items.PuzzleItem;
import com.mars.objects.NPC;

import java.util.List;
import java.util.Map;

public class Room extends Base {
    private String name;
    private String image;
    private String description;
    private Map<String, String> directions;
    private NPC npc;
    private List<PuzzleItem> items;

    public Room() {}

    public Room(String name, String image, String description, Map<String, String> directions, NPC npc, List<PuzzleItem> items) {
        setName(name);
        setDirections(directions);
        setDescription(description);
        setItems(items);
        setNpc(npc);
        setItems(items);
    }

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

    public void setItems(List<PuzzleItem> items) {
        this.items = items;
    }

    public List<PuzzleItem> getItems() {
        return items;
    }

    public PuzzleItem removeItem(String name) {
        // TODO logic to find item in list and remove it
        return new PuzzleItem();
    }

    public void addItem(PuzzleItem addDropped) {
        items.add(addDropped);
    }





    /*
    public boolean getOxygen() {
        return oxygen;
    }

    public void setOxygen(boolean oxygen) {
        this.oxygen = oxygen;
    }

    public String getAsciiArt() {
        return asciiArt;
    }

    public void setAsciiArt(String asciiArt) {
        this.asciiArt = asciiArt;
    }

    //return boolean if room contains a puzzle
    public boolean getPuzzle(){
        return this.puzzle;
    }

    public Puzzle getTypePuzzle(){
        return locationPuzzle;
    }

    //method to instantiate a puzzle, only if Location has a puzzle, else do nothing
    public void createPuzzle() {
        if (getPuzzle()) {
            if (this.getName().equals("Solar Plant")) {
                locationPuzzle = new SolarPuzzleRoom();
            } else if (this.getName().equals("Reactor")) {
                locationPuzzle = new ReactorPuzzleRoom();
            } else if (this.getName().equals("Hydro")) {
                locationPuzzle = new HydroPuzzleRoom();
            } else if (this.getName().equals("Green House")) {
                locationPuzzle = new GhPuzzleRoom();
            }
        }
    }

    //sets boolean value for if room has a puzzle
    private void setPuzzle(boolean puzzle){
        this.puzzle = puzzle;
    }

    public void startPuzzle(){
        //logic to kick off/run the challenge for the user
        locationPuzzle.runPuzzle();
    }

    public boolean isSolved(){
        return locationPuzzle.isSolved();
    }
     */
}
