package com.example.pianomaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity implements IntActivityClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_score, new FragmentScore()).commit();
        }
    }

    @Override
    public void onButtonClicked(String num) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_sms, new SMSActivity(num)).commit();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sp = getSharedPreferences("score", Activity.MODE_PRIVATE);
        SharedPreferences sp2 = getSharedPreferences("niveau", Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("getNiveau", 0);
        editor.apply();

        SharedPreferences.Editor editor2 = sp2.edit();
        editor2.putInt("getScore", 0);
        editor2.apply();
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra("numNiveau", 0);
        startActivity(intent);
    }
}
