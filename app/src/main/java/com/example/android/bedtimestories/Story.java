package com.example.android.bedtimestories;

/**
 * The class holds information about a single story: id, name and path to the resource
 *
 * @author Sergei Tsaturian
 */

public class Story {
    private int ID;
    private String name;
    private int resourceID;

    public Story(int ID, String name, int resourceID) {
        this.ID = ID;
        this.name = name;
        this.resourceID = resourceID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getResourceID() {
        return resourceID;
    }
}
