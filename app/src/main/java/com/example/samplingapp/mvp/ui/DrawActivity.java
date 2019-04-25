package com.example.samplingapp.mvp.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;
import com.yinghe.whiteboardlib.fragment.WhiteBoardFragment;

import java.io.File;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 画板
 */
public class DrawActivity extends BaseActivity {


    FragmentManager fragmentManager;
    WhiteBoardFragment whihteFragment;

    @BindView(R.id.left_item)
    ImageView back;
    @BindView(R.id.center_title)
    TextView title;

    private int type=0;

    private boolean isSaved=false;

    private Dialog dialog;
    private File file=null;
    private String path=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        fragmentManager = getSupportFragmentManager();
        ButterKnife.bind(this);
        type=getIntent().getIntExtra("type",0);
//        path=getIntent().getStringExtra("path");
        if (type==0){
            showToast("出现未知错误");
            finish();
        }
        initToolbar();
        initFragment();
    }

    private void initToolbar() {
        title.setText("绘制界面");
        back.setImageDrawable(getDrawable(R.drawable.go_back));
        back.setOnClickListener(view -> doBack());
    }

    private void initFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        whihteFragment = new WhiteBoardFragment();
        transaction.replace(R.id.fragment, whihteFragment, null)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (path!=null){
//            whihteFragment.setPath(path);
//        }
    }

    @Override
    public void onBackPressed() {
        doBack();
    }

    private void doBack() {
        if (!isSaved){
            file=whihteFragment.saveInOI(String.valueOf(getApplication().getFilesDir()),String.valueOf(type));
            isSaved=true;
        }
        new AlertView("提示", "是否保存？"
                , "取消"
                , new String[]{"确定"}
                , null
                , DrawActivity.this,
                AlertView.Style.Alert
                , (o, position) -> {
            if (position == 0) {
                Intent intent=new Intent();
                intent.putExtra("path",file.getAbsolutePath());
                setResult(type,intent);
            }else{
                Intent intent=new Intent();
                intent.putExtra("path","0");
                setResult(type,intent);
            }
            finish();
        }).show();

//        Intent intent=new Intent();
//        intent.putExtra("path",file.getAbsolutePath());
//        setResult(type,intent);
//        finish();
    }

//    private void showSavingDialog(){
//        View layout = getLayoutInflater().inflate(R.layout.dialog_save, null);
//        dialog=new Dialog(this);
//        dialog.setContentView(layout);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnCancelListener(dialog1 -> {});
//        dialog.show();
//    }
}
