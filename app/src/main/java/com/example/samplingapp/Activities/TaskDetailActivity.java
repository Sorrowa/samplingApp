package com.example.samplingapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.Data.ProjectData;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Activities.SamplingForm.SamplingPointActivity;
import com.example.samplingapp.Activities.TaskDetailBaseActivity.TaskBaseActivity;
import com.example.samplingapp.Activities.TaskDetial.TaskSearchActivity;
import com.example.samplingapp.Adapter.RecycleViewAdapters.TaskDetailAdapter;
import com.example.samplingapp.Presenter.TaskPresenter;
import com.example.samplingapp.R;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务详情界面
 */
public class TaskDetailActivity extends TaskBaseActivity implements TaskPresenter.listener, TaskPresenter.countListener {


    public static final int SEARCH = 0;

    private static ProjectData data;

    private String keyword = "";

    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.right_item)
    ImageView rightItem;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.point_percent)
    TextView pointPercent;
    @BindView(R.id.point_list)
    SwipeRecyclerView r;
    @BindView(R.id.add_new_sampling)
    TextView addNewSampling;

    private TaskPresenter presenter;
    private String type;//1:未采样 2:已采样
    private DialogListener listener;

    private TaskDetailAdapter adapter;

    //点位信息
    private ArrayList<PointData> pointDatas = new ArrayList<>();

    private Dialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        data = getIntent().getParcelableExtra("task");
        type = getIntent().getStringExtra("type");
        ButterKnife.bind(this);
        presenter = new TaskPresenter();
        presenter.attachView(this);
        initView();
        initRecycleView(pointDatas);
        //开始网络请求
        presenter.getPointList(type, data.getId(), searchRes, this);
        presenter.getPointCount(data.getId(), this);
        //dialog显示回调接口
        listener = () -> presenter.getPointList(type, data.getId(), searchRes, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //根据type做出系列操作
        doWithType();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        presenter.getPointList(type, data.getId(), keyword, this);
        presenter.getPointCount(data.getId(), this);
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
        rightItem.setImageDrawable(getDrawable(R.drawable.search));
        //显示搜索dialog
        rightItem.setOnClickListener(view -> {
            Intent intent = new Intent(TaskDetailActivity.this
                    , TaskSearchActivity.class);
            startActivityForResult(intent, SEARCH);
        });

        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(view -> finish());
//        pointPercent.setText("采样点位：" + data.getSampCount() + "/" + data.getTotalPoint());

        //跳转到点位详细信息界面
        pointPercent.setOnClickListener(view -> {
            Intent intent = new Intent(TaskDetailActivity.this
                    , SamplingPointActivity.class);
            intent.putExtra("projectId", data.getId());
            startActivity(intent);
        });

        addNewSampling.setOnClickListener(view -> {
            Intent intent = new Intent(TaskDetailActivity.this
                    , SamplingFormActivity.class);
            intent.putExtra("projectId", data.getId());
            startActivity(intent);
        });
    }


    @Override
    public void onSuccess(List<PointData> data, boolean isOk) {
        if (isOk) {
            dismissLoadingDialog();
            pointDatas.clear();
            pointDatas.addAll(data);
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        } else {
            dismissLoadingDialog();
            showToast("获取点位数据失败");
        }
    }

    /**
     * 将数据注入RecycleView
     *
     * @param data 数据
     */
    private void initRecycleView(List<PointData> data) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        r.setLayoutManager(manager);
        //菜单删除
        if (adapter == null) {
            r.setSwipeMenuCreator(mSwipeMenuCreator);
            r.setOnItemMenuClickListener(mOnItemMenuClickListener);
            r.setOnItemClickListener((view, adapterPosition) -> {
                Intent intent = new Intent(TaskDetailActivity.this
                        , SamplingFormActivity.class);
                intent.putExtra("formId", data.get(adapterPosition).getId());
                intent.putExtra("status", data.get(adapterPosition).getStatus());
                intent.putExtra("word_status", data.get(adapterPosition).getStatusName());
                startActivity(intent);
            });
            adapter = new TaskDetailAdapter(data);
        }
        r.setAdapter(adapter);
    }

    @Override
    public void onFail() {
        showToast("获取点位数据失败");
    }


    //创建侧滑菜单
    SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, position) -> {
        SwipeMenuItem deleteItem = new SwipeMenuItem(TaskDetailActivity.this)
                .setText("删除")
                .setBackground(R.drawable.delete_item)
                .setTextColor(Color.WHITE)
                .setHeight(150)
                .setWidth(200)
                .setWeight(1)
                .setTextSize(14);
        rightMenu.addMenuItem(deleteItem);
    };
    //删除监听器
    OnItemMenuClickListener mOnItemMenuClickListener = (menuBridge, adapterPosition) -> {
        menuBridge.closeMenu();
        //因为只有一个点击项，所以直接删除
        //仿苹果
        new AlertView("提示", "您确定要删除此条信息吗？"
                , "取消"
                , new String[]{"确定"}
                , null
                , TaskDetailActivity.this,
                AlertView.Style.Alert
                , (o, position) -> {
            if (position == 0)
                deleteItem(adapterPosition);
        }).show();

    };

    /**
     * 删除对应位置的信息
     *
     * @param adapterPosition
     */
    private void deleteItem(int adapterPosition) {

        if (pointDatas.get(adapterPosition).getStatusName().equals("打回")){
            showToast("该表单不可删除");
            return;
        }

        showDeleteDialog();

        presenter.deleteForm(pointDatas.get(adapterPosition).getId()
                , new TaskPresenter.deleteListener() {
                    @Override
                    public void onSuccess() {
                        deleteDialog.dismiss();
                        pointDatas.remove(adapterPosition);
                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                    }

                    @Override
                    public void onFail(String info) {
                        deleteDialog.dismiss();
                        showToast(info);
                    }
                });
    }

    private void showDeleteDialog() {

        if (deleteDialog == null) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_form_delete
                    , null);
            deleteDialog = new Dialog(TaskDetailActivity.this
                    ,R.style.loadingDialog);
            deleteDialog.setContentView(layout);
            deleteDialog.setCancelable(false);
            deleteDialog.setCanceledOnTouchOutside(true);
            deleteDialog.setOnCancelListener(dialog -> showToast("后台将继续删除信息"));
        }
        deleteDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case SEARCH:
                if (data != null) {
                    keyword = data.getStringExtra("res");
                    presenter.getPointList(type
                            , TaskDetailActivity.data.getId()
                            , keyword
                            , this);
                    showLoadingDialog();
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(String count) {
        //todo:点位统计数据
        runOnUiThread(() -> pointPercent.setText("采样点位：" + count));
    }

    @Override
    public void onFailCount(String info) {
        showToast(info);
    }
}
