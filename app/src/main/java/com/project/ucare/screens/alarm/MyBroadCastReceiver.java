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
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;
import com.project.ucare.R;
import com.project.ucare.common.Utils;
import com.project.ucare.models.Schedule;

import java.util.Timer;
import java.util.TimerTask;

import static com.project.ucare.common.Utils.playRing;

public class MyBroadCastReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        Schedule schedule = new Gson().fromJson(intent.getStringExtra("schedule"), Schedule.class);

        if (schedule != null) {
            NotificationHelper notificationHelper = new NotificationHelper(context, schedule);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            notificationHelper.getManager().notify(schedule.getAlarm().getId(), nb.build());
            Utils.playRing(context);

            //   startAgain(context, schedule);

        }

        Log.d("qqq", "onReceive:  call " + schedule.getId());


    }


    void startAgain(Context context, Schedule schedule) {

        String endDate = Utils.dateToString(Utils.incrementDate(Utils.stringToDate(schedule.getStartDate()), Integer.parseInt(schedule.getDuration())));

        Log.d("qqq", "startAgain: " + endDate);

        if (Utils.isDateValid(schedule.getStartDate()) && !Utils.isDateValid(endDate)) {
            Log.d("qqq", "startAgain:  valid");
            boolean flag = false;
            for (String day : schedule.getAlarm().getDays()) {
                if (Utils.getToday().equalsIgnoreCase(day)) {
                    flag = true;
                }
            }
            if (flag) {
                Log.d("qqq", "startAgain:  flag " + flag);
               // AlarmHandler handler = new AlarmHandler(context, schedule);
              //  handler.startNextAlarm(schedule.getAlarm().getHour(), schedule.getAlarm().getMinute());

            } else {

                Log.d("qqq", "startAgain:  flag " + flag);
            }


        } else {
            Log.d("qqq", "startAgain:  not  valid");
        }

    }


}

