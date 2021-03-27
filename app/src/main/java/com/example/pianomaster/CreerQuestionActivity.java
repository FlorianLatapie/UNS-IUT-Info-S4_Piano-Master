package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreerQuestionActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private static int score = 0;
    private static int nbQuestion = 0;
    private static int nbCount = 0;

    private int niveau = 0;

    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    Button b1, b2, b3, b4; // boutons de réponse
    TextView tvQuestion, tvNiveau;
    ImageView ivQuestion;

    private String numQuestion;
    private String typeQuestion;
    private String questions;
    private String questionsEn;
    private List<String> listProposition = new ArrayList<>();
    private String reponse;
    private String image;
    private ArrayList<QuestionMultiple> listQuestionMultiple = new ArrayList<>();
    private ArrayList<QuestionPiano> listQuestionPiano = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_question);

        b1 = findViewById(R.id.b_rep_1);
        b2 = findViewById(R.id.b_rep_2);
        b3 = findViewById(R.id.b_rep_3);
        b4 = findViewById(R.id.b_rep_4);
        tvQuestion = findViewById(R.id.tv_question_q4rep);
        tvNiveau = findViewById(R.id.tv_titre_niveau_q4rep);
        ivQuestion = findViewById(R.id.iv_question_q4rep);
        if (niveau == 0 && nbCount < 1) {
            nbCount++;
            niveau = getIntent().getExtras().getInt("numNiveau");
            SharedPreferences sp = getSharedPreferences("niveau", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("getNiveau", niveau);
            editor.apply();
        } else{
            SharedPreferences sp = getSharedPreferences("niveau", Activity.MODE_PRIVATE);
            niveau = sp.getInt("getNiveau", -1);
        }

        url_ressources = "https://androidpianomaster.000webhostapp.com/ressources/Niveau" + niveau + "/";
        new GetRessources().execute();
    }


    /**
     * Tache asynchrone
     */
    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @RequiresApi(api = Build.VERSION_CODES.R)
        @Override

        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched creer question");
            HttpHandler sh = new HttpHandler();
            SharedPreferences sp = getSharedPreferences("niveau", Activity.MODE_PRIVATE);
            int niv = sp.getInt("getNiveau", -1);
            String jsonStr = sh.makeServiceCall(url_ressources + "Niveau" + niv + ".json");
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int j = 0; j < 4; j++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(j);
                        numQuestion = jsonObj.getString("num_question");
                        typeQuestion = jsonObj.getString("type");
                        questions = jsonObj.getString("questions");
                        questionsEn = jsonObj.getString("questions-en");
                        //System.out.println("question-en:"+questionsEn);
                        if (typeQuestion.equals("multiple")) {
                            reponse = jsonObj.getString("reponse");
                            image = jsonObj.getString("image");
                            JSONArray propositions = jsonObj.getJSONArray("proposition");
                            for (int i = 0; i < propositions.length(); i++) {
                                String jsonString = propositions.getString(i);
                                listProposition.add(jsonString);
                            }
                            listQuestionMultiple.add(new QuestionMultiple(questions, numQuestion, niveau, score, image, url_ressources, listProposition, reponse, questionsEn));
                            listProposition = new ArrayList<>();
                        } else if (typeQuestion.equals("piano")) {
                            String audio = jsonObj.getString("sons");
                            String note = jsonObj.getString("reponse");
                            String[] parts = note.split(" ");
                            List<String> list = List.of(parts);
                            listQuestionPiano.add(new QuestionPiano(questions, numQuestion, niveau, score, url_ressources, audio, list, questionsEn));
                        }
                    }
                } catch (final JSONException e) {
                    System.err.println("Erreur : lecture JSON :" + e.getMessage());
                }
            } else {
                System.err.println("JSON introuvable, veuillez vérifier l'URL");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            String nb;
            if ((nb = getIntent().getStringExtra("nbQuestion")) != null) {
                nbQuestion = Integer.parseInt(nb);
            }
            switch (nbQuestion) {
                // case 3:
                case 2:
                    Intent intent0 = new Intent(CreerQuestionActivity.this, PianoActivity.class);
                    intent0.putParcelableArrayListExtra("listQuestionPiano", listQuestionPiano);
                    startActivity(intent0);
                    break;
                case 4:
                    nbQuestion = 0;
                    nbCount = 0;
                    Intent intent2 = new Intent(CreerQuestionActivity.this, SMSActivity.class);
                    startActivity(intent2);
                    break;
                //  case 1:
                case 0:
                    Intent intent3 = new Intent(CreerQuestionActivity.this, Question4RepActivity.class);
                    intent3.putParcelableArrayListExtra("listQuestion4Rep", listQuestionMultiple);
                    startActivity(intent3);
                    break;
                default:
                    System.out.println("167 nb question " + nbQuestion);
                    break;
            }
        }
    }

}
