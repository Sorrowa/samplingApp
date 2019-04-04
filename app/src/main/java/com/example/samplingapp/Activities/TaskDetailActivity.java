package com.example.samplingapp.Activities;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.Data.ProjectData;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Activities.SamplingForm.SamplingPointActivity;
import com.example.samplingapp.Activities.TaskDetailBaseActivity.TaskBaseActivity;
import com.example.samplingapp.Adapter.RecycleViewAdapters.TaskDetailAdapter;
import com.example.samplingapp.Presenter.TaskPresenter;
import com.example.samplingapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情界面
 */
public class TaskDetailActivity extends TaskBaseActivity implements TaskPresenter.listener {

    private static ProjectData data;

    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.right_item)
    ImageView rightItem;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.point_percent)
    TextView pointPercent;
    @BindView(R.id.point_list)
    RecyclerView r;
    @BindView(R.id.add_new_sampling)
    TextView addNewSampling;

    private TaskPresenter presenter;
    private String type;//1:未采样 2:已采样
    private DialogListener listener;

    //点位信息
    private ArrayList<PointData> pointDatas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        data=getIntent().getParcelableExtra("task");
        type=getIntent().getStringExtra("type");
        ButterKnife.bind(this);
        presenter=new TaskPresenter();
        presenter.attachView(this);
        initView();
        //开始网络请求
        presenter.getPointList(type,data.getId(),searchRes,this);
        //dialog显示回调接口
        listener= () -> presenter.getPointList(type,data.getId(),searchRes,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //根据type做出系列操作
        doWithType();
    }

    private void doWithType() {
        //隐藏新建表单项
        if (type.equals("2")) {
            addNewSampling.setVisibility(View.GONE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        title.setText("任务详情");
        rightItem.setImageDrawable(getDrawable(R.drawable.go_back));
        //显示搜索dialog
        rightItem.setOnClickListener(view -> showSearchDialog(listener));

        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(view -> finish());
        pointPercent.setText("采样点位："+data.getSampCount()+"/"+data.getTotalPoint());

        //跳转到点位详细信息界面
        pointPercent.setOnClickListener(view -> {
            Intent intent=new Intent(TaskDetailActivity.this
                    , SamplingPointActivity.class);
            intent.putExtra("projectId",data.getId());
            startActivity(intent);
        });

        addNewSampling.setOnClickListener(view -> {
            Intent intent=new Intent(TaskDetailActivity.this
                    ,SamplingFormActivity.class);
            intent.putExtra("projectId",data.getId());
            startActivity(intent);
        });
    }



    @Override
    public void onSuccess(List<PointData> data, boolean isOk) {
        if (isOk){
            pointDatas.addAll(data);
            initRecycleView(data);
        }else{
            showToast("获取点位数据失败");
        }
    }

    /**
     * 将数据注入RecycleView
     * @param data 数据
     */
    private void initRecycleView(List<PointData> data) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        r.setLayoutManager(manager);
        TaskDetailAdapter adapter=new TaskDetailAdapter(data);
        r.setAdapter(adapter);
    }

    @Override
    public void onFail() {
        showToast("获取点位数据失败");
    }
}
