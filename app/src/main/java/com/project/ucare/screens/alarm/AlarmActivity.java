package com.project.ucare.screens.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.project.ucare.R;
import com.project.ucare.common.Utils;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Utils.cancelNotification(this, 1);
        Utils.stopRing();


    }
}