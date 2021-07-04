package com.project.ucare.main.schedule;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ucare.R;
import com.project.ucare.models.Schedule;
import com.xihad.androidutils.AndroidUtils;

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
        AndroidUtils.Companion.init(context);
        Schedule schedule = data.get(position);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
