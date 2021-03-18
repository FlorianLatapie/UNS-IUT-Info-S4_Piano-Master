package com.example.pianomaster;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;


    private static String url_site = "https://androidpianomaster.000webhostapp.com/ressources";
    //WebView webview;
    QuestionMultiple questionMultiple;
    Button propositionButton1;
    Button propositionButton2;
    Button propositionButton3;
    Button propositionButton4;
    TextView questionTitle;
    ImageView imageQuestion;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niveau_4reponses);
        propositionButton1 = findViewById(R.id.b_rep_1);
        propositionButton2 = findViewById(R.id.b_rep_2);
        propositionButton3 = findViewById(R.id.b_rep_3);
        propositionButton4 = findViewById(R.id.b_rep_4);
        questionTitle = findViewById(R.id.tv_question_q4rep);
        imageQuestion = findViewById(R.id.iv_question_q4rep);
        /*webview = findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);*/
        new GetRessources().execute();
    }

    /**
     * Tache asynchrone
     */
    private class GetRessources extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(QuestionActivity.this);
            pDialog.setMessage("Connexion en cours...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(url_site+"/Niveau1/Niveau1.json");
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    System.out.println(jsonObj);
                    String questions = jsonObj.getString("questions");
                    String image = jsonObj.getString("image");
                    JSONArray propositions = jsonObj.getJSONArray("proposition");
                    List<String> listProposition = new ArrayList<>();
                    for(int i=0; i<propositions.length(); i++){
                        String jsonString = propositions.getString(i);
                        listProposition.add(jsonString);
                    }
                    System.out.println(listProposition);
                    String reponse = jsonObj.getString("reponse");

                    questionMultiple = new QuestionMultiple(questions, image, listProposition, reponse);
                } catch (final JSONException e) {
                    Log.e(TAG, "Erreur JSON " + e.getMessage());

                }
            } else {
                Log.e(TAG, "Probleme connexion ");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (pDialog.isShowing())
                pDialog.dismiss();

            System.out.println(questionMultiple.getIdImage());
            questionTitle.setText(questionMultiple.getTitre());
            String url_site_image = url_site+"/Niveau1/"+questionMultiple.getIdImage();
            System.out.println(url_site_image);

            new DownloadImage().execute(url_site_image);

            propositionButton1.setText(questionMultiple.getQuestionPossibilities().get(0));
            propositionButton2.setText(questionMultiple.getQuestionPossibilities().get(1));
            propositionButton3.setText(questionMultiple.getQuestionPossibilities().get(2));
            propositionButton4.setText(questionMultiple.getQuestionPossibilities().get(3));
        }

    }
    ProgressDialog mProgressDialog;
    private class DownloadImage extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(QuestionActivity.this);
            mProgressDialog.setTitle("Download Image");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(Object... URL) {
            String imageURL = (String)URL[0];
            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Object result) {
            // Set the bitmap into ImageView
            imageQuestion.setImageBitmap((Bitmap)result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }
}