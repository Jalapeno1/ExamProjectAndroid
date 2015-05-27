package com.example.jonas.examproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Jonas on 19-05-2015.
 */
public class AlarmService extends Service {

    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        //Could use NotifictionCompat.Builder to construct notification (it's newer)

        super.onStart(intent, startId);

        String title = intent.getStringExtra("Title");
        String content = intent.getStringExtra("Content");

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), EditNoteActivity.class);
        intent1.putExtra("TitleToEdit", title);
        intent1.putExtra("ContentToEdit", content);

        Notification notification = new Notification(R.mipmap.ic_launcher,"Notification", System.currentTimeMillis());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.setLatestEventInfo(this.getApplicationContext(), title, content, pendingNotificationIntent);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;

        mManager.notify(0, notification);
    }



}
