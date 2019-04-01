package com.example.samplingapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.samplingapp.Activities.PersonalActivity;
import com.example.samplingapp.Base.BaseActivity;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.view_pager_main)
    ViewPager viewPager;
    @BindView(R.id.top_tab)
    TabLayout tabLayout;
    @BindView(R.id.person_center)
    ImageView personCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
        initViewPager();
    }

    private void initViewPager() {
    }

    private void initTab() {
//        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab=tabLayout.newTab();
        tab.setText("待采样");
        TabLayout.Tab tab1=tabLayout.newTab();
        tab1.setText("已采样");
        tabLayout.addTab(tab);
        tabLayout.addTab(tab1);
    }

    @OnClick(R.id.person_center)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.person_center:
                Intent intent=new Intent(this, PersonalActivity.class);
                startActivity(intent);
        }

    }
}
