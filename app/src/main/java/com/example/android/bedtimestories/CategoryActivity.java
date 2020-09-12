package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * The class displays the list of available categories
 *
 * @author Sergei Tsaturian
 */

public class CategoryActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(
                R.layout.action_bar,null);
        // Set up your ActionBar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);

        TextView titleView = findViewById(R.id.action_bar_title);
        titleView.setText("Categories");

        TextView animalView = findViewById(R.id.animalsCategory);
        animalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Animal Stories");
                startActivity(intent);
            }
        });

        TextView kidsView = findViewById(R.id.kidsCategory);
        kidsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Kid Stories");
                startActivity(intent);
            }
        });
    }
}
