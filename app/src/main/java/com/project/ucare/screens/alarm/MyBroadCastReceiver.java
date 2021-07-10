package com.project.ucare.screens.alarm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.project.ucare.R;

public class MyBroadCastReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {


//        Intent i = new Intent();
//        i.setClassName("com.project.ucare", "com.project.ucare.screens.splash.SplashActivity");
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

//        Intent intent1 = new Intent(context, AlarmActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);


//        Intent serviceIntent = new Intent(context, MyService.class);
//        context.startService(serviceIntent);
//

//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
//            Intent serviceIntent = new Intent(context, MyService.class);
//            context.startService(serviceIntent);
//        } else {
//            Toast.makeText(context.getApplicationContext(), "Alarm Manager just ran", Toast.LENGTH_LONG).show();
//        }

        Log.d("qqq", "onReceive:  call");
    }


    public void show_Notification(Context context) {


    }
}

