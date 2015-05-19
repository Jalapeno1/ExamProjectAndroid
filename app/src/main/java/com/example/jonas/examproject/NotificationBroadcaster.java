package com.example.jonas.examproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Jonas on 19-05-2015.
 */
public class NotificationBroadcaster extends BroadcastReceiver {

    private String TAG = "NotificationBroadcaster";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service1 = new Intent(context, AlarmService.class);
        context.startService(service1);
        Log.d(TAG, "OnReceive");
    }
}
