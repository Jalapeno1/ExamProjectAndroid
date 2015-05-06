package com.example.jonas.examproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OverviewActivity extends ActionBarActivity {

    private TextView showTitle;
    private TextView showContent;
    private ListView noteView;
    private Button buttonNewNote;

    private ArrayList<NoteObject> allNotes = new ArrayList<>();
    private String fileName = "notes.txt";

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        initUI();
        initButtonListener();

        addNewNote(getIntent().getExtras().getString("getNote"));

        JSONArray jsonarray = new JSONArray(allNotes);

        String laa = jsonarray.toString();

        ArrayAdapter<NoteObject> adapter = new ArrayAdapter<NoteObject>(this, R.layout.activity_overview, allNotes);
        noteView.setAdapter(adapter);


//        String read = "";

//        try {
//            writeFile(jsonarray.toString());
//            read = readFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getApplicationContext(), "SAVING ERROR: Your notes may not be saved.",
//            Toast.LENGTH_LONG).show();
//        }
//
//        showTitle.setText(read);
    }

    public void addNewNote(String jsonObject){
        NoteObject newNote = gson.fromJson(jsonObject, NoteObject.class);

        String maa = Integer.toString(allNotes.size());

        allNotes.add(newNote);

        String naa = Integer.toString(allNotes.size());

        showContent.setText(maa + " : " + naa);
    }

    public void initUI(){
        showTitle = (TextView) findViewById(R.id.textViewShowTitle);
        showContent = (TextView) findViewById(R.id.textViewShowContent);
        noteView = (ListView) findViewById(R.id.listViewNotes);
        buttonNewNote = (Button) findViewById(R.id.buttonNewNote);
    }

    public void initButtonListener(){
        buttonNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    public String readFile() throws IOException {
        byte[] buffer = new byte[50];
        FileInputStream fis = openFileInput(fileName);
        fis.read(buffer);
        fis.close();

        return new String(buffer);
    }

    public void writeFile(String json) throws IOException {

        try{
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        }
        catch (IOException ioe){
            //error message to come!
        }
    }

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
