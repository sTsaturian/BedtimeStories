package com.example.android.bedtimestories;

import java.io.Serializable;

/**
 * The class holds information about a single story: id, name and path to the resource
 *
 * @author Sergei Tsaturian
 */

public class Story implements Serializable {
    private final int ID;
    private final String name;
    private final int resourceID;
    private int code;

    /**
     * Create a story with given parameters.
     *
     * @param ID         The unique ID of the story.
     * @param name       The title of the story.
     * @param resourceID The id of the resource string containing the story text.
     * @param code       The code that shows whether the story is read/favorite.
     *                   For more info see {@link #getCode()}.
     */
    public Story(int ID, String name, int resourceID, int code) {
        this.ID = ID;
        this.name = name;
        this.resourceID = resourceID;
        this.code = code;
    }

    public int getID() {
        return ID;
    }

    /**
     * Retrieves the title of the story.
     *
     * @return String title of the story.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the id of the string with the story text.
     *
     * @return int id of the string with the story text.
     */
    public int getResourceID() {
        return resourceID;
    }

    /**
     * Retrieve the code of the story.
     * possible values of the code:
     * not favorite / unread : 0
     * not favorite / read : 1
     * favorite / unread : 2
     * favorite / read : 3
     *
     * @return int The corresponding code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets the code of the story to a specified integer value.
     *
     * @param newCode the value of the new code.
     */
    public void setCode(int newCode) {
        if (newCode >= 0 && newCode <= 3)
            code = newCode;
    }

    /**
     * Checks whether the story is marked as favorite
     *
     * @return boolean true if the story is favorite, false otherwise
     */
    public boolean isFavorite() {
        return (code & 2) == 2;
    }

    /**
     * Checks whether the story is marked as read
     *
     * @return boolean true if the story is read, false otherwise
     */
    public boolean isRead() {
        return (code & 1) == 1;
    }
}
