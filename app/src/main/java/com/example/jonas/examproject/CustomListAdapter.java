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

    //overrides Constructor to contain ArrayList<NoteObjects> (to show in ListView)
    public CustomListAdapter(Context context, int textViewResourceId, ArrayList<NoteObject> items){
        super(context, textViewResourceId, items);
        this.objects = items;
    }

    //getView defines how the ListView will look
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //position of the dataset, convertView: individual view within listview, parent: the listview


        //creates a local variable of the view
        View v = convertView;

        //check if view is null. If it is, it inflates (is shown/created)
        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_notes, null);
        }

        //the 'position' paramter from getView method is what element of the ListView (which note) is currently at.
        //A note object is created for each element in the ArrayList.
        NoteObject no = objects.get(position);

        if(no != null){

            //the UI components for each element i the list (each note) - XML data is in layout > list_notes.xml
            //it contains a TextView for a title and content
            TextView note_title = (TextView) v.findViewById(R.id.noteTitle);
            TextView note_content = (TextView) v.findViewById(R.id.noteContent);
            //checks if the components are null. If they are not, add values
            if(note_title != null){
                note_title.setText(no.getTitle());
            }

            if(note_content != null){
                //only allows a maksimum of 40 characters to avoid a HUGE liste
                if(no.getContent().contains("/storage/")){
                    note_content.setText("[Picture Note]");
                }else {
                    if(no.getContent().length() < 40){
                        note_content.setText(no.getContent());
                    } else {
                        note_content.setText(no.getContent().substring(0,40)+ "...");
                    }
                }
            }
        }
        //the view gets returned to the Activity
        return v;
    }
}
