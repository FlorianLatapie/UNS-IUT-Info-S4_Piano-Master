package com.example.pianomaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LevelActivity extends Activity {
    private Context mContext = this;
    private final String URL_RESSOURCES = "https://androidpianomaster.000webhostapp.com/ressources";
    private ProgressDialog pDialog;
    private int nbNiveaux = 10;
    private GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix_niveau);
        GridView gridview = findViewById(R.id.gridView);

        new GetNbNiveau().execute();
    }

    private class GetNbNiveau extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Téléchargement du nombre de niveaux en cours...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override

        // appelee automatiquement après onPreExecute
        protected Void doInBackground(Void... arg0) {
            System.out.println("background task lanched");
            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall(URL_RESSOURCES + "/nbNiveaux.json");
            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    JSONObject jsonObj = jsonArray.getJSONObject(0);
                    nbNiveaux =  Integer.parseInt(jsonObj.getString("nbNiveaux"));
                    System.out.println("73 nb niveaux" + nbNiveaux);



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

            System.out.println("93 nb niveaux" + nbNiveaux);

            gridview = findViewById(R.id.gridView);
            gridview.setAdapter(new LevelAdapter(mContext, nbNiveaux));


        }


    }
}
