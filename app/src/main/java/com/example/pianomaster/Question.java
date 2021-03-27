package com.example.pianomaster;

public class Question {
    private String numQuestion;
    private int numNiveau;
    private String titre;
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

    public String getTitreEn() {
        return titreEn;
    }

    public void setTitreEn(String titreEn) {
        this.titreEn = titreEn;
    }
}