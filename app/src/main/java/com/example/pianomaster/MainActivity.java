package com.example.pianomaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.b_jouer);
        playButton.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sortie);
            animation.setDuration(100);
            playButton.startAnimation(animation);

            Intent intent = new Intent(MainActivity.this, LevelActivity.class);
            startActivity(intent);
        });
    }
}