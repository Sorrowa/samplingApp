package com.example.samplingapp.Activities.SamplingForm.SelectActivitys;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Adapter.ViewPagerAdapters.PointViewPagerSelectAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.PointSelectPresenter;
import com.example.samplingapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

//选择点位
public class SelectPointActivity extends BaseActivity implements PointViewPagerSelectAdapter.OnClickListener, PointSelectPresenter.onNetListener {

    private String projectId;


    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.top_tab)
    TabLayout topTab;

    private PointViewPagerSelectAdapter adapter;
    private List<PointDetailData> pointDatas=new ArrayList<>();

    private PointSelectPresenter presenter;

    private PointDetailData nowData;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_point);
        ButterKnife.bind(this);
        presenter=new PointSelectPresenter();
        presenter.attachView(this);
        projectId=getIntent().getStringExtra("projectId");

        initView();
        initData();
    }

    private void initData() {
        presenter.getProjectListInfo("0",projectId,this);
        View layout = getLayoutInflater().inflate(R.layout.dialog_waitting_network, null);
        dialog=new Dialog(this);
        dialog.setContentView(layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(dialog -> {});
        dialog.show();
    }

    private void initView() {
        initToolbar();
        initViewPager();
        initTab();
    }


    private void initTab() {
        topTab.setupWithViewPager(viewPager);
    }

    private void initViewPager() {
        adapter=new PointViewPagerSelectAdapter(this
                ,new String[]{"全部", "未采样", "已采样"}
                ,pointDatas
                ,this);
        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {
        title.setText("选择点位");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(view -> {
            Intent intent=new Intent();
            intent.putExtra("pointData",nowData);
            setResult(SamplingFormActivity.POINTGET,intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("pointData",nowData);
        setResult(SamplingFormActivity.POINTGET,intent);
        finish();
    }

    /**
     * Item点击回调接口
     * @param data
     */
    @Override
    public void onClick(PointDetailData data) {
        showToast("选择点位为："+data.getName());
        nowData=data;
    }

    /**
     * 获取点位信息
     * @param datas
     * @param isOk
     */
    @Override
    public void onSuccess(List<PointDetailData> datas, boolean isOk) {
        if (isOk) {
            handleRunnable(() -> {
                        adapter.setData(datas);
                        adapter.notifyRecy();
                    }
            );
        }
        dialog.dismiss();
    }

    @Override
    public void onFail() {
        showToast("网络出现错误");
        dialog.dismiss();
    }
}
