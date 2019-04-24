package com.example.samplingapp.Adapter.ViewPagerAdapters;

import com.example.samplingapp.Base.BaseFragment;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * 待采样与已采样
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private int len=0;
    private String[] name;

    public MainViewPagerAdapter(FragmentManager manager, List<Fragment> fragments
            ,String[] name){
        super(manager);
        this.fragments=fragments;
        this.name=name;
        len=fragments.size();
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return len;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return name[position];
    }
}

