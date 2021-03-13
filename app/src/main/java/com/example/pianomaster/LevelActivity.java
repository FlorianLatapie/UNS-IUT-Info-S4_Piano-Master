package com.example.pianomaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class LevelActivity extends Activity {
    private List<Button> listButtonLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_niveau);
        initButtonsLevel();

    }

    public void initButtonsLevel(){
        listButtonLevel.add(findViewById(R.id.b_niv_1));
        listButtonLevel.add(findViewById(R.id.b_niv_2));
        listButtonLevel.add(findViewById(R.id.b_niv_3));
        listButtonLevel.add(findViewById(R.id.b_niv_4));
        listButtonLevel.add(findViewById(R.id.b_niv_5));
        listButtonLevel.add(findViewById(R.id.b_niv_6));
    }
}
