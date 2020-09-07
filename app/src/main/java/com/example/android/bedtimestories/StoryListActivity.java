package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storylist);

        Intent intent = getIntent();
        String categoryName = intent.getStringExtra("categoryName");


        final ArrayList<Story> stories = StoryUtils.getStoryList(categoryName);

        StoryAdapter storyAdapter = new StoryAdapter(this, stories);
        ListView listView = (ListView) findViewById(R.id.story_list);
        listView.setAdapter(storyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StoryListActivity.this, StoryActivity.class);
                intent.putExtra("storyID", stories.get(position).getID());
                startActivity(intent);
            }
        });
    }
}
