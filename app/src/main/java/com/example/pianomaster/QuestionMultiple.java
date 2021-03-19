package com.example.pianomaster;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QuestionMultiple extends Question implements Parcelable {
    private List<String> reponses;
    private String response;
    private String idImage;
    private String url;

    public QuestionMultiple(String numQuestion, String titre, String idImage, String url, List<String> reponses, String response){
        super(numQuestion,titre);
        this.reponses = reponses;
        this.response = response;
        this.idImage = idImage;
        this.url = url;
    }

    protected QuestionMultiple(Parcel in) {
        super(in.readString(), in.readString());
        idImage = in.readString();
        url = in.readString();
        reponses = in.createStringArrayList();
        response = in.readString();
    }

    public static final Creator<QuestionMultiple> CREATOR = new Creator<QuestionMultiple>() {
        @Override
        public QuestionMultiple createFromParcel(Parcel in) {
            return new QuestionMultiple(in);
        }

        @Override
        public QuestionMultiple[] newArray(int size) {
            return new QuestionMultiple[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getResponse() {
        return response;
    }

    public String getIdImage() {
        return idImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getNumQuestion());
        dest.writeString(getTitre());
        dest.writeString(idImage);
        dest.writeString(url);
        dest.writeStringList(reponses);
        dest.writeString(response);
    }
}
