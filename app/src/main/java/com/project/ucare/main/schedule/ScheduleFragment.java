package com.project.ucare.main.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ucare.R;
import com.project.ucare.main.home.ProfileAdapter;
import com.project.ucare.models.Profile;
import com.project.ucare.models.Schedule;

import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment {

    RecyclerView recyclerView;

    ScheduleAdapter adapter;
    ProgressBar progressBar;
    TextView noData;

    private List<Schedule> scheduleList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_schedule, container, false);


        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.profileList);
        noData = root.findViewById(R.id.noData);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        adapter = new ScheduleAdapter(getActivity());
        recyclerView.setAdapter(adapter);


        getData();


        return root;
    }


    private void getData() {

        FirebaseDatabase.getInstance().getReference().child("Schedule").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null) {

                    scheduleList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Schedule schedule = ds.getValue(Schedule.class);
                        scheduleList.add(schedule);

                    }
                    progressBar.setVisibility(View.GONE);

                    if (scheduleList.isEmpty()) {
                        noData.setVisibility(View.VISIBLE);
                    } else {
                        noData.setVisibility(View.GONE);
                    }

                    adapter.setList(scheduleList);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}