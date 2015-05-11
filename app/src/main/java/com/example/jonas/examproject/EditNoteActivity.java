package com.example.jonas.examproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditNoteActivity extends Activity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSaveEdit;
    private Button buttonDeleteNote;

    private String oldtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initUI();
    }

    public void initUI(){
        editTextTitle = (EditText) findViewById(R.id.editTextTitle_EditNote);
        editTextContent = (EditText) findViewById(R.id.editTextContent_EditNote);
        buttonSaveEdit = (Button) findViewById(R.id.buttonSaveEdited);
        buttonDeleteNote = (Button) findViewById(R.id.buttonDeleteNote);

        Intent i = getIntent();

        editTextTitle.setText(i.getStringExtra("TitleToEdit"));
        editTextContent.setText(i.getStringExtra("ContentToEdit"));

        oldtitle = i.getStringExtra("TitleToEdit");

        //lock functionality -> change when 'edit' clicked
        editTextTitle.setFocusable(false);
        editTextContent.setFocusable(false);
        buttonSaveEdit.setVisibility(View.INVISIBLE);

        //Delete Button Listener
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote(view);
                    }
                });
                builder.setNegativeButton("No", null);
                AlertDialog ad = builder.create();
                ad.setCanceledOnTouchOutside(true);
                ad.show();
            }
        });
    }

public void editNote (View view){

        //Gets edited text
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();


        //Calls DBHandler with the new object
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title, content);
        dbHandler.updateNote(oldtitle, noteObject);

        //Changes view
        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
        //dbHandler.deleteNote(noteObject.getTitle().toString
        // ());
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

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_edit:
                editTextTitle.setFocusableInTouchMode(true);
                editTextContent.setFocusableInTouchMode(true);
                buttonSaveEdit.setVisibility(View.VISIBLE);
                break;
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
