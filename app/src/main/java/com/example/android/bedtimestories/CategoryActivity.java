package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        TextView animalView = findViewById(R.id.animalsCategory);
        animalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Animals");
                startActivity(intent);
            }
        });

        TextView kidsView = findViewById(R.id.kidsCategory);
        kidsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, StoryListActivity.class);
                intent.putExtra("categoryName", "Kids");
                startActivity(intent);
            }
        });
    }
}
