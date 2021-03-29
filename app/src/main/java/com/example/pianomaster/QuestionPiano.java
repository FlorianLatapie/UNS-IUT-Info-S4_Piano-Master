package com.example.pianomaster;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionPiano extends Question implements Parcelable {
    private List<String> listNotes;
    private final String idAudio;
    private String url;

    public QuestionPiano(String titre, String numQuestion, int numNiveau, int score, String url, String idAudio, List<String> list, String titreEn){
        super(titre, numQuestion, numNiveau, titreEn);
        this.url = url;
        this.idAudio = idAudio;
        this.listNotes = list;
    }

    public QuestionPiano(String titre, String numQuestion, int numNiveau, int score, String url, String titreEn){
        super(titre, numQuestion, numNiveau, titreEn);
        this.url = url;
        this.idAudio = "";
    }

    protected QuestionPiano(Parcel in) {
        super(in.readString(), in.readString(), in.readInt(), in.readString());
        url = in.readString();
        idAudio = in.readString();
        listNotes = in.createStringArrayList();
    }

    public static final Creator<QuestionPiano> CREATOR = new Creator<QuestionPiano>() {
        @Override
        public QuestionPiano createFromParcel(Parcel in) {
            return new QuestionPiano(in);
        }

        @Override
        public QuestionPiano[] newArray(int size) {
            return new QuestionPiano[size];
        }
    };

    public List<String> getReponse() {
        return listNotes;
    }

    public void setReponse(List<String> reponse) {
        this.listNotes = reponse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdAudio() {
        return idAudio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getTitre());
        dest.writeString(getNumQuestion());
        dest.writeInt(getNumNiveau());
        dest.writeString(getTitreEn());
        dest.writeString(url);
        dest.writeString(idAudio);
        dest.writeStringList(listNotes);
    }
}
