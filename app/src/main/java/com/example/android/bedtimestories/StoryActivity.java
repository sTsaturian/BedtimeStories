package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
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
    private final int DELAY_FOR_LAST = 5000;
    private Handler mHandler;
    private ArrayList<Story> storyList;
    private TextView titleView;
    private TextView storyNameView;
    private TextView storyTextView;
    private ToggleButton favButton;
    private Button unreadButton;
    private ImageButton leftButton;
    private ImageButton rightButton;
    private int storyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.story_action_bar,null);
        // Set up your ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(actionBarLayout, layoutParams);

        Intent intent = getIntent();
        storyID = intent.getIntExtra("storyID", 0);
        storyList = (ArrayList<Story>) intent.getSerializableExtra("storyList");
        int position = intent.getIntExtra("index", -1);

        titleView = findViewById(R.id.action_bar_title);
        storyNameView = findViewById(R.id.storyNameView);
        storyTextView = findViewById(R.id.storyTextView);
        favButton = findViewById(R.id.favoriteToggle);
        unreadButton = findViewById(R.id.unreadButton);
        leftButton = findViewById(R.id.left_button);
        rightButton = findViewById(R.id.right_button);

        mHandler = new Handler(Looper.getMainLooper());

        fillViews(storyID, position);
    }

    private void fillViews(final int id, final int pos){
        storyID = id;
        final Story story = StoryUtils.getStory(id);
        storyTextView.setText(getString(story.getResourceID()));
        titleView.setText(story.getName());
        storyNameView.setText(story.getName());

        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StoryUtils.changeFavoriteStatus(id, true);
                    Log.i("StoryActivity", "Changed story "+id + " to favorite");
                }
                else {
                    StoryUtils.changeFavoriteStatus(id, false);
                    Log.i("StoryActivity", "Changed story "+id + " to not favorite");
                }
            }
        });
        if (story.isFavorite()){
            Log.i("StoryActivity", "Story " + story.getID() +" is favorite, so check the favorite button");
            favButton.setChecked(true);
        }
        else {
            Log.i("StoryActivity", "Story " + story.getID() +" is not favorite, so uncheck the favorite button");
            favButton.setChecked(false);
        }


        if (story.isRead()){
            unreadButton.setVisibility(View.VISIBLE);
        }
        else{
            unreadButton.setVisibility(View.INVISIBLE);
        }

        unreadButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                markAsUnread(id);
            }
        });

        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAsLast(id);
            }
        }, DELAY_FOR_LAST);
        if (!story.isRead()) mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                markAsRead(id);
                unreadButton.setVisibility(View.VISIBLE);
            }
        }, DELAY_FOR_READ);

        if (storyList != null){
            leftButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int newPosition = (pos + storyList.size() - 1) % storyList.size();
                    Story newStory = storyList.get(newPosition);
                    int newID = newStory.getID();
                    fillViews(newID, newPosition);
                }
            });
            rightButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int newPosition = (pos + 1) % storyList.size();
                    Story newStory = storyList.get(newPosition);
                    int newID = newStory.getID();
                    fillViews(newID, newPosition);
                }
            });
        }
        else{
            leftButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int numOfStories = StoryUtils.getNumberOfStories();
                    int newID = (id + numOfStories - 1) % numOfStories;
                    fillViews(newID, -1);
                }
            });
            rightButton.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int numOfStories = StoryUtils.getNumberOfStories();
                    int newID = (id + 1) % numOfStories;
                    fillViews(newID, -1);
                }
            });
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Story story = StoryUtils.getStory(storyID);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setAsLast(storyID);
            }
        }, DELAY_FOR_LAST);
        if (!story.isRead()) mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                markAsRead(storyID);
                unreadButton.setVisibility(View.VISIBLE);
            }
        }, DELAY_FOR_READ);
    }

    private void setAsLast(int storyID){
        StoryUtils.setLastRead(storyID);
    }

    private void markAsRead(int storyID){
        StoryUtils.changeReadStatus(storyID, true);
    }

    private void markAsUnread(int storyID){
        StoryUtils.changeReadStatus(storyID, false);
    }


    // this makes the "up" button behave in the same way as the "back" button,
    // it is needed because we can reach StoryActivity from both MainActivity and StoryList Activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
