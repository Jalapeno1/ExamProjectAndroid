package com.example.jonas.examproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonas on 06-05-2015.
 */
public class CustomListAdapter extends ArrayAdapter<NoteObject> {

    private ArrayList<NoteObject> objects;

    // https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/

    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<NoteObject> items){
        super(context, textViewResourceId, items);
        this.objects = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_notes, null);
        }

        NoteObject no = objects.get(position);

        if(no != null){
            TextView top_text = (TextView) v.findViewById(R.id.toptext);
            TextView note_title = (TextView) v.findViewById(R.id.noteTitle);
            TextView note_content = (TextView) v.findViewById(R.id.noteContent);

            if(top_text != null) {
                top_text.setText("Your Notes:");
            }

            if(note_title != null){
                note_title.setText(no.getTitle());
            }

            if(note_content != null){
                note_content.setText(no.getContent());
            }
        }
        return v;
    }
}
