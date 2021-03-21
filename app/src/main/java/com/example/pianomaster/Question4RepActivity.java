package com.example.pianomaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Question4RepActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private static int count = 0;
    private static int nbPoint = 0;

    private int niveau;

    private ProgressDialog pDialog;


    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    Button b1, b2, b3, b4; // boutons de réponse
    TextView tvQuestion, tvNiveau;
    ImageView ivQuestion;
    QuestionMultiple question;
    Drawable d;
    Intent intent;
    private List<QuestionMultiple> questionMultipleList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_4reponses);
        b1 = findViewById(R.id.b_rep_1);
        b2 = findViewById(R.id.b_rep_2);
        b3 = findViewById(R.id.b_rep_3);
        b4 = findViewById(R.id.b_rep_4);
        tvQuestion = findViewById(R.id.tv_question_q4rep);
        tvNiveau = findViewById(R.id.tv_titre_niveau_q4rep);
        ivQuestion = findViewById(R.id.iv_question_q4rep);
        intent = new Intent(Question4RepActivity.this, Question4RepActivity.class);

        if(count>1){
            count = 0;
            Intent intent = new Intent(Question4RepActivity.this, CreerQuestionActivity.class);
            intent.putExtra("nbQuestion", "2");
            Question.addScore(nbPoint);
            startActivity(intent);
        }
        else{
            questionMultipleList = getIntent().getParcelableArrayListExtra("listQuestion4Rep");
            niveau = getIntent().getExtras().getInt("numNiveau");
            new GetRessources().execute();
            question = questionMultipleList.get(count);
            tvQuestion.setText(question.getTitre());
            tvNiveau.setText("Niveau "+question.getNumNiveau()+ " - Question " + question.getNumQuestion());
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
        MediaPlayer sonRep;
        if (question.getReponses().get(index).equals(question.getResponse())) {
            sonRep = MediaPlayer.create(getApplicationContext(), R.raw.bonne_reponse);
            sonRep.start();
            nbPoint++;
        } else {
            sonRep = MediaPlayer.create(getApplicationContext(), R.raw.mauvaise_reponse);
            sonRep.start();
        }
        colorReponseButton();
        count++;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(3000);
                    intent.putParcelableArrayListExtra("listQuestion4Rep", (ArrayList<? extends Parcelable>) questionMultipleList);
                    startActivity(intent);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void colorReponseButton(){
        if (question.getReponses().get(0).equals(question.getResponse())) {
            b1.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b1.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(1).equals(question.getResponse())) {
            b2.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b2.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(2).equals(question.getResponse())) {
            b3.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b3.setBackground(getDrawable(R.drawable.button_mrep));
        }
        if (question.getReponses().get(3).equals(question.getResponse())) {
            b4.setBackground(getDrawable(R.drawable.button_brep));
        } else {
            b4.setBackground(getDrawable(R.drawable.button_mrep));
        }
    }

    /**
     * Tache asynchrone
     */
    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Question4RepActivity.this);
            pDialog.setMessage("Téléchargement en cours...\nIl est possible que cela prenne 30s à 2 minutes");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched");
            try {
                String url = question.getUrl()+ question.getIdImage(); //"https://androidpianomaster.000webhostapp.com/ressources/Niveau1/lvl1.png"
                System.out.println(url);
                InputStream is = (InputStream) new URL(url).getContent();
                d = Drawable.createFromStream(is, "lvl1");
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

            if(count<2) {
                ivQuestion.setImageDrawable(d);
            }
        }
    }


}