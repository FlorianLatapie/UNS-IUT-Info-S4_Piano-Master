package com.example.pianomaster;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreActivity extends AppCompatActivity implements IntActivityClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_score, new FragmentSms()).commit();
        }
    }

    @Override
    public void onButtonClicked(String num) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_sms, new SMSActivity(num)).commit();
    }
}
