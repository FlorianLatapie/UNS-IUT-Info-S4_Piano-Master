package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Question4RepActivity extends Activity {
    private final String TAG = MainActivity.class.getSimpleName();
    private static int count = 0;
    private static int nbPoint = 0;
    private static final long delaiEntreChaqueQuestion = 3000;

    private int niveau;
    private MediaPlayer sonRep;

    private ProgressDialog pDialog;


    private static final String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    Button b1, b2, b3, b4; // boutons de réponse
    TextView tvQuestion, tvNiveau, tvScore, tvNumQuestion;
    ProgressBar pgCircle;
    ImageView ivQuestion;
    QuestionMultiple question;
    Drawable d;
    Intent intent;
    private List<QuestionMultiple> questionMultipleList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_4reponses);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        b1 = findViewById(R.id.b_rep_1);
        b2 = findViewById(R.id.b_rep_2);
        b3 = findViewById(R.id.b_rep_3);
        b4 = findViewById(R.id.b_rep_4);
        tvQuestion = findViewById(R.id.tv_question_q4rep);
        tvNiveau = findViewById(R.id.tv_titre_niveau_q4rep);
        ivQuestion = findViewById(R.id.iv_question_q4rep);
        tvScore = findViewById(R.id.tv_score_q4rep);
        tvNumQuestion = findViewById(R.id.tv_num_question_q4rep);
        pgCircle = findViewById(R.id.progressQuestion);
        intent = new Intent(Question4RepActivity.this, Question4RepActivity.class);

        if(count>1){
            tvScore.setText(nbPoint+"/4");
            count = 0;
            Intent intent = new Intent(Question4RepActivity.this, CreerQuestionActivity.class);
            intent.putExtra("nbQuestion", "2");
            SharedPreferences sp = getSharedPreferences("score", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("getScore", nbPoint);
            editor.apply();
            nbPoint = 0;
            startActivity(intent);
        }
        else{
            questionMultipleList = getIntent().getParcelableArrayListExtra("listQuestion4Rep");
            new GetRessources().execute();
            question = questionMultipleList.get(count);
            if (Locale.getDefault().getDisplayLanguage().equals("français")){
                tvQuestion.setText(question.getTitre());
            }
            else {
                tvQuestion.setText(question.getTitreEn());
            }
            tvNiveau.setText(getString(R.string.niveau) +" "+question.getNumNiveau());
            tvNumQuestion.setText(question.getNumQuestion()+"/4");
            pgCircle.setProgress(Integer.parseInt(question.getNumQuestion())*25);
            tvScore.setText(nbPoint+"/4 points");
            b1.setText(question.getReponses().get(0));
            b1.setTextColor(Color.WHITE);
            b2.setText(question.getReponses().get(1));
            b2.setTextColor(Color.WHITE);
            b3.setText(question.getReponses().get(2));
            b3.setTextColor(Color.WHITE);
            b4.setText(question.getReponses().get(3));
            b4.setTextColor(Color.WHITE);
        }
        b1.setOnClickListener(v -> {
            checkReponse(0);
            b1.setClickable(false);
            b2.setClickable(false);
            b3.setClickable(false);
            b4.setClickable(false);
        });
        b2.setOnClickListener(v -> {
            checkReponse(1);
            b1.setClickable(false);
            b2.setClickable(false);
            b3.setClickable(false);
            b4.setClickable(false);
        });
        b3.setOnClickListener(v -> {
            checkReponse(2);
            b1.setClickable(false);
            b2.setClickable(false);
            b3.setClickable(false);
            b4.setClickable(false);
        });
        b4.setOnClickListener(v -> {
            checkReponse(3);
            b1.setClickable(false);
            b2.setClickable(false);
            b3.setClickable(false);
            b4.setClickable(false);
        });

    }



    public void checkReponse(int index){
        if (question.getReponses().get(index).equals(question.getReponse())) {
            sonRep = MediaPlayer.create(getApplicationContext(), R.raw.bonne_reponse);
            sonRep.start();
            nbPoint++;
        } else {
            sonRep = MediaPlayer.create(getApplicationContext(), R.raw.mauvaise_reponse);
            sonRep.start();
        }
        colorReponseButton();
        count++;
        new Thread(() -> {
            try
            {

                Thread.sleep(delaiEntreChaqueQuestion);
                intent.putParcelableArrayListExtra("listQuestion4Rep", (ArrayList<? extends Parcelable>) questionMultipleList);
                intent.putExtra("numNiveau", question.getNumNiveau());
                stopAndResetSound();
                startActivity(intent);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }).start();
    }

    public void stopAndResetSound(){
        sonRep.reset();
        sonRep.release();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void colorReponseButton(){
        if (question.getReponses().get(0).equals(question.getReponse())) {
            b1.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b1.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(1).equals(question.getReponse())) {
            b2.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b2.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(2).equals(question.getReponse())) {
            b3.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b3.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(3).equals(question.getReponse())) {
            b4.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b4.setBackground(getDrawable(R.drawable.button_mrep));
        }
    }

    /**
     * Tache asynchrone
     */
    @SuppressLint("StaticFieldLeak")
    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //pDialog = new ProgressDialog(Question4RepActivity.this);
            //pDialog.setMessage("Téléchargement en cours...\nIl est possible que cela prenne 30s à 2 minutes");
            //pDialog.setCancelable(false);
            //pDialog.show();

        }

        @Override
        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched question4rep");
            try {
                String url = question.getUrl()+ question.getIdImage(); //"https://androidpianomaster.000webhostapp.com/ressources/Niveau1/lvl1.png"
                InputStream is = (InputStream) new URL(url).getContent();
                d = Drawable.createFromStream(is, "img");
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //if (pDialog.isShowing())
               // pDialog.dismiss();

            if(count<2) {
                ivQuestion.setImageDrawable(d);
            }
        }
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