package com.example.samplingapp.Base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity implements BaseView {

    App app;
    public Handler handler;

    String[] permmisons = new String[]{Manifest.permission.CAMERA
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.INTERNET
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission_group.PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app==null){
            app= (App) getApplication();
        }
        addActivity();

        //沉浸式标题栏设置
        if (Build.VERSION.SDK_INT >= 21) {
            //效果是只有5.0及以上系统才支持
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar=getSupportActionBar();
        if (null!=actionBar){
            actionBar.hide();
        }
    }

    public void addActivity(){
        app.addActivity_(this);
    }

    public void removeActivity(){
        app.removeActivity_(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
    }

    @Override
    public void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public Context getContext(){
        return this;
    }

    /**
     * 刷新handler
     */
    @SuppressLint("HandlerLeak")
    private void resetHandler(){
        if (handler==null){
            handler=new Handler(){
                @Override
                public String getMessageName(Message message) {
                    return super.getMessageName(message);
                }
            };
        }
    }

    public void handleRunnable(Runnable runnable){
        resetHandler();
        handler.post(runnable);
    }
}
