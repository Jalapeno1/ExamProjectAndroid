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

    // https://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/

    public CustomListAdapter(Context context, ArrayList<NoteObject> items){
        super(context, 0, items);
    }

}
