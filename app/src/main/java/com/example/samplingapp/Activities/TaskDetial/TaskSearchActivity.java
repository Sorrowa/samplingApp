package com.example.samplingapp.Activities.TaskDetial;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samplingapp.Activities.TaskDetailActivity;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskSearchActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView left_item;
    @BindView(R.id.search)
    EditText search;
    @BindView(R.id.search_bt)
    TextView search_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_search);

        ButterKnife.bind(this);

        initToolbar();
        initView();
    }

    private void initView() {
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                search.clearFocus();
                goback();
                return true;
            }
            return false;
        });

        search_bt.setOnClickListener(view -> {
            search.clearFocus();
            goback();
        });
    }

    private void initToolbar() {
        title.setText("搜索");
        left_item.setImageDrawable(getDrawable(R.drawable.go_back));
        left_item.setOnClickListener(view -> finish());

    }


    private void goback() {
        Intent intent=new Intent();
        intent.putExtra("res",search.getText().toString());
        setResult(TaskDetailActivity.SEARCH,intent);
        finish();
    }
}
