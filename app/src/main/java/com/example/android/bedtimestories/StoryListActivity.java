package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.ArrayList;

/**
 * The class displays all stories within the category passed to it
 *
 * @author Sergei Tsaturian
 */


public class StoryListActivity extends AppCompatActivity {

    private boolean mFirstAppearance;
    private ListView listView;
    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("categoryName");
        final ArrayList<Story> stories = StoryUtils.getStoryList(categoryName);

        if (stories.isEmpty() && categoryName.equals("Favorites")){
            setContentView(R.layout.empty_favorites);
            return;
        }
        setContentView(R.layout.activity_storylist);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar,null);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText(categoryName);

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
        Log.i("StoryListActivity", "Create");

        mFirstAppearance = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("StoryListActivity", "Start");
        if (listView != null && !mFirstAppearance) {
            listView.invalidateViews();
            Log.i("StoryListActivity", "Refreshed");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("StoryListActivity", "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("StoryListActivity", "Destroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("StoryListActivity", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirstAppearance = false;
        Log.i("StoryListActivity", "Pause");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            if (categoryName.equals("All Stories") || categoryName.equals("Favorites")){
                Intent intent = new Intent(StoryListActivity.this, MainActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(StoryListActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
            return true;
        }
        return false;
    }
}
