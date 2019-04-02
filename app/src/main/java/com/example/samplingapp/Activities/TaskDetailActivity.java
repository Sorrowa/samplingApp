package com.example.samplingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.Data.ProjectData;
import com.example.samplingapp.Adapter.RecycleViewAdapters.TaskDetailAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.TaskPresenter;
import com.example.samplingapp.R;

import java.util.List;

/**
 * 任务详情界面
 */
public class TaskDetailActivity extends BaseActivity implements TaskPresenter.listener {

    private static ProjectData data;

    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.point_percent)
    TextView pointPersent;
    @BindView(R.id.point_list)
    RecyclerView r;

    private TaskPresenter presenter;
    private String type;

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
        presenter.getPointList(type,data.getId(),this);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        title.setText("任务详情");
        leftItem.setOnClickListener(view -> finish());
        pointPersent.setText("采样点位："+data.getSampCount()+"/"+data.getTotalPoint());
    }



    @Override
    public void onSuccess(List<PointData> data, boolean isOk) {
        if (isOk){
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
