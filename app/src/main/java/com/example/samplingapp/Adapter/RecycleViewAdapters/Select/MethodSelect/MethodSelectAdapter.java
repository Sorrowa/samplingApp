package com.example.samplingapp.Adapter.RecycleViewAdapters.Select.MethodSelect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.core.Entity.Data.SampMethodData;
import com.example.samplingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 方法选择适配器
 */
public class MethodSelectAdapter extends RecyclerView.Adapter<MethodSelectAdapter.ViewHolder>{


    private List<SampMethodData> datas;
    private Context context;
    private onItemClickListener listener;

    public MethodSelectAdapter(Context context
            , List<SampMethodData> dataList
            , onItemClickListener listener){
        datas=dataList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_method,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.methodName.setText(datas.get(position).getSampMethodName());
        holder.itemView.setOnClickListener(view -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

//    public void setDatas(List<SampMethodData> datas) {
//        this.datas.clear();
//        this.datas.addAll(datas);
//    }

    public interface onItemClickListener{
        void onClick(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView methodName;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            methodName=itemView.findViewById(R.id.method_name);
        }
    }
}
