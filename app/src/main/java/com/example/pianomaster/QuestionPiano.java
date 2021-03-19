package com.example.pianomaster;

import java.util.HashMap;
import java.util.List;

public class QuestionPiano extends Question{
    private List<String> reponse;
    private String url;

    public QuestionPiano(String numQuestion, String titre, String url){
        super(numQuestion,titre);
        this.url = url;
    }

    public List<String> getReponse() {
        return reponse;
    }

    public void setReponse(List<String> reponse) {
        this.reponse = reponse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
