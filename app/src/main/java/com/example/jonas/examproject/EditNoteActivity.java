package com.example.jonas.examproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
        saveEdit = (Button) findViewById(R.id.buttonSaveEdit);

        Intent i = getIntent();

        Log.d("Editnoteactivity", i.getStringExtra("TitleToEdit"));

        editTextTitle.setText(i.getStringExtra("TitleToEdit"));
        editTextContent.setText(i.getStringExtra("ContentToEdit"));
        Log.d("SOMETHING", i.getParcelableExtra("objectToChange").toString());

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OverviewActivity ao = new OverviewActivity();
                if(ao.objectToEdit == null){
                    Log.d("ONCLICKER IN EDITNOTE", "object is null");
                } else {
                    ao.objectToEdit.setTitle(editTextTitle.getText().toString());
                    ao.objectToEdit.setContent(editTextContent.getText().toString());
                }

//                NoteObject objectEdit = new NoteObject(editTextTitle.getText().toString(), editTextContent.getText().toString());
//
//                Intent ie = new Intent(getApplicationContext(), OverviewActivity.class);
//
//                ie.putExtra("objectToChange", objectEdit);
//
//                startActivity(ie);
            }
        });
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
