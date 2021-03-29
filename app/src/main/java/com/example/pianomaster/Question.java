package com.example.pianomaster;

public class Question {
    private final String numQuestion;
    private int numNiveau;
    private final String titre;
    private String titreEn;

    public Question(String titre) {
        this.titre = titre;
        this.numQuestion = "num question non defini";
    }
    public Question(String titre, String numQuestion, int numNiveau, String titreEn) {
        this.titre = titre;
        this.numQuestion = numQuestion;
        this.numNiveau = numNiveau;
        this.titreEn = titreEn;
    }
    public String getTitre() {
        return titre;
    }
    public String getNumQuestion() {
        return numQuestion;
    }
    public int getNumNiveau() {
        return numNiveau;
    }
    public String getTitreEn() {
        return titreEn;
    }

}