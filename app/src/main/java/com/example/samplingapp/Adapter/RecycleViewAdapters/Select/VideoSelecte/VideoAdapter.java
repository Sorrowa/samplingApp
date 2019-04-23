package com.example.samplingapp.Adapter.RecycleViewAdapters.Select.VideoSelecte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.R;
import com.example.samplingapp.mvp.ui.VideoActivity;
import com.example.samplingapp.utils.BaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private List<String> paths=new ArrayList<>();
    private SamplingFormActivity context;

    public VideoAdapter(List<String> paths, SamplingFormActivity context){
        this.paths.addAll(paths);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = new File(paths.get(position));
        if(file.exists()){
            Bitmap bm=BaseUtil.getVideoThumb(paths.get(position));
            if (bm!=null){
                holder.item_video.setImageBitmap(bm);
            }else{
//                context.showToast("视频文件加载失败");
            }
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, VideoActivity.class);
            intent.putExtra("path",paths.get(position));
            context.startActivityForResult(intent,VideoActivity.VIDEO_ACTIVITY);
        });

    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public void setPaths(List<String> paths) {
        this.paths.clear();
        this.paths.addAll(paths);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView item_video;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_video=itemView.findViewById(R.id.item_video);
        }
    }
}
