package com.example.samplingapp.Activities.SamplingForm.SelectActivitys;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

//采用checkBox的模式选择

public class SelectPointActivity extends BaseActivity {

    private String projectId;


    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.recy)
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_point);
        ButterKnife.bind(this);

        projectId=getIntent().getStringExtra("projectId");

        initView();
    }

    private void initView() {
        initToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {

    }

    private void initToolbar() {
        title.setText("选择点位");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        leftItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:设置选定的点位信息
                finish();
            }
        });
    }
}
