package com.project.ucare.screens.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.project.ucare.R;
import com.project.ucare.db.ScheduleHandler;
import com.project.ucare.screens.medicine.AddMedicineActivity;
import com.project.ucare.models.Profile;
import com.project.ucare.models.Schedule;
import com.xihad.androidutils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScheduleActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    ScheduleAdapter adapter;
    ProgressBar progressBar;
    TextView noData, iconText, labelName;

    private List<Schedule> scheduleList = new ArrayList<>();

    Profile profile;

    FloatingActionButton floatingActionButton;
    ScheduleHandler scheduleHandler;
    String userId = "";

    private List<Schedule> scheduleListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        try {
            profile = (Profile) getIntent().getSerializableExtra("profile");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (AndroidUtils.sharePrefSettings.getStringValue("pro").equalsIgnoreCase("")) {
            userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        } else {
            userId = AndroidUtils.sharePrefSettings.getStringValue("pro");
        }

        scheduleHandler = new ScheduleHandler(this);

        floatingActionButton = findViewById(R.id.fab);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.profileList);
        noData = findViewById(R.id.noData);
        iconText = findViewById(R.id.iconText);
        labelName = findViewById(R.id.labelName);


        if (profile != null) {
            labelName.setText(profile.getName());
            iconText.setText(AndroidUtils.Companion.splitString(profile.getName(), 1));
        }


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ScheduleActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        adapter = new ScheduleAdapter(ScheduleActivity.this);
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, AddMedicineActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocalData();
    }

    private void getLocalData() {

        scheduleListData = scheduleHandler.getSchedules(userId);
        progressBar.setVisibility(View.GONE);

        if (scheduleListData.isEmpty()) {
            noData.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.GONE);
        }

        adapter.setList(scheduleListData);

    }

}