package com.example.jonas.examproject;

import android.content.Context;
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
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;
    private Button submit;
    private TextToSpeech tts;

    private String fileName = "notes.txt";

    ArrayList<NoteObject> allNotes = new ArrayList<NoteObject>();
    Gson gson = new Gson();



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

                NoteObject no = new NoteObject(title.getText().toString(), content.getText().toString());

                allNotes.add(no);

                JSONArray jsArray = new JSONArray(allNotes);
                String jsonObject = gson.toJson(no);

//                try {
//                    writeFile(jsonObject);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "ERROR: Failed to save note.",
//                            Toast.LENGTH_LONG).show();
//                }

                //i.putExtra("getNote", jsonObject);
                //i.putParcelableArrayListExtra("allNotes", allNotes);
                i.putExtra("JSON_NOTES", jsArray.toString());
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
