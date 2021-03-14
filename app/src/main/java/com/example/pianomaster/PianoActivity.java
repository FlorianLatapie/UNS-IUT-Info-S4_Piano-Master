package com.example.pianomaster;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PianoActivity extends Activity {
    private MediaPlayer media_do;
    private MediaPlayer media_do_diese;
    private MediaPlayer media_re;
    private MediaPlayer media_re_diese;
    private MediaPlayer media_mi;
    private MediaPlayer media_fa;
    private MediaPlayer media_fa_diese;
    private MediaPlayer media_sol;
    private MediaPlayer media_sol_diese;
    private MediaPlayer media_la;
    private MediaPlayer media_si_bemol;
    private MediaPlayer media_si;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_piano);
        Button btn_do = findViewById(R.id.b_do);
        Button btn_do_diese = findViewById(R.id.b_do_diese);
        Button btn_re = findViewById(R.id.b_re);
        Button btn_re_diese = findViewById(R.id.b_re_diese);
        Button btn_mi = findViewById(R.id.b_mi);
        Button btn_fa = findViewById(R.id.b_fa);
        Button btn_fa_diese = findViewById(R.id.b_fa_diese);
        Button btn_sol = findViewById(R.id.b_sol);
        Button btn_sol_diese = findViewById(R.id.b_sol_diese);
        Button btn_la = findViewById(R.id.b_la);
        Button btn_si_bemol = findViewById(R.id.b_si_bemol);
        Button btn_si = findViewById(R.id.b_si);

        media_do = MediaPlayer.create(getApplicationContext(), R.raw.note_do);
        media_do_diese = MediaPlayer.create(getApplicationContext(), R.raw.do_diese);
        media_re = MediaPlayer.create(getApplicationContext(), R.raw.re);
        media_re_diese = MediaPlayer.create(getApplicationContext(), R.raw.re_diese);
        media_mi = MediaPlayer.create(getApplicationContext(), R.raw.mi);
        media_fa = MediaPlayer.create(getApplicationContext(), R.raw.fa);
        media_fa_diese = MediaPlayer.create(getApplicationContext(), R.raw.fa_diese);
        media_sol = MediaPlayer.create(getApplicationContext(), R.raw.sol);
        media_sol_diese = MediaPlayer.create(getApplicationContext(), R.raw.sol_diese);
        media_la = MediaPlayer.create(getApplicationContext(), R.raw.la);
        media_si_bemol = MediaPlayer.create(getApplicationContext(), R.raw.si_bemol);
        media_si = MediaPlayer.create(getApplicationContext(), R.raw.si);



        btn_do.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_do.start();
            }
        });

        btn_do_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_do_diese.start();
            }
        });
        btn_re.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_re.start();
            }
        });
        btn_re_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_re_diese.start();
            }
        });
        btn_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_mi.start();
            }
        });
        btn_fa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_fa.start();
            }
        });
        btn_fa_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_fa_diese.start();
            }
        });
        btn_sol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_sol.start();
            }
        });
        btn_sol_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_sol_diese.start();
            }
        });
        btn_la.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_la.start();
            }
        });
        btn_si_bemol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_si_bemol.start();
            }
        });
        btn_si.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_si.start();
            }
        });
    }
}
