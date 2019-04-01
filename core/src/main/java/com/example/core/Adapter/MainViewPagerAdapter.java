package com.example.core.Adapter;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

/**
 * 待采样与已采样
 */
public class MainViewPagerAdapter extends PagerAdapter {

    private List<Fragment> fragments;
    private int len=0;

    public MainViewPagerAdapter(List<Fragment> fragments){
        this.fragments=fragments;
        len=fragments.size();
    }

    @Override
    public int getCount() {
        return len;
    }



    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
}
