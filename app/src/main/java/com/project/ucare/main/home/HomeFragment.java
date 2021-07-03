package com.project.ucare.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ucare.R;
import com.project.ucare.main.MainActivity;
import com.project.ucare.main.createprofile.CreateProfileActivity;
import com.project.ucare.splash.SplashActivity;


public class HomeFragment extends Fragment {



    FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        floatingActionButton = root.findViewById(R.id.fab);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateProfileActivity.class);
                startActivity(intent);

            }
        });



        return root;
    }
}