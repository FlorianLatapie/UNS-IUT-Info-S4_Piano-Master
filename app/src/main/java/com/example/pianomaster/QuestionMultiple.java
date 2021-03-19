package com.example.pianomaster;

import java.util.List;

public class QuestionMultiple extends Question {
    private List<String> reponses;
    private String response;
    private String idImage;

    public QuestionMultiple(String numQuestion, String titre, String idImage, List<String> reponses, String response){
        super(numQuestion,titre);
        this.reponses = reponses;
        this.response = response;
        this.idImage = idImage;
    }

    public boolean isResponseCorrect(String responseChoose){
        return responseChoose.equals(response);
    }

    public List<String> getReponses() {
        return reponses;
    }

    public void setReponses(List<String> reponses) {
        this.reponses = reponses;
    }

    @Override
    public String getTitre() {
        return super.getTitre();
    }

    public String getResponse() {
        return response;
    }

    public String getIdImage() {
        return idImage;
    }
}
