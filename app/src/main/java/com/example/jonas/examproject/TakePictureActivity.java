package com.example.jonas.examproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class TakePictureActivity extends Activity {

    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private Uri imageUri;
    private ImageView picture;
    private Button savePicture;
    private String fileName;
    private Bitmap bmp;
    private MyDBHandler db;

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

        savePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bitmapstring = BitMapToString(bmp);
                db.addNote(new NoteObject("test", bitmapstring));

            }
        });
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
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
//        fileName = "temp_file_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName));
//        newI.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
