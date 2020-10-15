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

        TextView aesopView = findViewById(R.id.aesopCategory);
        aesopView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.aesop_s_fables);
            startActivity(intent);
        });

        TextView andersenView = findViewById(R.id.andersenCategory);
        andersenView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.hans_christian_andersen_s_stories);
            startActivity(intent);
        });

        TextView grimmView = findViewById(R.id.grimmCategory);
        grimmView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.brothers_grimm_stories);
            startActivity(intent);
        });

        TextView kiplingView = findViewById(R.id.kiplingCategory);
        kiplingView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName", R.string.rudyard_kipling_stories);
            startActivity(intent);
        });

        TextView animalView = findViewById(R.id.animalsCategory);
        animalView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.animal_stories);
            startActivity(intent);
        });

        TextView famousView = findViewById(R.id.famousCategory);
        famousView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.famous_stories);
            startActivity(intent);
        });

        TextView happyView = findViewById(R.id.happyEndCategory);
        happyView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.happy_end_stories);
            startActivity(intent);
        });

        TextView royalView = findViewById(R.id.royalCategory);
        royalView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.royal_stories);
            startActivity(intent);
        });

        TextView familyView = findViewById(R.id.familyCategory);
        familyView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.family_stories);
            startActivity(intent);
        });

        TextView mythView = findViewById(R.id.mythCategory);
        mythView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.myth_creatures);
            startActivity(intent);
        });

        TextView objectsView = findViewById(R.id.objectsCategory);
        objectsView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.object_stories);
            startActivity(intent);
        });

        TextView religiousView = findViewById(R.id.religiousCategory);
        religiousView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.religious_stories);
            startActivity(intent);
        });

        TextView soldierView = findViewById(R.id.soldierCategory);
        soldierView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.soldier_stories);
            startActivity(intent);
        });

        TextView thoughtView = findViewById(R.id.thoughtCategory);
        thoughtView.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
            intent.putExtra("categoryName",R.string.food_for_thought);
            startActivity(intent);
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
