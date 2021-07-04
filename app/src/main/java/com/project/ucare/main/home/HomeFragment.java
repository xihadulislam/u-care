package com.project.ucare.main.home;

import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ucare.R;
import com.project.ucare.main.MainActivity;
import com.project.ucare.main.createprofile.CreateProfileActivity;
import com.project.ucare.models.Profile;
import com.project.ucare.splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;

    ProfileAdapter adapter;
    ProgressBar progressBar;
    TextView noData;

    private List<Profile> profileList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        floatingActionButton = root.findViewById(R.id.fab);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.profileList);
        noData = root.findViewById(R.id.noData);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        adapter = new ProfileAdapter(getActivity());
        recyclerView.setAdapter(adapter);


        getData();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
                startActivity(intent);

            }
        });


        return root;
    }

    private void getData() {

        FirebaseDatabase.getInstance().getReference().child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null) {

                    profileList.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Profile profile = ds.getValue(Profile.class);
                        profileList.add(profile);

                    }
                    progressBar.setVisibility(View.GONE);

                    if (profileList.isEmpty()) {
                        noData.setVisibility(View.VISIBLE);
                    } else {
                        noData.setVisibility(View.GONE);
                    }

                    adapter.setList(profileList);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}