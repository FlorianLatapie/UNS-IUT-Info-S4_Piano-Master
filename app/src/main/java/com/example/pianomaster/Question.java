package com.example.pianomaster;

public class Question {
    private String numQuestion;
    private int numNiveau;
    private String titre;

    public Question(String titre) {
        this.titre = titre;
        this.numQuestion = "num question non defini";
    }
    public Question(String titre, String numQuestion, int numNiveau) {
        this.titre = titre;
        this.numQuestion = numQuestion;
        this.numNiveau = numNiveau;
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

    public int getNumNiveau() {
        return numNiveau;
    }

    public void setNumNiveau(int numNiveau) {
        this.numNiveau = numNiveau;
    }
}