package com.example.android.bedtimestories;

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
            intent.putExtra("categoryName", "All Stories");
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
            intent.putExtra("categoryName", "Favorites");
            startActivity(intent);
        });

        TextView continueLast = findViewById(R.id.continueLastView);
        continueLast.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            intent.putExtra("storyID", StoryUtils.last());
            intent.putExtra("categoryName", "All Stories");
            startActivity(intent);
        });

        TextView randomStory = findViewById(R.id.randomStoryView);
        randomStory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
            intent.putExtra("storyID", StoryUtils.random());
            intent.putExtra("categoryName", "All Stories");
            startActivity(intent);
        });

        TextView clearDataView = findViewById(R.id.clearDataView);
        clearDataView.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.do_you_want_to_clear_data)
                    .setTitle(R.string.clear_data);
            builder.setPositiveButton(R.string.ok, (dialog, id) -> {
                // clear data, back stack, and restart activity
                StoryUtils.clearData();
                Intent newIntent = new Intent(MainActivity.this, MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent);
                finish();
            });
            builder.setNegativeButton("Cancel", (dialog, id) -> {
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
        ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
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