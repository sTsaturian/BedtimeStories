package com.s_tsat.android.bedtimestories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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

        StoryUtils.loadStoryLists(this);

        TextView allStoriesView = findViewById(R.id.allStoriesView);
        allStoriesView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.all_stories);
            startActivity(intent);
        });

        TextView categoriesView = findViewById(R.id.categoriesView);
        categoriesView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            startActivity(intent);
        });

        TextView favoritesView = findViewById(R.id.favoritesView);
        favoritesView.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.favorites);
            startActivity(intent);
        });

        TextView continueLast = findViewById(R.id.continueLastView);
        continueLast.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            int category = StoryUtils.lastCategory();
            intent.putExtra("categoryName", category);
            intent.putExtra("storyList", StoryUtils.getStoryList(category, this));
            intent.putExtra("index", StoryUtils.lastPosition());
            intent.putExtra("scrollingPosition", StoryUtils.getLastReadScrollingPosition());
            startActivity(intent);
        });

        TextView randomStory = findViewById(R.id.randomStoryView);
        randomStory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            intent.putExtra("index", StoryUtils.random(this));
            intent.putExtra("storyList", StoryUtils.getStoryList(R.string.all_stories,this));
            intent.putExtra("categoryName", R.string.all_stories);
            startActivity(intent);
        });

        TextView clearDataView = findViewById(R.id.clearDataView);
        clearDataView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.do_you_want_to_clear_data)
                    .setTitle(R.string.clear_data);
            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                // clear data, back stack, and restart activity
                StoryUtils.clearData(this);
                Intent newIntent = new Intent(MainActivity.this, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent);
                finish();
            });
            builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
                // do nothing
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    /**
     * Sets up the custom action bar.
     */
    private void setupActionBar() {
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionBarLayout);
            TextView titleView = findViewById(R.id.action_bar_title);
            titleView.setText(R.string.app_name);
        }
    }
}