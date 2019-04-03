package com.example.samplingapp.Activities.SamplingForm;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointData;
import com.example.samplingapp.Adapter.ViewPagerAdapters.PointViewPagerAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看点位的活动
 */
public class SamplingPointActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.top_tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_point)
    ViewPager viewPager;

    private ArrayList<PointData> pointDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_point);
        ButterKnife.bind(this);
        pointDatas = getIntent().getParcelableArrayListExtra("data");
        initView();
    }

    private void initView() {
        initToolbar();
        initViewPager();
        initTab();
    }

    private void initTab() {
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        PointViewPagerAdapter adapter=new PointViewPagerAdapter(this
                ,new String[]{"全部","未采样","已采样"},pointDatas);
        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {
        title.setText("采样点位");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(view -> {
            Intent res = new Intent();
            //todo:添加选择内容
            setResult(SamplingFormActivity.POINTGET, res);
            finish();
        });
    }
}
