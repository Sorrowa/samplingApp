package com.example.samplingapp.Adapter.RecycleViewAdapters.PhotoSelect;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.samplingapp.R;
import com.example.samplingapp.mvp.ui.PreviewActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnvironmentAdapter extends RecyclerView.Adapter<EnvironmentAdapter.ViewHolder> {

    private Activity context;
    private List<String> photos=new ArrayList<>();

    public EnvironmentAdapter(Activity context, List<String> photos){
        this.context=context;
        this.photos.addAll(photos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo
                ,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File file = new File(photos.get(position));
        if(file.exists()){
            Bitmap bm = BitmapFactory.decodeFile(photos.get(position));
            holder.imageView.setImageBitmap(bm);
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, PreviewActivity.class);
            intent.putExtra("path",photos.get(position));
            intent.putExtra("type","1");
            context.startActivityForResult(intent,PreviewActivity.PREVIEWACTIVITY);
        });
    }

    public void setPhotos(List<String> photos) {
        this.photos.clear();
        this.photos.addAll(photos);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_photo);
        }
    }
}
