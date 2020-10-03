package com.example.android.bedtimestories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Main activity that shows up when the app is started
 *
 * @author Sergei Tsaturian
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupActionBar();

        TextView allStoriesView = findViewById(R.id.allStoriesView);
        allStoriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "All Stories");
                startActivity(intent);
            }
        });

        TextView categoriesView = findViewById(R.id.categoriesView);
        categoriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        TextView favoritesView = findViewById(R.id.favoritesView);
        favoritesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Favorites");
                startActivity(intent);
            }
        });

        TextView continueLast = findViewById(R.id.continueLastView);
        continueLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                intent.putExtra("storyID", StoryUtils.last());
                intent.putExtra("categoryName", "All Stories");
                startActivity(intent);
            }
        });

        TextView randomStory = findViewById(R.id.randomStoryView);
        randomStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                intent.putExtra("storyID", StoryUtils.random());
                intent.putExtra("categoryName", "All Stories");
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the custom action bar.
     */
    private void setupActionBar() {
        ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionBarLayout);
            TextView titleView = findViewById(R.id.action_bar_title);
            titleView.setText(R.string.bedtime_stories);
        }
    }
}