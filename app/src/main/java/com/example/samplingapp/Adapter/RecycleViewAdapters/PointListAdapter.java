package com.example.samplingapp.Adapter.RecycleViewAdapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.Location.BNaviMainActivity;
import com.example.samplingapp.utils.Location.Location;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.ViewHolder> {

    private ArrayList<PointDetailData> pointData;
    private Activity context;
    private ItemClickListener listener;
    private Dialog loadingDialog;

    public PointListAdapter(ArrayList<PointDetailData> pointData1, Activity context) {
        pointData = pointData1;
        this.context = context;
    }

    public PointListAdapter(ArrayList<PointDetailData> pointData1, ItemClickListener listener, Activity context) {
        pointData = pointData1;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_point_list, parent, false);
        return new PointListAdapter.ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pointName.setText(pointData.get(position).getName());
        holder.pointState.setText(pointData.get(position).getStatusName());
        if (pointData.get(position).getStatus().equals("0")) {
            //默认是未采样，所以只判断一边
            holder.pointState.setTypeface(Typeface.MONOSPACE);
            holder.pointState.setTextColor(Color.parseColor("#ff8a00"));
        }
        holder.stateImg.setOnClickListener(view -> {
            showLoadingDialog();
            Location.beginToGetNowLocation2(context, new BDAbstractLocationListener() {
                @Override
                public void onReceiveLocation(BDLocation bdLocation) {
                    dismissLoadingDialog();
                    Intent intent = new Intent();
                    intent.putExtra("Latitude", bdLocation.getLatitude());
                    intent.putExtra("Longitude", bdLocation.getLongitude());
                    intent.putExtra("Latitude1", Double.parseDouble(pointData.get(position).getLatitude()));
                    intent.putExtra("Longitude1", Double.parseDouble(pointData.get(position).getLongitude()));
                    intent.setClass(context, BNaviMainActivity.class);
                    context.startActivity(intent);
                }
            });
        });
        if (listener != null) {
            holder.itemView.setOnClickListener(view -> listener.onClick(pointData.get(position)));
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
            pointName = itemView.findViewById(R.id.point_name);
            pointState = itemView.findViewById(R.id.point_state);
            stateImg = itemView.findViewById(R.id.state_img);
        }
    }

    public interface ItemClickListener {
        void onClick(PointDetailData data);
    }

    /**
     * 显示加载中的图标
     */
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            View layout = context.getLayoutInflater().inflate(R.layout.dialog_loading
                    , null);
            loadingDialog = new Dialog(context);
            loadingDialog.setContentView(layout);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(true);
            loadingDialog.setOnCancelListener(dialog -> {
            });
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        loadingDialog.dismiss();
    }
}
