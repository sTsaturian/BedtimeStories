package com.example.android.bedtimestories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main activity that shows up when the app is started
 *
 * @author Sergei Tsaturian
 */


public class MainActivity extends AppCompatActivity {

    // loads data in the memory and displays the main buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar,null);
        // Set up your ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText("Bedtime Stories");

        Button allStoriesView = findViewById(R.id.allStories);
        allStoriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "All Stories");
                startActivity(intent);
            }
        });

        Button categoriesView = findViewById(R.id.categoriesButton);
        categoriesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        Button favoritesView = findViewById(R.id.favoritesButton);
        favoritesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Favorites");
                startActivity(intent);
            }
        });

        Button continueLast = findViewById(R.id.continueLastButton);
        continueLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                intent.putExtra("storyID", StoryUtils.last());
                startActivity(intent);
            }
        });

        Button randomStory = findViewById(R.id.randomStoryButton);
        randomStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StoryActivity.class);
                intent.putExtra("storyID", StoryUtils.random());
                startActivity(intent);
            }
        });
    }
}