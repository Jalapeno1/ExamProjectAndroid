package com.example.jonas.examproject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;



public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private EditText title;
    private EditText content;
    private Button submit;
    private String cordinates;

    private static final String TAG = "MainActivity";

    //For location
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGoogleApixClient();

        initUI();
        initButtonListener();

    }

    private void buildGoogleApixClient() {
        //Creates methods to be used for finding location
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

/*    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }*/

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
        NoteObject noteObject = new NoteObject(title.getText().toString(), content.getText().toString()); //+ " - Cordinates: " + cordinates
        dbHandler.addNote(noteObject);

        Toast.makeText(getApplicationContext(), getString(R.string.noteCreatedToast),
                Toast.LENGTH_LONG).show();

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
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "onStart()");
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.d("DEBUG", "data: " + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient));
        if (mLastLocation != null) {
            cordinates = String.valueOf(mLastLocation.getLatitude() + " - " + String.valueOf(mLastLocation.getLongitude()));
            Log.d("Cordinates",String.valueOf(mLastLocation.getLatitude()) + " : " + String.valueOf(mLastLocation.getLongitude()));
        } else {
            cordinates = "No location found";
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
}
