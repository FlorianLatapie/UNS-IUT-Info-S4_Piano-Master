package com.example.pianomaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class LevelActivity extends Activity {
    private Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_niveau);
        GridView gridview = findViewById(R.id.gridView);
        gridview.setAdapter(new LevelAdapter(this));

        Button b_sms_a_supprimer  = findViewById(R.id.b_sms_a_supprimer);
        b_sms_a_supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SMSActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
