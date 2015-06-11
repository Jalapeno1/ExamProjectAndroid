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

        //If set FLAG_ACTIVITY_CLEAR_TOP, and the activity being launched is already running in the current task,
        // then instead of launching a new instance of that activity, all of the other
        // activities on top of it will be closed and this Intent will be delivered to
        // the (now on top) old activity as a new Intent.
        //If set FLAG_ACTIVITY_SINGLE_TOP, the activity will not be launched if it is already running at the top of the history stack.
        //intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //creates pending intent and updates current pending intent (if there is one)
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);

        //cancels notification when clicked
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //sets content and intent
        notification.setLatestEventInfo(this.getApplicationContext(), title, content, pendingNotificationIntent);
        //add vibrate and sound
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;

        mManager.notify(0, notification);


//    private void addNotification() {
//
//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("Notifications Example")
//                        .setContentText("This is a test notification")
//                        .setWhen();
//
//        Intent ni = new Intent(this, EditNoteActivity.class);
//        ni.putExtra("Title", "sdf");
//        ni.putExtra("Content","sd");
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, ni,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//
//        // Add as notification
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(1, builder.build());
//    }
    }



}
