package com.example.jonas.examproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditNoteActivity extends ActionBarActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initUI();
    }

    public void initUI(){
        editTextTitle = (EditText) findViewById(R.id.editTextTitle_EditNote);
        editTextContent = (EditText) findViewById(R.id.editTextContent_EditNote);


        Intent i = getIntent();

        editTextTitle.setText(i.getStringExtra("TitleToEdit"));
        editTextContent.setText(i.getStringExtra("ContentToEdit"));

    }

public void editNote (View view){

        //Gets edited text
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        //Calls DBHandler with the new object
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title, content);
        dbHandler.updateNote(noteObject);

        //Changes view
        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
        //dbHandler.deleteNote(noteObject.getTitle().toString());
    }
    public void deleteNote (View view){

        //Gets the text
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        //Calls DBHandler
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title, content);
        dbHandler.deleteNote(title);

        //Changes view
        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
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
