package com.example.jonas.examproject;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;


public class OverviewActivity extends ActionBarActivity {

    private TextView showTitle;
    private TextView showContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        showTitle = (TextView) findViewById(R.id.textViewShowTitle);
        showContent = (TextView) findViewById(R.id.textViewShowContent);

        Gson gson = new Gson();

        NoteObject note = gson.fromJson(getIntent().getStringExtra("getNote"), NoteObject.class);

        showTitle.setText(note.getTitle());
        showContent.setText(note.getContent());
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
