package com.example.android.bedtimestories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * The class contains different utility methods
 *
 * @author Sergei Tsaturian
 */


public class StoryUtils {

    private static int[] animalIDs = {0, 1, 2};
    private static int[] kidIDs = {1};

    private static ArrayList<Story> allStories;

    private static SharedPreferences sharedPref;


    /**
     * This method does all necessary initializations. This is called on start of the app.
     */
    public static void loadStoryLists(Context context) {
        initializeAllStories(context);
    }

    /**
     * This method retrieves a random unread story from the list of all available stories
     *
     * @return int ID of a random unread story
     */
    public static int random() {
        //TODO
        return 0;
    }

    /**
     * This method retrieves the last read story
     *
     * @return int ID of the last read story
     */
    public static int last() {
        //TODO
        return 0;
    }

    /**
     * This method retrieves the story by given ID
     *
     * @param storyID ID of the story to retrieve
     * @return Story the corresponding story
     */
    public static Story getStory(int storyID) {
        return allStories.get(storyID);
    }

    /**
     * This method retrieves all stories with given category
     *
     * @param categoryName the name of the category. Could be "All"
     * @return ArrayList<Story> all stories in the given category
     */
    public static ArrayList<Story> getStoryList(String categoryName) {
        if (categoryName.equals("All")) return allStories;
        if (categoryName.equals("Favorites")) return getFavorites();

        ArrayList<Story> categoryStoryList = new ArrayList<>();

        int[] idList = new int[0];
        if (categoryName.equals("Animals")) idList = animalIDs;
        else if (categoryName.equals("Kids")) idList = kidIDs;

        for (int ID : idList) {
            categoryStoryList.add(allStories.get(ID));
        }
        return categoryStoryList;
    }

    public static ArrayList<Story> getFavorites(){
        ArrayList<Story> favorites = new ArrayList<>();
        for (Story story : allStories){
            if (story.isFavorite())
                favorites.add(story);
        }
        return favorites;
    }

    /**
     * Helper method that creates the story list with all stories
     */
    private static void initializeAllStories(Context context) {
        sharedPref = getDefaultSharedPreferences(context);
        allStories = new ArrayList<>();
        Resources res = context.getResources();
        TypedArray storiesArray = res.obtainTypedArray(R.array.story_arrays);
        for (int id = 0; id < storiesArray.length(); id++){
            int arrayResourceId = storiesArray.getResourceId(id, 0);
            TypedArray storyArray = res.obtainTypedArray(arrayResourceId);
            String storyName = storyArray.getString(0);
            @SuppressLint("ResourceType")
            int resourceId = storyArray.getResourceId(1,0);
            int code = sharedPref.getInt(String.valueOf(id), 0);
            allStories.add(new Story(id, storyName, resourceId, code));
            storyArray.recycle();
        }
        storiesArray.recycle();
    }

    /**
     * This method marks/un-marks a story as favorite and saves the settings
     * @param ID int ID of the story
     * @param status boolean true if the story should be favorite, false otherwise
     */
    public static void changeFavoriteStatus(int ID, boolean status){
        Story story = getStory(ID);
        int oldCode = story.getCode();
        int newCode = (status)? oldCode | 2 : oldCode & 1;
        story.setCode(newCode);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(ID), newCode);
        editor.apply();
    }

    /**
     * This method marks/un-marks a story as read and saves the settings
     * @param ID int ID of the story
     * @param status boolean true if the story should be read, false otherwise
     */
    public static void changeReadStatus(int ID, boolean status){
        Story story = getStory(ID);
        int oldCode = story.getCode();
        int newCode = (status)? oldCode | 1 : oldCode & 2;
        story.setCode(newCode);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(ID), newCode);
        editor.apply();
    }
}
