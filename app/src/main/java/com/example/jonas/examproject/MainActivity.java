package com.example.jonas.examproject;

import android.content.Intent;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;
    private Button submit;
    private TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.editTextTitle);
        content = (EditText) findViewById(R.id.editTextContent);
        submit = (Button) findViewById(R.id.buttonSaveNote);





        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        tts.setLanguage(Locale.US);
                        tts.speak(title.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        tts.speak(content.getText().toString(), TextToSpeech.QUEUE_ADD, null);
                    }
                });


                Intent i = new Intent(getApplicationContext(), OverviewActivity.class);

                Gson gson = new Gson();
                NoteObject no = new NoteObject(title.getText().toString(), content.getText().toString());

                i.putExtra("getNote", gson.toJson(no));

                startActivity(i);

            }
        });
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
