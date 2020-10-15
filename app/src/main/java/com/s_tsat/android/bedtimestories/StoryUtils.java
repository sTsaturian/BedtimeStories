package com.s_tsat.android.bedtimestories;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * The class contains the lists of stories in each category along with various utility methods
 *
 * @author Sergei Tsaturian
 */


public class StoryUtils {
    private static final int[] aesopIDs = {0, 1, 2, 3, 4, 5, 6, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 41, 43, 44, 45, 46, 47, 48, 49,
            50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93,
            94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112,
            113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130,
            131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148,
            149, 150, 151, 152, 153, 154, 155, 156, 157};
    private static final int[] grimmIDs = {7, 8, 9, 10, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289,
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
    private static final int[] andersenIDs = {11, 12, 13, 14, 15, 16, 17, 158, 159, 160, 161, 162, 163,
            164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 180,
            181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195, 196, 197,
            198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214,
            215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231,
            232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248,
            249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265,
            266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279};
    private static final int[] kiplingIDs = {5, 283, 289, 296, 314, 345, 355, 361, 365, 479, 480,
            481};
    private static final int[] animalIDs = {2, 3, 4, 5, 6, 13, 18, 19, 20, 21, 22, 23, 25, 27, 28,
            29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 40, 41, 43, 44, 45, 46, 47, 48, 49, 52, 53, 54,
            55, 56, 58, 59, 60, 61, 63, 64, 65, 66, 68, 69, 70, 71, 72, 73, 74, 75, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 101, 102, 103,
            105, 106, 107, 108, 109, 111, 113, 114, 115, 116, 117, 118, 120, 121, 123, 124, 125,
            126, 127, 128, 129, 130, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143,
            144, 145, 147, 148, 149, 150, 151, 152, 161, 164, 169, 179, 180, 182, 195, 199, 212,
            213, 235, 236, 244, 246, 255, 258, 283, 289, 291, 294, 296, 299, 303, 307, 313, 314,
            316, 325, 326, 327, 330, 342, 345, 348, 352, 355, 361, 365, 369, 375, 377, 382, 390,
            400, 402, 425, 426, 467, 469, 470, 474, 475, 476, 481};
    private static final int[] famousIDs = {0, 1, 2, 4, 6, 7, 8, 12, 15, 20, 62, 101, 161, 165, 176,
            177, 179, 180, 247, 248, 249, 250, 251, 252, 253, 294, 300, 321, 399, 400, 422, 424};
    private static final int[] happyEndIDs = {8, 10, 161, 180, 247, 248, 249, 250, 251, 252, 253,
            282, 284, 287, 304, 315, 324, 334, 335, 341, 343, 354, 356, 357, 359, 378, 381, 386,
            387, 393, 397, 400, 401, 403, 407, 409, 411, 417, 418, 422, 423, 427, 430, 433, 435,
            442, 446, 454, 455, 458, 476};
    private static final int[] royalIDs = {10, 12, 15, 163, 168, 171, 177, 179, 183, 197, 225, 268,
            280, 284, 287, 292, 298, 305, 309, 316, 320, 334, 336, 342, 346, 347, 353, 354, 357,
            358, 359, 370, 381, 393, 394, 399, 403, 409, 414, 418, 419, 420, 422, 424, 447, 449,
            450, 451, 455, 457, 458, 461, 464, 465};
    private static final int[] familyIDs = {1, 38, 50, 99, 131, 146, 166, 173, 184, 199, 200, 233,
            247, 248, 249, 250, 251, 252, 253, 256, 265, 270, 182, 285, 288, 295, 298, 300, 324,
            329, 343, 360, 362, 372, 374, 378, 380, 384, 387, 401, 411, 412, 415, 416, 417, 423,
            429, 431, 433, 435, 438, 442, 443, 444, 448, 449, 453, 456, 457, 460, 463, 465, 468,
            471, 473};
    private static final int[] mythIDs = {9, 51, 158, 161, 162, 170, 176, 184, 185, 203, 217, 239,
            263, 268, 271, 280, 281, 290, 304, 308, 309, 310, 319, 333, 336, 359, 366, 391, 417,
            421, 424, 428, 439, 466, 478};
    private static final int[] objectsIDs = {5, 14, 16, 24, 26, 100, 153, 160, 165, 168, 172, 175,
            181, 182, 186, 187, 191, 194, 197, 198, 201, 202, 208, 219, 222, 229, 230, 238, 243,
            257, 261, 264, 273, 277, 279};
    private static final int[] religiousIDs = {171, 178, 204, 209, 221, 223, 224, 228, 233, 234,
            237, 272};
    private static final int[] soldierIDs = {158, 292, 293, 297, 419, 440};
    private static final int[] thoughtIDs = {190, 193, 206, 214, 227, 286};
    private static final int[] shortIDs = {0, 1, 2, 3, 4, 6, 15, 18, 19, 20, 21, 22, 23, 24, 26, 27,
            28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49,
            50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71,
            72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93,
            94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 113,
            114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130,
            131, 132, 133, 134, 135, 136, 137, 138, 139, 141, 142, 143, 144, 145, 147, 148, 149,
            150, 151, 152, 153, 154, 155, 156, 157, 281, 286, 291, 295, 303, 306, 311, 317, 323,
            325, 327, 328, 337, 344, 348, 350, 368, 376, 377, 379, 383, 384, 391, 392, 405, 408,
            412, 413, 425, 430, 431, 436, 449, 463, 466, 472};
    private static final int[] longIDs = {5, 158, 159, 160, 161, 166, 167, 172, 173, 176, 179, 180,
            182, 183, 184, 185, 194, 195, 197, 198, 212, 216, 218, 220, 226, 230, 231, 232, 233,
            234, 235, 237, 238, 239, 240, 241, 245, 247, 248, 249, 250, 251, 252, 253, 255, 259,
            263, 265, 268, 269, 270, 278, 280, 283, 285, 288, 289, 296, 297, 298, 300, 309, 314,
            315, 321, 329, 335, 342, 347, 353, 358, 360, 372, 387, 417, 418, 423, 424, 433, 435,
            454, 455, 461, 462, 464, 473, 478, 479, 480};

    private static ArrayList<Story> allStories;

    private static SharedPreferences sharedPref;

    /**
     * Does all necessary initializations. This is called on start of the app.
     * Currently it loads the stories from stories.xml to the allStories
     * and retrieves the read/favorite status from SharedPreferences.
     */
    public static void loadStoryLists(Context context) {
        sharedPref = getDefaultSharedPreferences(context);
        allStories = new ArrayList<>();
        Resources res = context.getResources();
        TypedArray storiesArray = res.obtainTypedArray(R.array.story_arrays);
        for (int id = 0; id < storiesArray.length(); id++) {
            int arrayResourceId = storiesArray.getResourceId(id, 0);
            TypedArray storyArray = res.obtainTypedArray(arrayResourceId);
            String storyName = storyArray.getString(0);
            @SuppressLint("ResourceType")
            int resourceId = storyArray.getResourceId(1, 0);
            int code = sharedPref.getInt(String.valueOf(id), 0);
            allStories.add(new Story(id, storyName, resourceId, code));
            storyArray.recycle();
        }
        storiesArray.recycle();
    }

    /**
     * Retrieves a random unread story from the list of all available stories
     *
     * @return int ID of a random unread story
     */
    public static int random(Context context) {
        if (allStories == null) {
            loadStoryLists(context);
        }
        ArrayList<Integer> indices = new ArrayList<>();
        for (int id = 0; id < allStories.size(); id++) {
            Story story = allStories.get(id);
            if (!story.isRead()) indices.add(id);
        }
        if (indices.size() == 0) return (int) (Math.random() * allStories.size());
        return indices.get((int) (Math.random() * indices.size()));
    }

    /**
     * Retrieves the last read category
     *
     * @return int resource with the name of the last read category
     */
    public static int lastCategory(){
        return sharedPref.getInt("last_category", R.string.all_stories);
    }

    /**
     * Retrieves the position of the last read story
     *
     * @return int position of the last read story in the last read category
     */
    public static int lastPosition() {
        return sharedPref.getInt("last_position", 0);
    }

    /**
     * Retrieves the story by given ID
     *
     * @param storyID ID of the story to retrieve
     * @return Story the corresponding story
     */
    public static Story getStory(int storyID, Context context) {
        if (allStories == null) {
            loadStoryLists(context);
        }
        return allStories.get(storyID);
    }

    /**
     * Retrieves all stories with given category
     *
     * @param categoryName the ID of the resource with the name of the category.
     * @return ArrayList<Story> all stories in the given category
     */
    public static ArrayList<Story> getStoryList(int categoryName, Context context) {
        if (allStories == null) {
            loadStoryLists(context);
        }
        if (categoryName == R.string.all_stories) return allStories;
        if (categoryName == R.string.favorites) return getFavorites(context);

        ArrayList<Story> categoryStoryList = new ArrayList<>();

        int[] idList;
        switch (categoryName) {
            case R.string.animal_stories:
                idList = animalIDs;
                break;
            case R.string.aesop_s_fables:
                idList = aesopIDs;
                break;
            case R.string.brothers_grimm_stories:
                idList = grimmIDs;
                break;
            case R.string.hans_christian_andersen_s_stories:
                idList = andersenIDs;
                break;
            case R.string.famous_stories:
                idList = famousIDs;
                break;
            case R.string.happy_end_stories:
                idList = happyEndIDs;
                break;
            case R.string.royal_stories:
                idList = royalIDs;
                break;
            case R.string.family_stories:
                idList = familyIDs;
                break;
            case R.string.myth_creatures:
                idList = mythIDs;
                break;
            case R.string.object_stories:
                idList = objectsIDs;
                break;
            case R.string.religious_stories:
                idList = religiousIDs;
                break;
            case R.string.soldier_stories:
                idList = soldierIDs;
                break;
            case R.string.food_for_thought:
                idList = thoughtIDs;
                break;
            case R.string.rudyard_kipling_stories:
                idList = kiplingIDs;
                break;
            case R.string.short_stories:
                idList = shortIDs;
                break;
            case R.string.long_stories:
                idList = longIDs;
                break;
            default:
                return allStories;
        }
        for (int ID : idList) {
            categoryStoryList.add(allStories.get(ID));
        }
        return categoryStoryList;
    }

    /**
     * Retrieves the list of all favorite stories.
     *
     * @return ArrayList<Story> the list of all favorite stories.
     */
    public static ArrayList<Story> getFavorites(Context context) {
        if (allStories == null) {
            loadStoryLists(context);
        }
        ArrayList<Story> favorites = new ArrayList<>();
        for (Story story : allStories) {
            if (story.isFavorite())
                favorites.add(story);
        }
        return favorites;
    }

    /**
     * Marks/un-marks a story as favorite and saves the settings
     *
     * @param ID     int ID of the story
     * @param status boolean true if the story should be favorite, false otherwise
     */
    public static void changeFavoriteStatus(int ID, boolean status, Context context) {
        if (sharedPref == null) {
            loadStoryLists(context);
        }
        Story story = getStory(ID, context);
        int oldCode = story.getCode();
        int newCode = (status) ? oldCode | 2 : oldCode & 1;
        story.setCode(newCode);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(ID), newCode);
        editor.apply();
    }

