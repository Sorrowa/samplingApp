package com.example.samplingapp.Activities.SamplingForm.ShowDataActivitys;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.SamplingData;
import com.example.samplingapp.Adapter.RecycleViewAdapters.SamplingDetailAdapter;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.SamplingStatusPresenter;
import com.example.samplingapp.R;

import java.util.ArrayList;
import java.util.List;

public class SamplingStatusActivity extends BaseActivity implements SamplingStatusPresenter.StatusListener {

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.sampling_list)
    RecyclerView recyclerView;
    SamplingDetailAdapter adapter;

    //显示的样品信息
    private List<SamplingData> datas=new ArrayList<>();
    private SamplingStatusPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_status);
        String pointId = getIntent().getStringExtra("pointId");

        presenter=new SamplingStatusPresenter();
        presenter.attachView(this);
        ButterKnife.bind(this);
        initView();

        presenter.getStatus(pointId,(App) getApplication(),this);
    }


    private void initView() {
        initToolbar();
        initRcy();
    }

    /**
     * 初始化列表
     */
    private void initRcy() {
        adapter=new SamplingDetailAdapter(datas);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    private void initToolbar() {
        title.setText("样品信息");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(view -> finish());
    }


    @Override
    protected void onDestroy() {
        presenter.detachView();
        presenter=null;
        super.onDestroy();
    }

    /**
     * 状态回调接口
     * @param data
     */
    @Override
    public void onSuccess(List<SamplingData> data) {
        adapter.setDatas(data);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    @Override
    public void onFail() {
        showToast("因网络原因无法显示样品信息");
    }
}
