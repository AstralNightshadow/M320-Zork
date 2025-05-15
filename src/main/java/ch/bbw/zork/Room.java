package ch.bbw.zork;
/**
 * Author:  Michael Kolling, Version: 1.1, Date: August 2000
 * refactoring: Rinaldo Lanza, September 2020
 */

import java.util.ArrayList;

public class Room {
    private String description;
    private Room northExit, southExit, eastExit, westExit;
    private ArrayList<String> items; // List of items in the room

    public Room(String description) {
        this.description = description;
        this.items = new ArrayList<>();
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        northExit = north;
        eastExit = east;
        southExit = south;
        westExit = west;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public boolean removeItem(String item) {
        return items.remove(item);
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public String shortDescription() {
        return description;
    }

    public String longDescription() {
        return "You are " + description + ".\n" + getExitString() + "\nItems here: " + items;
    }

    private String getExitString() {
        String exits = "Exits:";
        if (northExit != null) exits += " north";
        if (eastExit != null) exits += " east";
        if (southExit != null) exits += " south";
        if (westExit != null) exits += " west";
        return exits;
    }

    public Room nextRoom(String direction) {
        switch (direction) {
            case "north":
                return northExit;
            case "east":
                return eastExit;
            case "south":
                return southExit;
            case "west":
                return westExit;
            default:
                return null;
        }
    }
}




