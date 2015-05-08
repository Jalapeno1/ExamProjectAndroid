package com.example.jonas.examproject;


import android.os.Parcel;
import android.os.Parcelable;

public class NoteObject implements Parcelable {
    private String title;
    private String content;

    public NoteObject(){
        //Empty Constructor for DB
    }

    public NoteObject(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    @Override public String toString(){
        return "{\"title\":" + "\"" + title + "\"" + ",\"content\":" + "\"" + content + "\"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
    }

    public static final Parcelable.Creator<NoteObject> CREATOR = new Parcelable.Creator<NoteObject>() {
        public NoteObject createFromParcel(Parcel in) {
            return new NoteObject(in);
        }

        public NoteObject[] newArray(int size) {
            return new NoteObject[size];
        }
    };

    private NoteObject(Parcel in) {
        title = in.readString();
        content = in.readString();
    }
}
