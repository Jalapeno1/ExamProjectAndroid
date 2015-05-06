package com.example.jonas.examproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jonas on 28-04-2015.
 */
public class NoteObject {
    private String title;
    private String content;

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

}
