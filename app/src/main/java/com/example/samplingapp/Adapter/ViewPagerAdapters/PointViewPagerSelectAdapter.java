package com.example.samplingapp.Adapter.ViewPagerAdapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.Adapter.RecycleViewAdapters.PointListAdapter;
import com.example.samplingapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class PointViewPagerSelectAdapter extends PagerAdapter implements PointListAdapter.ItemClickListener {
    private Context context;
    private String[] name;
    private List<PointDetailData> pointData;
    private List<PointListAdapter> adapters;
    private OnClickListener listener;


    public PointViewPagerSelectAdapter(Context context
            , String[] name
            , List<PointDetailData> pointDatas
            ,OnClickListener listener) {
        this.context = context;
        this.name = name;
        if (pointDatas != null) {
            this.pointData = pointDatas;
        }else{
            this.pointData=new ArrayList<>();
        }
        adapters=new ArrayList<>();
        this.listener=listener;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.recy_point_list, null);

        RecyclerView recyclerView = view.findViewById(R.id.recy);
        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        ArrayList<PointDetailData> pointData1 = new ArrayList<>();

        switch (position) {
            case 0:
                pointData1.addAll(pointData);
                if (adapters.size()<=0||adapters.get(0)==null){
                    adapters.add(new PointListAdapter(pointData1,this, (Activity) context));
                }
                recyclerView.setAdapter(adapters.get(0));
                break;
            case 1:
                for (PointDetailData detailData:pointData){
                    if (detailData.getStatus().equals("0")){
                        pointData1.add(detailData);
                    }
                }
                if (adapters.size()<=1||adapters.get(1)==null){
                    adapters.add(new PointListAdapter(pointData1,this, (Activity) context));
                }
                recyclerView.setAdapter(adapters.get(1));
                break;
            case 2:
                for (PointDetailData detailData:pointData){
                    if (detailData.getStatus().equals("1")){
                        pointData1.add(detailData);
                    }
                }
                if (adapters.size()<=2||adapters.get(2)==null){
                    adapters.add(new PointListAdapter(pointData1,this, (Activity) context));
                }
                recyclerView.setAdapter(adapters.get(2));
                break;
        }
        //添加view
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void notifyRecy(){

        if (adapters.size()!=0){
            int i=0;
            for (PointListAdapter adapter:adapters){
                ArrayList<PointDetailData> pointData1 = new ArrayList<>();
                switch (i){
                    case 0:
                        pointData1.addAll(pointData);
                        name[0]=name[0]+"("+pointData.size()+")";
                        adapter.setPointData(pointData1);
                        break;
                    case 1:
                        for (PointDetailData detailData:pointData){
                            if (detailData.getStatus().equals("0")){
                                pointData1.add(detailData);
                            }
                        }
                        name[1]=name[1]+"("+pointData1.size()+")";
                        adapter.setPointData(pointData1);
                        break;
                    case 2:
                        for (PointDetailData detailData:pointData){
                            if (detailData.getStatus().equals("1")){
                                pointData1.add(detailData);
                            }
                        }
                        name[2]=name[2]+"("+pointData1.size()+")";
                        adapter.setPointData(pointData1);
                        break;
                }
                adapter.notifyDataSetChanged();
                notifyDataSetChanged();
                i++;
            }
        }
    }

    public void setData(List<PointDetailData> datas) {
        this.pointData.clear();
        this.pointData.addAll(datas);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    //选择的回调事件
    @Override
    public void onClick(PointDetailData data) {
        listener.onClick(data);
    }

    public interface OnClickListener{
        void onClick(PointDetailData data);
    }
}
