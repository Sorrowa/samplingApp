package com.example.samplingapp.Activities.SamplingForm.SelectActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.SampMethodData;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Adapter.RecycleViewAdapters.Select.MethodSelect.MethodSelectAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.MethodSelectPresenter;
import com.example.samplingapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MethodSelectActivity extends BaseActivity implements MethodSelectPresenter.MethodListener, MethodSelectAdapter.onItemClickListener {

    @BindView(R.id.left_item)
    ImageView left_item;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.search_edit_method)
    EditText search_edit_method;
    @BindView(R.id.method_recyclerview)
    RecyclerView method_recyclerview;
    @BindView(R.id.search_bt)
    TextView search_bt;

    MethodSelectPresenter presenter;
    MethodSelectAdapter adapter;

    List<SampMethodData> datas=new ArrayList<>();

    SampMethodData res=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_method_select);

        presenter=new MethodSelectPresenter();
        presenter.attachView(this);

        ButterKnife.bind(this);

        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog();
        presenter.getMethod("",this);
    }


    private void initView() {
        initToolbar();
        initRecyclerView();
        initSearch();
    }

    /**
     * 搜索框
     */
    private void initSearch() {
        search_bt.setOnClickListener(view ->
                presenter.getMethod(search_edit_method.getText().toString()
                ,MethodSelectActivity.this));
    }


    private void initToolbar() {
        left_item.setImageDrawable(getDrawable(R.drawable.go_back));
        title.setText("采样方法");
        left_item.setOnClickListener(view -> doBack());
    }

    private void initRecyclerView() {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        method_recyclerview.setLayoutManager(manager);
        adapter=new MethodSelectAdapter(this,datas,this);
        method_recyclerview.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doBack();
    }


    /**
     * 将结果设置到intent中
     */
    private void doBack() {
        Intent intent=new Intent();
        if (res!=null){
            intent.putExtra("Method",res);
        }
        setResult(SamplingFormActivity.METHOD,intent);
        finish();
    }

    @Override
    public void onSuccess(List<SampMethodData> dataList) {
        datas.clear();
        datas.addAll(dataList);
        runOnUiThread(() -> {
            dismissLoadingDialog();
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
    }

    /**
     * item点击确定项
     * @param position
     */
    @Override
    public void onClick(int position) {
        res=datas.get(position);
        doBack();
//        AlertDialog.Builder normalDialog =
//                new AlertDialog.Builder(MethodSelectActivity.this);
//        normalDialog.setTitle("提示");
//        normalDialog.setMessage("确定选择"+res.getSampMethodName()+"吗？");
//        normalDialog.setPositiveButton("确定",
//                (dialog, which) -> doBack());
//        normalDialog.setNegativeButton("取消",
//                (dialog, which) -> dialog.dismiss());
//        // 显示
//        normalDialog.show();
    }
}
