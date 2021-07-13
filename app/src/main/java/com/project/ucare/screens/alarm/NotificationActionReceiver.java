package com.project.ucare.screens.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.project.ucare.common.Utils;

public class NotificationActionReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("id",1);

        Utils.cancelNotification(context, id);
        Utils.stopRing();

        String type = intent.getStringExtra("type");

        Log.d("qqq", "onReceive: "+type);

    }
}
