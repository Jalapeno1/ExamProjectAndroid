package com.example.jonas.examproject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;

import com.google.gson.Gson;
import java.util.ArrayList;

public class OverviewActivity extends ListActivity {

    private ListView noteView;
    private Button buttonNewNote;

    private ArrayList<NoteObject> allNotes = new ArrayList<>();
    private Runnable viewNotes;
    private CustomListAdapter adapter;

    private static final String TAG = "OverviewActivity";
    public NoteObject objectToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        noteEdit();

        //Reads all notes in DB
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        allNotes = dbHandler.getAll();

        for(NoteObject no : allNotes){
            //Prints to LOG
            String log = "Title: " + no.getTitle() + ", Content: " + no.getContent();
        }

        initUI();
        initButtonListener();
        initAdapter();
        initListViewListener();

    }

    public void noteEdit(){
        try{
            Log.d(TAG, objectToEdit.toString());
        } catch (Exception e){

        }
    }
    
    public void initListViewListener(){
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClickItem EXECUTED");

                objectToEdit = (NoteObject)parent.getAdapter().getItem(position);

                Log.d(TAG, objectToEdit.toString());

                String SELECTED_TITLE = objectToEdit.getTitle();
                String SELECTED_CONTENT = objectToEdit.getContent();

                Intent i = new Intent(getApplicationContext(), EditNoteActivity.class);

                i.putExtra("TitleToEdit", SELECTED_TITLE);
                i.putExtra("ContentToEdit", SELECTED_CONTENT);
                i.putExtra("objectToChange", objectToEdit);

                startActivity(i);
            }
        });
    }

    public void initUI(){
        //noteView = (ListView) findViewById(R.id.);
        buttonNewNote = (Button) findViewById(R.id.buttonNewNote);
        Log.d(TAG, "initiated UI");
    }

    public void initButtonListener(){
        buttonNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        Log.d(TAG, "initiated ButtonListener");
    }

    public void initAdapter(){

        //instantiate the custom adapter (XML layout file; list_notes & objects; ArrayList<NoteObjects> added)
        adapter = new CustomListAdapter(this, R.layout.list_notes, allNotes);
        setListAdapter(adapter);

        //start thread for list creating
        viewNotes = new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };

        Thread thread = new Thread(null, viewNotes, "MagentoBackground");
        thread.start();
        Log.d(TAG, "initiated Adapter");
    }

    //handles the data used for the listView
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            //test data :-)
            allNotes.add(new NoteObject("TestTitle", "You can scroll the History view up and down " +
                    "to see all of a repository's commitseven the first one ever made! " +
                    "Select a commit from the list to reveal its individual file diffs."));
            allNotes.add(new NoteObject("TestTitle2", "Congratulations! So far, you've learned " +
                    "how to fork a project to your own account on GitHub, clone it in GitHub for " +
                    "Mac so that you can make your own changes, commit and sync those changes, and " +
                    "view the whole commit history."));
            allNotes.add(new NoteObject("TestTitle", "You can scroll the History view up and down " +
                    "to see all of a repository's commitseven the first one ever made! " +
                    "Select a commit from the list to reveal its individual file diffs."));
            allNotes.add(new NoteObject("TestTitle2", "Congratulations! So far, you've learned " +
                    "how to fork a project to your own account on GitHub, clone it in GitHub for " +
                    "Mac so that you can make your own changes, commit and sync those changes, and " +
                    "view the whole commit history."));
            allNotes.add(new NoteObject("TestTitle", "You can scroll the History view up and down " +
                    "to see all of a repository's commitseven the first one ever made! " +
                    "Select a commit from the list to reveal its individual file diffs."));
            allNotes.add(new NoteObject("TestTitle2", "Congratulations! So far, you've learned " +
                    "how to fork a project to your own account on GitHub, clone it in GitHub for " +
                    "Mac so that you can make your own changes, commit and sync those changes, and " +
                    "view the whole commit history."));
            allNotes.add(new NoteObject("TestTitle", "You can scroll the History view up and down " +
                    "to see all of a repository's commitseven the first one ever made! " +
                    "Select a commit from the list to reveal its individual file diffs."));
            allNotes.add(new NoteObject("TestTitle2", "Congratulations! So far, you've learned " +
                    "how to fork a project to your own account on GitHub, clone it in GitHub for " +
                    "Mac so that you can make your own changes, commit and sync those changes, and " +
                    "view the whole commit history."));
            allNotes.add(new NoteObject("TestTitle", "You can scroll the History view up and down " +
                    "to see all of a repository's commitseven the first one ever made! " +
                    "Select a commit from the list to reveal its individual file diffs."));
            allNotes.add(new NoteObject("TestTitle2", "Congratulations! So far, you've learned " +
                    "how to fork a project to your own account on GitHub, clone it in GitHub for " +
                    "Mac so that you can make your own changes, commit and sync those changes, and " +
                    "view the whole commit history."));

            //NOTE: hvis der skal hentes data fra DB/server, saa goeres det her.
            adapter = new CustomListAdapter(OverviewActivity.this, R.layout.list_notes, allNotes);

            setListAdapter(adapter);
            Log.d(TAG, "HandlerSet");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
