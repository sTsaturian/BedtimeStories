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
    /* possible values of code:
     * not favorite / unread : 0
     * not favorite / read : 1
     * favorite / unread : 2
     * favorite / read : 3
     */
    private int code;

    public Story(int ID, String name, int resourceID, int code) {
        this.ID = ID;
        this.name = name;
        this.resourceID = resourceID;
        this.code = code;
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

    public int getCode(){ return code;}

    public void setCode(int newCode){
        this.code = newCode;
    }

    public boolean isFavorite(){
        return (code & 2) == 2;
    }

    public boolean isRead(){
        return (code & 1) == 1;
    }
}
