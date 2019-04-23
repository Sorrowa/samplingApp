package com.example.samplingapp.Adapter.RecycleViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Entity.Data.SamplingData;
import com.example.samplingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 查看样品
 */
public class SamplingDetailAdapter extends RecyclerView.Adapter<SamplingDetailAdapter.ViewHolder>{


    private List<SamplingData> datas;
    private Context context;

    public SamplingDetailAdapter(List<SamplingData> datas){
        this.datas=datas;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sampling_detail
                ,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        this.context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.num.setText(datas.get(position).getSampCode());
        holder.name.setText(datas.get(position).getMonNames()+"("+datas.get(position).getSampTypeName()+")");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 更新显示数据
     * @param datas
     */
    public void setDatas(List<SamplingData> datas){
        this.datas.clear();
        this.datas.addAll(datas);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView num;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num=itemView.findViewById(R.id.sampling_num);
            name=itemView.findViewById(R.id.sampling_name);
        }
    }
}
