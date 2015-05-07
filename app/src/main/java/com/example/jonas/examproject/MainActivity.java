package com.example.jonas.examproject;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;
    private Button submit;
    private TextToSpeech tts;

    private static final String TAG = "MainActivity";

    Gson gson = new Gson();

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

        //Text to speech
        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
                tts.speak(title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                tts.speak(content.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });
        //Writes to DB

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title.getText().toString(), content.getText().toString());
        dbHandler.addNote(noteObject);


        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);

        //Is just duplicating(Not in use anymore)
/*        NoteObject no = new NoteObject(title.getText().toString(), content.getText().toString());
        String jsonObject = gson.toJson(no);
        i.putExtra("getNote", jsonObject);*/

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
