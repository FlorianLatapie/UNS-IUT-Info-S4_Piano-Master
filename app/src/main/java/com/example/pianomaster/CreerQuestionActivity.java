package com.example.pianomaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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

public class CreerQuestionActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private static int score = 0;

    private ProgressDialog pDialog;
    private ListView lv;


    private static String url_ressources = "https://androidpianomaster.000webhostapp.com/ressources";
    Button b1, b2, b3, b4; // boutons de réponse
    TextView tvQuestion, tvNiveau;
    ImageView ivQuestion;


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
        //Intent intent = getIntent();
        //int imageIdPersonne = getIntent().getExtras().getInt("idImage");
        //question = getIntent().getExtras().getParcelable("");
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
            pDialog = new ProgressDialog(CreerQuestionActivity.this);
            pDialog.setMessage("Téléchargement en cours...\nIl est possible que cela prenne 30s à 2 minutes");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched");
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url_ressources +"/Niveau1/Niveau1.json");
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    String numQuestion = jsonObj.getString("num_question");
                    String typeQuestion = jsonObj.getString("type");
                    String questions = jsonObj.getString("questions");
                    List<String> listProposition = new ArrayList<>();
                    if(typeQuestion.equals("multiple")) {
                        String reponse = jsonObj.getString("reponse");
                        String image = jsonObj.getString("image");
                        JSONArray propositions = jsonObj.getJSONArray("proposition");
                        for(int i=0; i<propositions.length(); i++){
                            String jsonString = propositions.getString(i);
                            listProposition.add(jsonString);
                        }
                        QuestionMultiple question = new QuestionMultiple(numQuestion, questions, image, url_ressources+"/Niveau1/", listProposition, reponse);
                        Intent intent = new Intent(CreerQuestionActivity.this, Question4RepActivity.class);
                        intent.putExtra("questionMultiple", (Parcelable) question);
                        startActivity(intent);
                    }
                    else if(typeQuestion.equals("piano")){
                        QuestionPiano question = new QuestionPiano(numQuestion, typeQuestion, url_ressources+"/Niveau1/");
                        Intent intent = new Intent(CreerQuestionActivity.this, QuestionPiano.class);
                        intent.putExtra("questionPiano", (Parcelable) question);
                        startActivity(intent);
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

            if (pDialog.isShowing())
                pDialog.dismiss();
        }

    }
}
