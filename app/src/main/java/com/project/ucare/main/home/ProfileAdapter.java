package com.project.ucare.main.home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ucare.R;
import com.project.ucare.models.Profile;
import com.xihad.androidutils.AndroidUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {


    private List<Profile> data;
    private Activity context;

    public ProfileAdapter(Activity context) {
        this.context = context;

    }

    public void setList(List<Profile> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    interface ProfileListener {
        void onProfileClick(Profile profile);
    }

    ProfileListener profileListener;

    public void setProfileListener(ProfileListener profileListener) {
        this.profileListener = profileListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AndroidUtils.Companion.init(context);

        Profile profile = data.get(position);

        holder.labelName.setText(profile.getName());
        holder.labelDate.setText("Birth Date: " + profile.getBirth_date() + " \n" + "Gender: " + profile.getGender());
        holder.iconText.setText(AndroidUtils.Companion.splitString(profile.getName(), 1));
        //  holder.circleImageView.setCircleBackgroundColor(AndroidUtils.Companion.getRandomColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileListener!=null){
                    profileListener.onProfileClick(profile);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView labelName;
        private TextView labelDate;
        private TextView iconText;
        private CircleImageView circleImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            labelName = view.findViewById(R.id.labelName);
            labelDate = view.findViewById(R.id.labelDate);
            iconText = view.findViewById(R.id.iconText);
            circleImageView = view.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
