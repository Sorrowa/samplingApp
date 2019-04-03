package com.example.samplingapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.samplingapp.Activities.PersonalActivity;
import com.example.samplingapp.Adapter.ViewPagerAdapters.MainViewPagerAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.MainPresenter;
import com.example.samplingapp.mvp.Fragment.HasBeenSampledFragment;
import com.example.samplingapp.mvp.Fragment.WaitingForSamplingFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
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

    private MainPresenter presenter;
    private List<Fragment> fragments=new ArrayList<>();

    //待采样碎片
    private WaitingForSamplingFragment waiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter=new MainPresenter();
        presenter.attachView(this);
        initTab();
        initViewPager();
    }

    private void initViewPager() {
        initFragment();
        MainViewPagerAdapter adapter=new MainViewPagerAdapter(getSupportFragmentManager(),
                fragments,new String[]{"待采样","已采样"});
        viewPager.setAdapter(adapter);
    }

    private void initFragment() {
        WaitingForSamplingFragment.setPresenter(presenter);
        fragments.add(new WaitingForSamplingFragment());
        fragments.add(new HasBeenSampledFragment());
    }

    private void initTab() {
        tabLayout.setupWithViewPager(viewPager);
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
