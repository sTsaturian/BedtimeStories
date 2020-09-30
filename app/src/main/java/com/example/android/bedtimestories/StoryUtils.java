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
 * The class contains various utility methods
 *
 * @author Sergei Tsaturian
 */


public class StoryUtils {

    private static int[] animalIDs = {2, 3, 4, 6, 10};
    private static int[] kidIDs = {};
    private static int[] aesopIDs = {0, 1, 2, 3, 4, 5, 6, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 41, 43, 44, 45, 46, 47, 48, 49,
            50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93,
            94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
            113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130,
            131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148,
            149, 150, 151, 152, 153, 154, 155, 156, 157};
    private static int[] grimmIDs = {7, 8, 9, 10, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289,
            290, 291, 292, 293, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306,
            307, 308, 309, 310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321, 322, 323,
            324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336, 337, 338, 339, 340,
            341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356, 357,
            358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374,
            375, 376, 377, 378, 379, 380, 381, 382, 383, 384, 385, 386, 387, 388, 389, 390, 391,
            392, 393, 394, 395, 396, 397, 398, 399, 400, 401, 402, 403, 404, 405, 406, 407, 408,
            409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425,
            426, 427, 428, 429, 430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442,
            443, 444, 445, 446, 447, 448, 449, 450, 451, 452, 453, 454, 455, 456, 457, 458, 459,
            460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470, 471, 472, 473, 474, 475, 476,
            477, 478};
    private static int[] andersenIDs = {11, 12, 13, 14, 15, 16, 17, 158, 159, 160, 161, 162, 163,
            164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180,
            181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197,
            198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214,
            215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231,
            232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248,
            249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265,
            266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279};

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
        ArrayList<Integer> indices = new ArrayList<>();
        for (int id = 0; id < allStories.size(); id++){
            Story story = allStories.get(id);
            if (!story.isRead()) indices.add(id);
        }
        if (indices.size() == 0) return (int)(Math.random() * allStories.size());
        return indices.get((int)(Math.random() * indices.size()));
    }

    /**
     * This method retrieves the last read story
     *
     * @return int ID of the last read story
     */
    public static int last() {
        return sharedPref.getInt("last_read", 0);
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
     * @param categoryName the name of the category. Could be "All Stories"
     * @return ArrayList<Story> all stories in the given category
     */
    public static ArrayList<Story> getStoryList(String categoryName) {
        if (categoryName.equals("All Stories")) return allStories;
        if (categoryName.equals("Favorites")) return getFavorites();

        ArrayList<Story> categoryStoryList = new ArrayList<>();

        int[] idList = new int[0];
        switch (categoryName) {
            case "Animal Stories":
                idList = animalIDs;
                break;
            case "Kid Stories":
                idList = kidIDs;
                break;
            case "Aesop's Fables":
                idList = aesopIDs;
                break;
            case "Brothers Grimm Stories":
                idList = grimmIDs;
                break;
            case "Hans Christian Andersen's Stories":
                idList = andersenIDs;
                break;
        }
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

    public static void setLastRead(int storyID) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("last_read", storyID);
        editor.apply();
    }

    public static int getNumberOfStories() {
        return allStories.size();
    }
}
