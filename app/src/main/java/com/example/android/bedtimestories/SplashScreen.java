package com.example.android.bedtimestories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Log.i("SplashScreen","Loading lists");
        StoryUtils.loadStoryLists(this);
        Log.i("SplashScreen", "Loaded lists");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
