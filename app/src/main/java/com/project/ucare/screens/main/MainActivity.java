package com.project.ucare.screens.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ucare.R;
import com.project.ucare.common.Utils;
import com.project.ucare.db.ProfileHandler;
import com.project.ucare.db.ScheduleHandler;
import com.project.ucare.models.Profile;
import com.project.ucare.models.Schedule;
import com.xihad.androidutils.AndroidUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ProfileHandler profileHandler;
    ScheduleHandler scheduleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidUtils.Companion.init(this);
        profileHandler = new ProfileHandler(this);
        scheduleHandler = new ScheduleHandler(this);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_emergencyContact, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        onCall();


        sync();

    }

    private void sync() {
        getProfile();
        syncProfiles();
        syncSchedules();
    }

    private void getProfile() {
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference().child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {
                    Profile profile = snapshot.getValue(Profile.class);
                    if (profile != null)
                        profileHandler.addProfile(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void syncProfiles() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Profile> profileList = new ArrayList<>();
                List<Profile> profileListLocal;

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Profile profile = ds.getValue(Profile.class);
                    profileList.add(profile);
                }

                profileListLocal = profileHandler.getProfileList(Utils.firebaseUserId());
                boolean flg = true;
                for (Profile profile : profileList) {
                    flg = true;
                    for (Profile profileLocal : profileListLocal) {
                        if (profile.getId().equalsIgnoreCase(profileLocal.getId())) {
                            flg = false;
                            break;
                        }
                    }

                    if (flg) {
                        profileHandler.addProfile(profile);
                        Log.d("aaa", "onDataChange:  local");
                    }

                }


                for (Profile profileLocal : profileListLocal) {
                    flg = true;
                    for (Profile profile : profileList) {
                        if (profileLocal.getId().equalsIgnoreCase(profile.getId())) {
                            flg = false;
                            break;
                        }
                    }
                    if (flg) {
                        FirebaseDatabase.getInstance().getReference().child("Profile").child(userId).child(profileLocal.getId()).setValue(profileLocal);
                        Log.d("aaa", "onDataChange:  firebase");
                    }

                }

                profileListLocal = profileHandler.getProfileList(Utils.firebaseUserId());

                for (Profile profileLocal : profileListLocal) {
                    flg = true;
                    for (Profile profile : profileList) {
                        if (profileLocal.getId().equalsIgnoreCase(profile.getId())) {
                            if (profileLocal.getUpdatedTime() > profile.getUpdatedTime()) {
                                FirebaseDatabase.getInstance().getReference().child("Profile").child(userId).child(profileLocal.getId()).setValue(profileLocal);
                            }
                            break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void syncSchedules() {


        FirebaseDatabase.getInstance().getReference().child("Schedule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Schedule> scheduleList = new ArrayList<>();
                List<Schedule> scheduleListLocal;


                for (DataSnapshot ds : snapshot.getChildren()) {
                    Schedule schedule = ds.getValue(Schedule.class);
                    scheduleList.add(schedule);
                }

                scheduleListLocal = scheduleHandler.getAllSchedules();


                Log.d(TAG, "onDataChange: " + scheduleList.size());
                Log.d(TAG, "onDataChange: " + scheduleListLocal.size());

                boolean flg = true;
                for (Schedule schedule : scheduleList) {
                    flg = true;
                    for (Schedule scheduleLocal : scheduleListLocal) {
                        if (schedule.getId().equalsIgnoreCase(scheduleLocal.getId())) {
                            flg = false;
                            break;
                        }

                    }

                    if (flg) {
                        scheduleHandler.addSchedule(schedule);
                    }
                }

                for (Schedule scheduleLocal : scheduleListLocal) {
                    flg = true;
                    for (Schedule schedule : scheduleList) {
                        if (scheduleLocal.getId().equalsIgnoreCase(schedule.getId())) {
                            flg = false;
                            break;
                        }
                    }

                    if (flg) {
                        FirebaseDatabase.getInstance().getReference().child("Schedule").child(scheduleLocal.getId()).setValue(scheduleLocal);
                        Log.d("aaa", "onDataChange:  firebase");
                    }
                }

                scheduleListLocal = scheduleHandler.getAllSchedules();

                for (Schedule scheduleLocal : scheduleListLocal) {
                    flg = true;
                    for (Schedule schedule : scheduleList) {
                        if (scheduleLocal.getId().equalsIgnoreCase(schedule.getId())) {
                            if (scheduleLocal.getUpdatedTime() > scheduleLocal.getUpdatedTime()) {
                                FirebaseDatabase.getInstance().getReference().child("Schedule").child(scheduleLocal.getId()).setValue(scheduleList);
                            }
                            break;
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void onCall() {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        }
    }

}