package com.example.jonas.examproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.File;
import java.util.Locale;


public class MainActivity extends Activity {

    private EditText title;
    private EditText content;
    private Button submit;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initButtonListener();

    }

    public void initUI(){
        title = (EditText) findViewById(R.id.editTextTitle);
        content = (EditText) findViewById(R.id.editTextContent);
        Log.d(TAG, "initiated UI");
    }

    public void initButtonListener(){

        Log.d(TAG, "initiated ButtonListener");
    }

    public void newNote (View view) {

        //Writes to DB
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title.getText().toString(), content.getText().toString());
        dbHandler.addNote(noteObject);


        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_take_picture:
                Intent newI = new Intent(getApplicationContext(), TakePictureActivity.class);
                startActivity(newI);
                break;
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
