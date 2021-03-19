package com.example.pianomaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Question4RepActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;


    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    QuestionMultiple question;
    Button b1, b2, b3, b4; // boutons de réponse
    TextView tvQuestion, tvNiveau;
    ImageView ivQuestion;


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
        question = getIntent().getExtras().getParcelable("questionMultiple");
        new GetRessources().execute();
    }

    /**
     * Tache asynchrone
     */
    private class GetRessources extends AsyncTask<Void, Void, Void> {
        Drawable d;
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
                String url = question.getUrl()+question.getIdImage(); //"https://androidpianomaster.000webhostapp.com/ressources/Niveau1/lvl1.png"
                InputStream is = (InputStream) new URL(url).getContent();
                System.out.println("is créé");
                d = Drawable.createFromStream(is, "lvl1");
                System.out.println("drawable créé");
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

            System.out.println("id de l'image : "+question.getIdImage());
            tvQuestion.setText(question.getTitre());
            tvNiveau.setText("Niveau "+question.getNumQuestion());
            ivQuestion.setImageDrawable(d);
            b1.setText(question.getReponses().get(0));
            b1.setTextColor(Color.WHITE);
            b2.setText(question.getReponses().get(1));
            b2.setTextColor(Color.WHITE);
            b3.setText(question.getReponses().get(2));
            b3.setTextColor(Color.WHITE);
            b4.setText(question.getReponses().get(3));
            b4.setTextColor(Color.WHITE);
        }

    }
}