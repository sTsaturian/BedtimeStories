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

        setupActionBar();

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

        TextView aesopView = findViewById(R.id.aesopCategory);
        aesopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Aesop's Fables");
                startActivity(intent);
            }
        });

        TextView grimmView = findViewById(R.id.grimmCategory);
        grimmView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Brothers Grimm Stories");
                startActivity(intent);
            }
        });

        TextView andersenView = findViewById(R.id.andersenCategory);
        andersenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Hans Christian Andersen's Stories");
                startActivity(intent);
            }
        });
    }

    /**
     * Sets up the custom action bar.
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
        titleView.setText(R.string.categories);
    }
}
