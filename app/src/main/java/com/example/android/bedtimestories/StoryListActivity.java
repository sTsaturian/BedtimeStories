package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * The class displays all stories within the category passed to it
 *
 * @author Sergei Tsaturian
 */


public class StoryListActivity extends AppCompatActivity {

    private boolean mFirstAppearance;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storylist);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");


        final ArrayList<Story> stories = StoryUtils.getStoryList(categoryName);

        StoryAdapter storyAdapter = new StoryAdapter(this, stories);
        listView = findViewById(R.id.story_list);
        listView.setAdapter(storyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoryListActivity.this, StoryActivity.class);
                intent.putExtra("storyID", stories.get(position).getID());
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
        if (!mFirstAppearance) {
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
}
