package com.project.ucare.screens.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.ucare.models.Schedule;

import java.util.Random;

public class AlarmHandler {


    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    Context context;

    public AlarmHandler(Context context, Schedule schedule) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, MyBroadCastReceiver.class);
        alarmIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        alarmIntent.putExtra("schedule", new Gson().toJson(schedule));

        pendingIntent = PendingIntent.getBroadcast(context, new Random().nextInt(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public void startAlarm(Long triggerAt) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAt, pendingIntent);
        }

    }

    public void cancelAlarm() {
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context, "Alarm Cancelled", Toast.LENGTH_LONG).show();
    }


}
