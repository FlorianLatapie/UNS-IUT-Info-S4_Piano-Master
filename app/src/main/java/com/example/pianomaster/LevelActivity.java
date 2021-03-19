package com.example.pianomaster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class LevelActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_niveau);
        GridView gridview = findViewById(R.id.gridView);
        gridview.setAdapter(new LevelAdapter(this));
    }
}
