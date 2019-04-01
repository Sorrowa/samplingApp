package com.example.samplingapp.Activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//个人中心
public class PersonalActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView textView;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.orgName)
    TextView orgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);
        initView();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        textView.setText("个人中心");
        leftItem.setImageDrawable(this.getResources().getDrawable(R.drawable.go_back));
        userName.setText("当前账号: "+ App.getUser((App) getApplication()).getAccount());
        orgName.setText("所属机构: "+App.getUser((App) getApplication()).getOrgName());
    }

    @OnClick({R.id.left_item})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.left_item:
                finish();
        }
    }
}
