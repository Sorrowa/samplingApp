package com.example.samplingapp.Activities.SamplingForm;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

public class SamplingFormActivity extends BaseActivity {

    public static final int POINTGET=0;

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.right_item)
    ImageView rightItem;

    @BindView(R.id.person_name)
    EditText personName;
    @BindView(R.id.point_select)
    View point_select;//点位选择
    @BindView(R.id.point_name)
    TextView pointName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_form);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initToolBar();
        initPointSelect();
    }

    /**
     * 点位选择
     */
    private void initPointSelect() {
        //todo:开启下一个活动选择对应的点位
    }


    private void initToolBar() {
        title.setText("采样详情");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        rightItem.setImageDrawable(getDrawable(R.drawable.submit));
        leftItem.setOnClickListener(view -> finish());
        //todo:提交的逻辑

    }
}
