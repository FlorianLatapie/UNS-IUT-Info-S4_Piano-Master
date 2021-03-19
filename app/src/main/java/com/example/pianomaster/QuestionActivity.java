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

public class QuestionActivity extends Activity {
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
            pDialog = new ProgressDialog(QuestionActivity.this);
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
                    System.out.println(jsonObj.length());
                    System.out.println(jsonObj);
                    String numQuestion = jsonObj.getString("num_question");
                    String questions = jsonObj.getString("questions");
                    String image = jsonObj.getString("image");
                    JSONArray propositions = jsonObj.getJSONArray("proposition");
                    List<String> listProposition = new ArrayList<>();
                    System.out.println(propositions.length());
                    for(int i=0; i<propositions.length(); i++){
                        String jsonString = propositions.getString(i);
                        listProposition.add(jsonString);
                    }
                    System.out.println("print listProposition :"+listProposition);
                    String reponse = jsonObj.getString("reponse");

                    question = new QuestionMultiple(numQuestion,questions, image, listProposition, reponse);
                    System.out.println("question creee");
                } catch (final JSONException e) {
                    System.err.println("Erreur : lecture JSON :" + e.getMessage());

                }
            } else {
                System.err.println("JSON introuvable, veuillez vérifier l'URL");
            }
            try {
                String url = "https://androidpianomaster.000webhostapp.com/ressources/Niveau1/lvl1.png";
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
            String url_site_image = url_ressources +"/Niveau1/"+ question.getIdImage();
            System.out.println(url_site_image);
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