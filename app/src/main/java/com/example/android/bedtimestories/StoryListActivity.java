package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * The class displays all stories within the category passed to it
 *
 * @author Sergei Tsaturian
 */


public class StoryListActivity extends AppCompatActivity {

    private boolean isFirstAppearance;
    private ListView listView;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        if (categoryName == null)
            finish();
        final ArrayList<Story> stories = StoryUtils.getStoryList(categoryName);

        setupActionBar();

        if (stories.isEmpty() && categoryName.equals("Favorites")) {
            setContentView(R.layout.empty_favorites);
            return;
        }
        setContentView(R.layout.activity_storylist);

        StoryAdapter storyAdapter = new StoryAdapter(this, stories);
        listView = findViewById(R.id.story_list);
        listView.setAdapter(storyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoryListActivity.this, StoryActivity.class);
                intent.putExtra("storyID", stories.get(position).getID());
                intent.putExtra("storyList", stories);
                intent.putExtra("categoryName", categoryName);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

        isFirstAppearance = true;
    }

    /**
     * This inflates the custom action bar.
     */
    private void setupActionBar() {
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar, null);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionBarLayout);
        }
        TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText(categoryName);
    }


    /**
     * Redraws the views if navigated back to activity after pause,
     * as the favorite/read status could be changed.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (listView != null && !isFirstAppearance) {
            listView.invalidateViews();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isFirstAppearance = false;
    }

    /**
     * Overrides the "up" button to navigate to correct activity
     * it is needed because we can reach StoryListActivity from both MainActivity and Category Activity
     *
     * @param item the menu item. The behaviour is only overridden if it is the up button.
     * @return boolean true if overridden, default value otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (categoryName.equals("All Stories") || categoryName.equals("Favorites")) {
                Intent intent = new Intent(StoryListActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(StoryListActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
            return true;
        }
        return false;
    }
}
