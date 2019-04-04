package com.example.samplingapp.Activities.SamplingForm;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.Adapter.ViewPagerAdapters.PointViewPagerAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.PointSelectPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看点位的活动
 */
public class SamplingPointActivity extends BaseActivity implements PointSelectPresenter.onNetListener {

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.top_tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager_point)
    ViewPager viewPager;

    private String projectId;

    private List<PointDetailData> data=new ArrayList<>();

    private PointSelectPresenter presenter;
    private PointViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_point);
        ButterKnife.bind(this);
        projectId = getIntent().getStringExtra("projectId");
        presenter = new PointSelectPresenter();
        presenter.attachView(this);
        initPointData();
        initView();
    }

    /**
     * 初始化点位数据
     * 现阶段只有从网络获取的数据
     */
    private void initPointData() {
        //todo:修改数据
        presenter.getProjectListInfo("0"
                , "F1D85C28-95A0-4BC4-B714-0F4A3FFB0B00"
                , this);
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
        adapter = new PointViewPagerAdapter(this
                , new String[]{"全部", "未采样", "已采样"}, data);
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

    @Override
    public void onSuccess(List<PointDetailData> datas, boolean isOk) {
        if (isOk) {
            handleRunnable(() -> {
                        adapter.setData(datas);
                        BaseUtil.softInput(this);
                        adapter.notifyRecy();
                    }
            );
        }
    }

    @Override
    public void onFail() {
        showToast("请检查网络状况");
    }
}
