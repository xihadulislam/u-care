package com.project.ucare.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.ucare.R;
import com.project.ucare.main.MainActivity;
import com.project.ucare.main.createprofile.CreateProfileActivity;
import com.project.ucare.models.Profile;
import com.project.ucare.schedule.ScheduleActivity;
import com.project.ucare.splash.SplashActivity;
import com.xihad.androidutils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment implements ProfileAdapter.ProfileListener {


    FloatingActionButton floatingActionButton;

    RecyclerView recyclerView;

    ProfileAdapter adapter;
    ProgressBar progressBar;
    TextView noData, iconText, labelName, labelDate;

    LinearLayout rootView;

    ConstraintLayout profileRoot;


    private List<Profile> profileList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        floatingActionButton = root.findViewById(R.id.fab);
        progressBar = root.findViewById(R.id.progressBar);
        recyclerView = root.findViewById(R.id.profileList);
        noData = root.findViewById(R.id.noData);
        iconText = root.findViewById(R.id.iconText);
        labelName = root.findViewById(R.id.labelName);
        labelDate = root.findViewById(R.id.labelDate);
        rootView = root.findViewById(R.id.rootView);
        profileRoot = root.findViewById(R.id.profileRoot);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set Adapter
        adapter = new ProfileAdapter(getActivity());
        adapter.setProfileListener(HomeFragment.this);
        recyclerView.setAdapter(adapter);


        getProfile();
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


    private void getProfile() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseDatabase.getInstance().getReference().child("User").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {
                    Profile profile = snapshot.getValue(Profile.class);
                    setProfile(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setProfile(Profile profile) {


        rootView.setVisibility(View.VISIBLE);
        labelName.setText(profile.getName());
        labelDate.setText("Birth Date: " + profile.getBirth_date() + " || " + "Gender: " + profile.getGender());
        iconText.setText(AndroidUtils.Companion.splitString(profile.getName(), 1));

        profileRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("profile", profile);
                startActivity(intent);

            }
        });


    }

    private void getData() {

        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseDatabase.getInstance().getReference().child("Profile").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChildren()) {

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

    @Override
    public void onProfileClick(Profile profile) {
        Intent intent = new Intent(getActivity(), ScheduleActivity.class);
        intent.putExtra("profile", profile);
        startActivity(intent);

    }
}