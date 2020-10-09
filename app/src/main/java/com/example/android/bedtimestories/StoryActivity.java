package com.example.android.bedtimestories;

import android.content.Intent;
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
    private String categoryName;
    private boolean isSetupPhase;

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

        Intent intent = getIntent();
        storyID = intent.getIntExtra("storyID", 0);
        categoryName = intent.getStringExtra("categoryName");
        storyList = (ArrayList<Story>) intent.getSerializableExtra("storyList");
        int position = intent.getIntExtra("index", -1);

        findViews();

        mHandler = new Handler(Looper.getMainLooper());

        fillViews(storyID, position);
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
    private void fillViews(final int id, final int pos) {
        isSetupPhase = true;
        storyID = id;
        final Story story = StoryUtils.getStory(id);
        storyTextView.setText(getString(story.getResourceID()));
        titleView.setText(story.getName());
        storyNameView.setText(story.getName() + " " + storyID);
        scrollView.scrollTo(0, 0);

        favButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                StoryUtils.changeFavoriteStatus(id, true);
                if (!isSetupPhase) showToast("Story added to favorites");
                Log.i("StoryActivity", "Changed story " + id + " to favorite");
            } else {
                StoryUtils.changeFavoriteStatus(id, false);
                if (!isSetupPhase) showToast("Story removed from favorites");
                Log.i("StoryActivity", "Changed story " + id + " to not favorite");
            }
        });

        if (story.isFavorite()) {
            Log.i("StoryActivity", "Story " + story.getID() + " is favorite, so check the favorite button");
            favButton.setChecked(true);
        } else {
            Log.i("StoryActivity", "Story " + story.getID() + " is not favorite, so uncheck the favorite button");
            favButton.setChecked(false);
        }

        unreadButton.setOnClickListener(v -> {
            if (story.isRead()) {
                StoryUtils.changeReadStatus(id, false);
                unreadButton.setText("Mark\nRead");
                if (!isSetupPhase) showToast("Story marked as unread");
            } else {
                StoryUtils.changeReadStatus(id, true);
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
                int newPosition = (pos + storyList.size() - 1) % storyList.size();
                Story newStory = storyList.get(newPosition);
                int newID = newStory.getID();
                fillViews(newID, newPosition);
            });
            rightButton.setOnClickListener(v -> {
                int newPosition = (pos + 1) % storyList.size();
                Story newStory = storyList.get(newPosition);
                int newID = newStory.getID();
                fillViews(newID, newPosition);
            });
        } else {
            leftButton.setOnClickListener(v -> {
                int numOfStories = StoryUtils.getNumberOfStories();
                int newID = (id + numOfStories - 1) % numOfStories;
                fillViews(newID, -1);
            });
            rightButton.setOnClickListener(v -> {
                int numOfStories = StoryUtils.getNumberOfStories();
                int newID = (id + 1) % numOfStories;
                fillViews(newID, -1);
            });
        }

        resetTimers();
    }

    /**
     * This stops the timers on pause.
     */
    @Override
    protected void onPause() {
        super.onPause();
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
        Story story = StoryUtils.getStory(storyID);
        mHandler.postDelayed(() -> StoryUtils.setLastRead(storyID), DELAY_FOR_LAST);
        if (!story.isRead() && !unreadButton.isShown()) mHandler.postDelayed(() -> {
            StoryUtils.changeReadStatus(storyID, true);
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
}
