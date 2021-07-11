package com.project.ucare.screens.alarm;


import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.project.ucare.R;

public class MyService extends IntentService {


    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent alarmIntent = new Intent(this, MyBroadCastReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, pendingIntent);

        Log.d("qqq", "onHandleIntent:  call");

//        Intent intent1 = new Intent(this, AlarmActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent1.setAction("com.newsportal.NewsDetailsActivity.INTENT");
//        startActivity(intent1);




    }




}
