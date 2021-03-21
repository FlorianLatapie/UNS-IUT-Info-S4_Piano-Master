package com.example.pianomaster;

public class Question {
    private String numQuestion;
    private int numNiveau;
    private String titre;
    private static int score;

    public Question(String titre) {
        this.titre = titre;
        this.numQuestion = "num question non defini";
    }
    public Question(String titre, String numQuestion, int numNiveau, int score) {
        this.titre = titre;
        this.numQuestion = numQuestion;
        this.numNiveau = numNiveau;
        this.score = score;
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

    public static int getScore() {
        return score;
    }

    public static void setScore(int s) {
        score = s;
    }

    public static void addScore(int nbPoint){
        score += nbPoint;
    }
}