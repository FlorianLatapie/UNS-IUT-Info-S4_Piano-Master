package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PianoActivity extends Activity {
    private ProgressDialog pDialog;

    private static int count = 0;

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

    private MediaPlayer currentSong = new MediaPlayer();

    private List<String> listNote = new ArrayList<>();
    private int numNote = 0;

    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    QuestionPiano question;
    TextView tvQuestion, tvNiveau;
    private int nbTentative = 0;

    Intent intent;
    private List<QuestionPiano> questionPianoList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_piano);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        listNote.add("do");
        listNote.add("re");
        listNote.add("mi");
        listNote.add("do_diese");


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
        Button btn_reecoutez = findViewById(R.id.b_recommencer_piano);
        tvNiveau = findViewById(R.id.tv_titre_niveau_piano);
        tvQuestion = findViewById(R.id.tv_question_piano);

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

        //question = getIntent().getExtras().getParcelable("questionPiano");
        //tvQuestion.setText(question.getTitre());
        //tvNiveau.setText("Niveau piano "+question.getNumQuestion());

        intent = new Intent(PianoActivity.this, PianoActivity.class);
        if(count>1){
            Intent intent = new Intent(PianoActivity.this, CreerQuestionActivity.class);
            intent.putExtra("nbQuestion", "4");
            startActivity(intent);
        }
        else{
            questionPianoList = getIntent().getParcelableArrayListExtra("listQuestionPiano");
            new GetRessources().execute();
            question = questionPianoList.get(count);
            tvQuestion.setText(question.getTitre());
            tvNiveau.setText("Niveau 1"+ " - Question " + question.getNumQuestion());
        }

        btn_do.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_do.start();
                checkNote("do");
            }
        });

        btn_do_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_do_diese.start();
                checkNote("do_diese");
            }
        });
        btn_re.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_re.start();
                checkNote("re");
            }
        });
        btn_re_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_re_diese.start();
                checkNote("re_diese");
            }
        });
        btn_mi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_mi.start();
                checkNote("mi");
            }
        });
        btn_fa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_fa.start();
                checkNote("fa");
            }
        });
        btn_fa_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_fa_diese.start();
                checkNote("fa_diese");
            }
        });
        btn_sol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_sol.start();
                checkNote("sol");
            }
        });
        btn_sol_diese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_sol_diese.start();
                checkNote("sol_diese");
            }
        });
        btn_la.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_la.start();
                checkNote("la");
            }
        });
        btn_si_bemol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_si_bemol.start();
                checkNote("si_bemol");
            }
        });
        btn_si.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                media_si.start();
                checkNote("si");
            }
        });

        btn_reecoutez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSong.start();
            }
        });

        new PianoActivity.GetRessources().execute();
    }

    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PianoActivity.this);
            pDialog.setMessage("Téléchargement en cours...\nIl est possible que cela prenne 30s à 2 minutes");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched");
            try {
                String url = "https://androidpianomaster.000webhostapp.com/ressources/Niveau1/1.wav";
               // String url = question.getUrl()+ question.getIdAudio(); //"https://androidpianomaster.000webhostapp.com/ressources/Niveau1/1.wav"
                currentSong.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                );
                currentSong.setDataSource(url);
                currentSong.prepare();
                currentSong.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();
        }


    }

    public void checkNote(String note){
        if(tvQuestion.getText().equals("IL FAUT RECOMMENCER !")) tvQuestion.setText("ECOUTEZ ET JOUEZ !");
        if(numNote <= listNote.size()-1 && listNote.get(numNote).equals(note)) {
            System.out.println("good");
            numNote++;
            if(numNote == 4)
                System.out.println("termine");
        }
        else {
            tvQuestion.setText("IL FAUT RECOMMENCER !");
            nbTentative++;
            if (nbTentative == 3) System.out.println("Trop nul question suivante") ; // passe a la question suivante
            numNote = 0;
        }
    }
}