    /**
     * Marks/un-marks a story as read and saves the settings
     *
     * @param ID     int ID of the story
     * @param status boolean true if the story should be read, false otherwise
     */
    public static void changeReadStatus(int ID, boolean status, Context context) {
        if (sharedPref == null) {
            loadStoryLists(context);
        }
        Story story = getStory(ID, context);
        int oldCode = story.getCode();
        int newCode = (status) ? oldCode | 1 : oldCode & 2;
        story.setCode(newCode);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(String.valueOf(ID), newCode);
        editor.apply();
    }

    /**
     * Sets the last read story to a given id.
     *
     * @param category the resource id of the name of the category which was last read.
     *                 Cannot be "Favorites".
     * @param position the position of the last read story.
     */
    public static void setLastRead(int category, int position, Context context) {
        if (sharedPref == null) {
            loadStoryLists(context);
        }
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("last_position", position);
        editor.putInt("last_category", category);
        editor.apply();
    }

    /**
     * Retrieves the total number of stories.
     *
     * @return int the total number of stories.
     */
    public static int getNumberOfStories(Context context) {
        if (allStories == null) {
            loadStoryLists(context);
        }
        return allStories.size();
    }

    /**
     * Resets status of all stories to not favorite/unread.
     */
    public static void clearData(Context context) {
        if (allStories == null || sharedPref == null) {
            loadStoryLists(context);
        }
        for (Story story : allStories)
            story.setCode(0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

}
