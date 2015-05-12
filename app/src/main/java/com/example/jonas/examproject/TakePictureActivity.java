package com.example.jonas.examproject;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class TakePictureActivity extends Activity {

    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Uri imageUri;
    private ImageView picture;
    private Button savePicture;
    private TextView pictureTitle;
    private String fileName;
    private Bitmap bmp;
    private Bitmap bmpToSave;
    private String savePath;
    private MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        initUI();
        takePicture();
    }

    public void initUI(){
        picture = (ImageView) findViewById(R.id.imageViewPictureNote);
        savePicture = (Button) findViewById(R.id.buttonSavePicture);
        pictureTitle = (TextView) findViewById(R.id.PictureNote_Title);

        savePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savePath = ih.saveToInternalSorage("test", getApplicationContext(), bmp);

                String picTitle = pictureTitle.getText().toString();
                savePath = saveToInternalSorage(picTitle, bmp);
                //also save to NoteOverview to view picture notes in OverviewActivity
                NoteObject no = new NoteObject(picTitle, savePath);
                dbHandler.addNote(no);

                Intent i = new Intent(getApplicationContext(), OverviewActivity.class);

                startActivity(i);

            }
        });
    }

    public String saveToInternalSorage(String title, Bitmap bitmapImage){

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir

        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir

        File path = new File(directory, "/" + title + ".jpg");

        FileOutputStream fos;

        try {
            fos = new FileOutputStream(path);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return directory.getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                //use imageUri here to access the image

                Bundle extras = data.getExtras();

                bmp = (Bitmap) extras.get("data");

                // here you will get the image as bitmap

                picture.setImageBitmap(bmp);


            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
            }
        }
    }

    public void takePicture(){
        Intent newI = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(newI, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_picture, menu);
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
            case R.id.action_standard_note:
                Intent newI = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(newI);
                break;
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
