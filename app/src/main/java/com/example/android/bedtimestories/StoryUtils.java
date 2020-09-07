package com.example.android.bedtimestories;

import android.util.Log;

import java.util.ArrayList;

/**
 * The class contains different utility methods
 *
 * @author Sergei Tsaturian
 */


public class StoryUtils {

    private static int[] animalIDs = {0, 1, 2};
    private static int[] kidIDs = {0};

    private static ArrayList<Story> allStories;

    /**
     * This method does all necessary initializations. This is called on start of the app.
     */
    public static void loadStoryLists() {
        initializeAllStories();
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
        Log.i("StoryUtils", categoryName);
        if (categoryName.equals("All")) return allStories;

        ArrayList<Story> categoryStoryList = new ArrayList<>();

        int[] idList = new int[0];
        if (categoryName.equals("Animals")) idList = animalIDs;
        else if (categoryName.equals("Kids")) idList = kidIDs;

        for (int ID : idList) {
            categoryStoryList.add(allStories.get(ID));
        }
        return categoryStoryList;
    }

    /**
     * Helper method that creates the story list with all stories
     */
    private static void initializeAllStories() {
        int ID = 0;
        allStories = new ArrayList<>();
        allStories.add(new Story(ID++, "Story 0", R.string.story0text));
        allStories.add(new Story(ID++, "Story 1", R.string.story1text));
        allStories.add(new Story(ID++, "Story 2", R.string.story2text));
    }
}
