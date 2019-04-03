package com.example.samplingapp.Adapter.ViewPagerAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.core.Entity.Data.PointData;
import com.example.samplingapp.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class PointViewPagerAdapter extends PagerAdapter {

    private Context context;
    private String[] name;
    private ArrayList<PointData> pointData;

    public PointViewPagerAdapter(Context context
            , String[] name
            , ArrayList<PointData> pointDatas){
        this.context=context;
        this.name=name;
        this.pointData=pointDatas;
    }

    @Override
    public int getCount() {
        return name.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=View.inflate(context,R.layout.recy_point_list,null);
        RecyclerView recyclerView=view.findViewById(R.id.recy);
        //todo:分position更新数据,暂时就不更新了
        ArrayList<PointData> pointData1=new ArrayList<>();
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}
