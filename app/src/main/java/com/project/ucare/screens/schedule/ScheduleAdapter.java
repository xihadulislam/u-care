package com.project.ucare.screens.schedule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ucare.R;
import com.project.ucare.models.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {


    private List<Schedule> data;

    private Activity context;

    public ScheduleAdapter(Activity context) {
        this.context = context;

    }

    public void setList(List<Schedule> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule schedule = data.get(position);


        holder.labelName.setText(schedule.getMedicineName());
        holder.labelIntake.setText(schedule.getIntake());
        holder.labelUnit.setText("Unit : "+schedule.getMedicineUnit());
        holder.labelReminder.setText(schedule.getReminderTime());


    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView labelName;
        private TextView labelIntake;
        private TextView labelUnit;
        private TextView labelReminder;

        public ViewHolder(View view) {
            super(view);

            labelName = view.findViewById(R.id.labelName);
            labelIntake = view.findViewById(R.id.labelIntake);
            labelUnit = view.findViewById(R.id.labelUnit);
            labelReminder = view.findViewById(R.id.labelReminder);

        }


    }
}
