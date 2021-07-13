package com.project.ucare.common;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import java.util.Timer;
import java.util.TimerTask;

public class Utils {

    public static void cancelNotification(Context context, int id) {
        NotificationManager notificacaoManager = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);
        notificacaoManager.cancel(id);
    }

    private static Ringtone ringtone;

    public static void playRing(Context context) {

        //  Uri alarmUri = Uri.parse("content://media/internal/audio/media");
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                ringtone.stop();
                t.cancel();
            }
        }, 30000);
    }

    public static void stopRing() {
        if (ringtone != null) {
            ringtone.stop();
        }
    }


}
