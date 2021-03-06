package com.s_tsat.android.bedtimestories;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * This activity displays a single story on the screen
 *
 * @author Sergei Tsaturian
 */

public class StoryActivity extends AppCompatActivity {

    private static final int DELAY_FOR_READ = 10000;
    private static final int DELAY_FOR_LAST = 5000;
    private Handler mHandler;
    private ArrayList<Story> storyList;
    private TextView titleView;
    private TextView storyNameView;
    private TextView storyTextView;
    private ToggleButton favButton;
    private Button unreadButton;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private ScrollView scrollView;
    private int storyID;
    private int categoryName;
    int position;
    private boolean isSetupPhase;
    private boolean isLastRead;

    /**
     * this method is called on creation. It sets up the action bar,
     * initializes the member variables and then calls fillViews()
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        setupActionBar();

        int scrollingPosition;
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            categoryName = intent.getIntExtra("categoryName", -1);
            if (categoryName == -1) StoryUtils.loadStoryLists(this);
            storyList = (ArrayList<Story>) intent.getSerializableExtra("storyList");
            position = intent.getIntExtra("index", -1);
            storyID = storyList.get(position).getID();
            scrollingPosition = intent.getIntExtra("scrollingPosition", 0);
        }
        else{
            storyID = savedInstanceState.getInt("storyID", -1);
            categoryName = savedInstanceState.getInt("categoryName");
            position = savedInstanceState.getInt("position");
            storyList = (ArrayList<Story>) savedInstanceState.getSerializable("storyList");
            scrollingPosition = -1;
        }

        findViews();

        mHandler = new Handler(Looper.getMainLooper());

        fillViews(storyID, position, scrollingPosition);
    }

    /**
     * This inflates the custom action bar.
     */
    private void setupActionBar() {
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.story_action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(actionBarLayout, layoutParams);
        }
    }

    /**
     * This sets the member variables for views.
     */
    private void findViews() {
        titleView = findViewById(R.id.action_bar_title);
        storyNameView = findViewById(R.id.storyNameView);
        storyTextView = findViewById(R.id.storyTextView);
        favButton = findViewById(R.id.favoriteToggle);
        unreadButton = findViewById(R.id.unreadButton);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);
        scrollView = findViewById(R.id.scrollView);
    }

    /**
     * This method is called after creation and every time the storyID changes.
     * It sets the content to display, listeners, and timers to mark the story read/unread
     *
     * @param id  the story ID
     * @param pos position of the story in the underlying story list
     */
    private void fillViews(final int id, final int pos, int scrollPos) {
        isSetupPhase = true;
        storyID = id;
        final Story story = StoryUtils.getStory(id, this);
        storyTextView.setText(getString(story.getResourceID()));
        titleView.setText(story.getName());
        storyNameView.setText(story.getName());
        if (scrollPos != -1)
            scrollView.post(() -> scrollView.scrollTo(0, scrollPos));
        isLastRead = false;

        favButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                StoryUtils.changeFavoriteStatus(id, true, this);
                if (!isSetupPhase) showToast("Story added to favorites");
            } else {
                StoryUtils.changeFavoriteStatus(id, false, this);
                if (!isSetupPhase) showToast("Story removed from favorites");
            }
        });

        favButton.setChecked(story.isFavorite());

        unreadButton.setOnClickListener(v -> {
            if (story.isRead()) {
                StoryUtils.changeReadStatus(id, false, this);
                unreadButton.setText("Mark\nRead");
                if (!isSetupPhase) showToast("Story marked as unread");
            } else {
                StoryUtils.changeReadStatus(id, true, this);
                unreadButton.setText("Mark\nUnread");
                if (!isSetupPhase) showToast("Story marked as read");
            }
        });

        if (story.isRead()) {
            unreadButton.setVisibility(View.VISIBLE);
            unreadButton.setText("Mark\nUnread");
        } else {
            unreadButton.setVisibility(View.INVISIBLE);
            unreadButton.setText("Mark\nRead");
        }

        isSetupPhase = false;

        if (storyList != null) {
            leftButton.setOnClickListener(v -> {
                if (isLastRead) StoryUtils.setLastReadScrollingPosition(scrollView.getScrollY());
                position = (pos + storyList.size() - 1) % storyList.size();
                Story newStory = storyList.get(position);
                int newID = newStory.getID();
                fillViews(newID, position, 0);
            });
            rightButton.setOnClickListener(v -> {
                if (isLastRead) StoryUtils.setLastReadScrollingPosition(scrollView.getScrollY());
                position = (pos + 1) % storyList.size();
                Story newStory = storyList.get(position);
                int newID = newStory.getID();
                fillViews(newID, position, 0);
            });
        } else {
            leftButton.setOnClickListener(v -> {
                if (isLastRead) StoryUtils.setLastReadScrollingPosition(scrollView.getScrollY());
                int numOfStories = StoryUtils.getNumberOfStories(this);
                int newID = (id + numOfStories - 1) % numOfStories;
                fillViews(newID, -1, 0);
            });
            rightButton.setOnClickListener(v -> {
                if (isLastRead) StoryUtils.setLastReadScrollingPosition(scrollView.getScrollY());
                int numOfStories = StoryUtils.getNumberOfStories(this);
                int newID = (id + 1) % numOfStories;
                fillViews(newID, -1, 0);
            });
        }

        resetTimers();
    }

    /**
     * This stops the timers and remembers the last read position on pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (isLastRead) StoryUtils.setLastReadScrollingPosition(scrollView.getScrollY());
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * This starts the timers on resume.
     */
    @Override
    protected void onResume() {
        super.onResume();
        resetTimers();
    }

    /**
     * This resets the timers for marking the story read / last read.
     */
    private void resetTimers() {
        mHandler.removeCallbacksAndMessages(null);
        Story story = StoryUtils.getStory(storyID, this);
        if (categoryName == R.string.favorites)
            mHandler.postDelayed(() -> {
                StoryUtils.setLastRead(R.string.all_stories, storyID, this);
                isLastRead = true;
            }, DELAY_FOR_LAST)
            ;
        else
            mHandler.postDelayed(() -> {
                StoryUtils.setLastRead(categoryName, position, this);
                isLastRead = true;
                }, DELAY_FOR_LAST);
        if (!story.isRead() && !unreadButton.isShown()) mHandler.postDelayed(() -> {
            StoryUtils.changeReadStatus(storyID, true, this);
            unreadButton.setText("Mark\nUnread");
            unreadButton.setVisibility(View.VISIBLE);
        }, DELAY_FOR_READ);
    }

    /**
     * This method shows the toast with given text in the top right corner
     *
     * @param text the text to show
     */
    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.END, 16, 128);
        toast.setText(text);
        toast.show();
    }

    /**
     * overrides the "up" button to navigate to StoryList activity with correct category
     * it is needed because we can reach StoryActivity from both MainActivity and StoryList Activity
     *
     * @param item the menu item. we only override if the item is the "up" button
     * @return boolean true if overridden, default value otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent = new Intent(StoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", categoryName);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("storyID", storyID);
        outState.putInt("categoryName", categoryName);
        outState.putInt("position", position);
        outState.putSerializable("storyList", storyList);
    }
}
