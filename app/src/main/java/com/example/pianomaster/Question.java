package com.example.pianomaster;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class Question {
    private String numQuestion;
    private String titre;
    public Question(String titre) {
        this.titre = titre;
        this.numQuestion = "num question non defini";
    }
    public Question(String numQuestion, String titre) {
        this.titre = titre;
        this.numQuestion = numQuestion;
    }
    public String getTitre() {
        return titre;
    }
    public String getNumQuestion() {
        return numQuestion;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setNumQuestion(String numQuestion) {
        this.numQuestion = numQuestion;
    }
}