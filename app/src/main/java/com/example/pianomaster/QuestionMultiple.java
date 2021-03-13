package com.example.pianomaster;

import java.util.List;

public class QuestionMultiple extends Question {
    private List<String> questionPossibilities;
    private final String response;

    public QuestionMultiple(String titre, List<String> listPossibilite, String response){
        super(titre);
        this.questionPossibilities = listPossibilite;
        this.response = response;
    }

    public boolean isResponseCorrect(String responseChoose){
        return responseChoose.equals(response);
    }
}
