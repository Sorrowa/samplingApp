package com.example.samplingapp.Adapter.RecycleViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Entity.Data.ProjectData;
import com.example.samplingapp.Activities.TaskDetailActivity;
import com.example.samplingapp.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SamplingTaskAdapter extends RecyclerView.Adapter<SamplingTaskAdapter.ViewHolder> {


    private List<ProjectData> datas;
    private Context context;
    private String type="1";

    public SamplingTaskAdapter(List<ProjectData> datas){
        this.datas=datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_main_undo_task,parent,false);
        context=parent.getContext();
        return new ViewHolder(layout);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.taskDate.setText(
                datas.get(position).getBeginTime()
                        +"--"
                        +datas.get(position).getEndTime());
        holder.taskName.setText(datas.get(position).getProjectName());
        holder.pointPercentage.setText("点位："
                +datas.get(position).getSampCount()+"/"
                +datas.get(position).getTotalPoint());
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, TaskDetailActivity.class);
            intent.putExtra("type",type);
            intent.putExtra("task",datas.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setType(String type) {
        this.type = type;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskName;
        TextView pointPercentage;
        TextView taskDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName=itemView.findViewById(R.id.task_name);
            pointPercentage=itemView.findViewById(R.id.task_point);
            taskDate=itemView.findViewById(R.id.task_date);
        }
    }

    public void setDatas(List<ProjectData> datas){
        this.datas.clear();
        this.datas.addAll(datas);
    }


}
