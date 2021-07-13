package com.project.ucare.screens.alarm;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.project.ucare.R;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID1";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.setSound(null, null);
        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {


        Intent fullScreenIntent = new Intent(this, AlarmActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent actionSn = new Intent(this, NotificationActionReceiver.class);
        actionSn.putExtra("id", 1);
        actionSn.putExtra("type", "snooze");
        PendingIntent actionSnPendingIntent = PendingIntent.getBroadcast(this, 0,
                actionSn, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent actionTake = new Intent(this, NotificationActionReceiver.class);
        actionTake.putExtra("id", 1);
        actionTake.putExtra("type", "taking_now");
        PendingIntent actionTakePendingIntent = PendingIntent.getBroadcast(this, 0,
                actionTake, PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                //  .setTimeoutAfter(2000)
                .addAction(R.mipmap.ic_launcher, "Snooze", actionSnPendingIntent)
                .addAction(R.mipmap.ic_launcher, "Taking Now", actionTakePendingIntent)
                .setContentTitle("Alarm!")
                .setContentText("Your AlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\nAlarmManager is working.\n")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(false)
                .setSound(null)
                .setDefaults(0)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(fullScreenPendingIntent, true);

    }
}
