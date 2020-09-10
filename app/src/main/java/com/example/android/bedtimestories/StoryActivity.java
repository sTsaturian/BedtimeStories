package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity displays a single story on the screen
 *
 * @author Sergei Tsaturian
 */

public class StoryActivity extends AppCompatActivity {

    private static final int DELAY_FOR_READ = 10000;
    private final int DELAY_FOR_LAST = 5000;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        final int storyID = intent.getIntExtra("storyID", 0);
        Story story = StoryUtils.getStory(storyID);

        TextView nameView = findViewById(R.id.storyNameView);
        nameView.setText(story.getName());

        TextView storyTextView = findViewById(R.id.storyTextView);
        storyTextView.setText(getString(story.getResourceID()));

        final ToggleButton favButton = findViewById(R.id.favoriteToggle);
        if (story.isFavorite()){
            favButton.setChecked(true);
        }
        else {
            favButton.setChecked(false);
        }
        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    StoryUtils.changeFavoriteStatus(storyID, true);
                }
                else {
                    StoryUtils.changeFavoriteStatus(storyID, false);
                }
            }
        });

        final Button unreadButton = findViewById(R.id.unreadButton);
        if (story.isRead()){
            unreadButton.setVisibility(View.VISIBLE);
        }
        unreadButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                markAsUnread(storyID);
            }
        })
;

        mHandler = new Handler(Looper.getMainLooper());
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

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
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
