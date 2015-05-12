package com.example.jonas.examproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ViewPictureActivity extends Activity {

    private ImageView image;
    private Button deletePic;
    private String picTitle;
    private String picDir;
    private EditText viewTitle;
    private Bitmap b = null;
    private File file;
    private MyDBHandler db = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);
        initUI();
    }

    public void initUI(){
        image = (ImageView) findViewById(R.id.imageViewPicture);
        viewTitle = (EditText) findViewById(R.id.pictureTextTitle_PictureView);
        deletePic = (Button) findViewById(R.id.buttonDeletePicture);

        Intent i = getIntent();

        picTitle = i.getStringExtra("TitleToEdit");
        viewTitle.setText(picTitle);
        viewTitle.setFocusable(false);
        picDir = i.getStringExtra("ContentToEdit");

        loadImageFromStorage(picDir, picTitle);

        deletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImageFromStorage();
            }
        });
    }

    public Bitmap loadImageFromStorage(String path, String title)
    {
        try {
            file = new File(path, title + ".jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(file));
            image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return b;
    }

    public void deleteImageFromStorage(){
        boolean delt = file.delete();
        Log.d("ViewPictureActivity", Boolean.toString(delt));
        db.deleteNote(picTitle);

        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_picture, menu);
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
