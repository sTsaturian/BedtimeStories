package com.example.android.bedtimestories;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The class displays a single story on the screen
 *
 * @author Sergei Tsaturian
 */

public class StoryActivity extends AppCompatActivity {

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
            favButton.setBackgroundColor(Color.YELLOW);
        }
        else {
            favButton.setBackgroundColor(Color.WHITE);
        }
        favButton.setBackgroundColor(Color.WHITE);
        favButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    favButton.setBackgroundColor(Color.YELLOW);
                    StoryUtils.changeFavoriteStatus(storyID, true);
                }
                else {
                    favButton.setBackgroundColor(Color.WHITE);
                    StoryUtils.changeFavoriteStatus(storyID, false);
                }
            }
        });
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
