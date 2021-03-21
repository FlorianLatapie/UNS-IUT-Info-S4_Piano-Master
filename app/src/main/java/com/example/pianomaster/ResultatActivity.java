package com.example.pianomaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultatActivity extends Activity {
    EditText affichage;
    Button envoyer;
    Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoyer_sms);
        affichage = findViewById(R.id.et_contenu_sms);
        retour = findViewById(R.id.b_retour_accueil);
        int score = getIntent().getExtras().getInt("score");
        affichage.setText("J\'ai fait un score de "+score+"/4 sur le niveau 1 dans \"Piano Master\" ! Télécharge l\'appli pour me battre !");

        retour.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sortie);
                animation.setDuration(100);
                retour.startAnimation(animation);
                Intent intent = new Intent(ResultatActivity.this, LevelActivity.class);
                startActivity(intent);
            }
        });
    }
}
