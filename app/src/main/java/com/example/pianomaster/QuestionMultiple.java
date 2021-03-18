package com.example.pianomaster;

import java.util.List;

public class QuestionMultiple extends Question {
    private List<String> questionPossibilities;
    private String response;
    private String idImage;

    public QuestionMultiple(String titre, String idImage, List<String> listPossibilite, String response){
        super(titre);
        this.questionPossibilities = listPossibilite;
        this.response = response;
        this.idImage = idImage;
    }

    public boolean isResponseCorrect(String responseChoose){
        return responseChoose.equals(response);
    }

    public List<String> getQuestionPossibilities() {
        return questionPossibilities;
    }

    public void setQuestionPossibilities(List<String> questionPossibilities) {
        this.questionPossibilities = questionPossibilities;
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
