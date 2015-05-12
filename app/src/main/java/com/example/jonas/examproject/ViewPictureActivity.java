package com.example.jonas.examproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ViewPictureActivity extends Activity {

    private ImageView image;
    private String picTitle;
    private String picDir;
    private EditText viewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picture);
        initUI();
    }

    public void initUI(){
        image = (ImageView) findViewById(R.id.imageViewPicture);
        viewTitle = (EditText) findViewById(R.id.pictureTextTitle_PictureView);

        Intent i = getIntent();

        picTitle = i.getStringExtra("TitleToEdit");
        viewTitle.setText(picTitle);
        picDir = i.getStringExtra("ContentToEdit");


        loadImageFromStorage(picDir, picTitle);
    }

    public Bitmap loadImageFromStorage(String path, String title)
    {
        Bitmap b = null;

        try {
            File f = new File(path, title + ".jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            image.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return b;
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
