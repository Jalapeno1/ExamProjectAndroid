package com.example.jonas.examproject;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;


public class EditNoteActivity extends FragmentActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button buttonSaveEdit;
    private Button buttonDeleteNote;

    private static int noteID;

    private String oldtitle;
    private String oldcontent;
    private String cordinates;
    private TextToSpeech tts;

    private PendingIntent pendingIntent;
    private AlarmManager alarmManager = null;

    private static String TAG = "EditNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        initUI();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //testAlarm();
    }

    public void initUI(){
        editTextTitle = (EditText) findViewById(R.id.editTextTitle_EditNote);
        editTextContent = (EditText) findViewById(R.id.editTextContent_EditNote);
        buttonSaveEdit = (Button) findViewById(R.id.buttonSaveEdited);
        buttonDeleteNote = (Button) findViewById(R.id.buttonDeleteNote);

        Intent i = getIntent();

        editTextTitle.setText(i.getStringExtra("TitleToEdit"));
        editTextContent.setText(i.getStringExtra("ContentToEdit"));

        oldtitle = i.getStringExtra("TitleToEdit");
        oldcontent = i.getStringExtra("ContentToEdit");

        //lock functionality -> change when 'edit' clicked
        editTextTitle.setFocusable(false);
        editTextContent.setFocusable(false);
        buttonSaveEdit.setVisibility(View.INVISIBLE);

        //Delete Button Listener
        buttonDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(getString(R.string.edit_note_delete_title));
                builder.setMessage(getString(R.string.edit_note_delete_message));
                builder.setPositiveButton(getString(R.string.edit_note_delete_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), getString(R.string.edit_note_delete_toast_message),
                                Toast.LENGTH_LONG).show();
                        deleteNote(view);
                    }
                });
                builder.setNegativeButton(getString(R.string.edit_note_delete_negative), null);
                AlertDialog ad = builder.create();
                ad.setCanceledOnTouchOutside(true);
                ad.show();
            }
        });
    }

    public void testAlarm(int hour, int minute){
        Calendar c= Calendar.getInstance();

//        calendar.set(Calendar.MONTH, month - 1);
//        calendar.set(Calendar.YEAR, year);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));

        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        Intent myIntent = new Intent(EditNoteActivity.this, NotificationBroadcaster.class);
        myIntent.putExtra("Title", editTextTitle.getText().toString());
        myIntent.putExtra("Content", editTextContent.getText().toString());
        pendingIntent = PendingIntent.getBroadcast(EditNoteActivity.this, 0, myIntent,0);

        //AlarmManager.RTC does not wake the device up (RTC_WAKEUP will) and will not be delivered until the device wakes up
        alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarms(){
        try {
            alarmManager.cancel(pendingIntent);
            Log.d(TAG, "Alarms cancelled");
        } catch (Exception e) {
            Log.e(TAG, "AlarmManager not canceled. " + e.toString());
        }
    }

    public void textToSpeech(){
            tts = new TextToSpeech(EditNoteActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.US);
                tts.speak(editTextTitle.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                tts.speak(editTextContent.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });
    }

    public void editNote (View view){

        //Gets edited text
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();


        //Calls DBHandler with the new object
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title, content);
        dbHandler.updateNote(oldtitle, noteObject);

        //Changes view
        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
        //dbHandler.deleteNote(noteObject.getTitle().toString
        // ());
    }
    public void deleteNote (View view){

        //Gets the text
        String title = editTextTitle.getText().toString();
        String content = editTextContent.getText().toString();

        //Calls DBHandler
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        NoteObject noteObject = new NoteObject(title, content);
        dbHandler.deleteNote(title);

        //Changes view
        Intent i = new Intent(getApplicationContext(), OverviewActivity.class);
        startActivity(i);
    }

    public void showTimeAndDatePickerDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment newFragment = new TimeAndDatePickerFragment();
        newFragment.show(fm, "datePickerHOLO");
    }
    public void seeLocation() {
        if(oldcontent != null){
            if(oldcontent.contains("No location found")){
                Log.d("DEBUG", "Works");
                Toast.makeText(getApplicationContext(), "Could not find any location",
                        Toast.LENGTH_LONG).show();
            }
            else{
                String[] parts;
                if (oldcontent.contains("Cordinates: ")) {
                    parts = oldcontent.split("Cordinates: ");
                    cordinates = parts[1];
                }
                Intent i = new Intent(getApplicationContext(), Maps.class);
                i.putExtra("cordinates",oldcontent);
                startActivity(i);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_edit:
                editTextTitle.setFocusableInTouchMode(true);
                editTextContent.setFocusableInTouchMode(true);
                buttonSaveEdit.setVisibility(View.VISIBLE);
                break;
            case R.id.action_addNotification:
                showTimeAndDatePickerDialog();
                break;
            case R.id.action_t2p:
                textToSpeech();
                break;
            case R.id.action_removeNotification:
                cancelAlarms();
                break;
            case R.id.action_seeLocation:
                seeLocation();
            default:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //'add notification' fragment
    public static class TimeAndDatePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        private int hour;
        private int minute;

        private int callCount = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            //set current time as default values
            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            final TimePickerDialog dl = new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

            //create new instance of dialog
            return dl;
        }

        //onTimeSet is called twice for some reason 'callCount' fixes this
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.d(TAG, "HOUR: " + Integer.toString(hourOfDay));
            if(callCount == 0){
                ((EditNoteActivity)getActivity()).testAlarm(hourOfDay,minute);
                Log.d(TAG, "ADDED " + Integer.toString(callCount));
            } else {
                Log.d(TAG,"NOT ADDED " + Integer.toString(callCount));
            }
            callCount++;
        }
    }
}
