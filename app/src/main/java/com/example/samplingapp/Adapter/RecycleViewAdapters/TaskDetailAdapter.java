package com.example.samplingapp.Adapter.RecycleViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Entity.Data.PointData;
import com.example.samplingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.ViewHolder> {


    private List<PointData> data;
    private Context context;

    public TaskDetailAdapter(List<PointData> data){
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_task_detail,parent,false);
        context=parent.getContext();
        return new TaskDetailAdapter.ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(data.get(position));
        //跳转到详情界面
        holder.itemView.setOnClickListener(view -> {});
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pointName;
        private TextView personName;
        private TextView pointDate;
        private TextView pointState;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pointState=itemView.findViewById(R.id.point_state);
            pointDate=itemView.findViewById(R.id.point_date);
            personName=itemView.findViewById(R.id.person_name);
            pointName=itemView.findViewById(R.id.point_name);
        }

        public void setData(PointData data){
            pointName.setText(data.getPointName());
            pointDate.setText(data.getActSampTime());
            personName.setText(data.getActSamper());
            pointState.setText(data.getStatusName());
            if (data.getPointSatus().equals("1")){
                pointState.setTextColor(context.getResources().getColor(R.color.red));
            }else
                pointState.setTextColor(Color.parseColor("#3778e6"));
        }
    }
}
