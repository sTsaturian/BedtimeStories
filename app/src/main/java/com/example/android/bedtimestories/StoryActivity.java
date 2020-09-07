package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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
        int storyID = intent.getIntExtra("storyID", 0);
        Story story = StoryUtils.getStory(storyID);

        TextView nameView = findViewById(R.id.storyNameView);
        nameView.setText(story.getName());

        TextView storyTextView = findViewById(R.id.storyTextView);
        storyTextView.setText(getString(story.getResourceID()));
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
