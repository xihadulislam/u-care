package com.project.ucare.screens.alarm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.project.ucare.R;

public class MyBroadCastReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

//        Intent serviceIntent = new Intent(context, MyService.class);
//        context.startService(serviceIntent);
//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent serviceIntent = new Intent(context, MyService.class);
//            context.startService(serviceIntent);
//        } else {
//            Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
//        }


//        Intent intent1 = new Intent(context, AlarmActivity.class);
//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        sendNotification(context, "title", "message", intent, 0);
//

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());

//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        if (alarmUri == null) {
//            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
//        ringtone.play();

        Log.d("qqq", "onReceive:  call");
    }


}

