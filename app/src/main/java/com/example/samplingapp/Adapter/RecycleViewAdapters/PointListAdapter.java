package com.example.samplingapp.Adapter.RecycleViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.ViewHolder> {

    private ArrayList<PointDetailData> pointData;
    private Context context;

    public PointListAdapter(ArrayList<PointDetailData> pointData1){
        pointData=pointData1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_point_list,parent,false);
        context=parent.getContext();
        return new PointListAdapter.ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pointName.setText(pointData.get(position).getName());
        holder.pointState.setText(pointData.get(position).getStatusName());
        if (pointData.get(position).getStatus().equals("1")){
            //默认是未采样，所以只判断一边
            holder.stateImg.setImageDrawable(context.getDrawable(R.drawable.point_has));
        }
    }

    public void setPointData(ArrayList<PointDetailData> pointData) {
        this.pointData.clear();
        this.pointData.addAll(pointData);
    }

    @Override
    public int getItemCount() {
        return pointData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView pointName;
        TextView pointState;
        ImageView stateImg;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            pointName=itemView.findViewById(R.id.point_name);
            pointState=itemView.findViewById(R.id.point_state);
            stateImg=itemView.findViewById(R.id.state_img);
        }
    }
}
