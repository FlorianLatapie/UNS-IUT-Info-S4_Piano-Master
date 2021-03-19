package com.example.pianomaster;

import java.util.List;

public class Level {
    private List<Question> listQuestion;
    private String titre;

    public Level(List<Question> list, String titre){
        this.listQuestion = list;
        this.titre = titre;
    }

    public List<Question> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<Question> listQuestion) {
        this.listQuestion = listQuestion;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}