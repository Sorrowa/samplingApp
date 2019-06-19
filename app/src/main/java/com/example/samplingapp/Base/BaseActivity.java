package com.example.samplingapp.Base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.example.network.InternetUtil;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.ShareUtil;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class BaseActivity extends AppCompatActivity implements BaseView , EasyPermissions.PermissionCallbacks{

    public App app;
    public Handler handler;

    Dialog loadingDialog;
    Dialog compressDialog;
    Dialog uploadDialog;

    private static boolean isOK = false;

    String[] permmisons = new String[]{Manifest.permission.CAMERA
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.INTERNET
            , Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_WIFI_STATE
//            , Manifest.permission.READ_SYNC_SETTINGS
            , Manifest.permission.WAKE_LOCK
            , Manifest.permission.RECORD_AUDIO
            , Manifest.permission.ACCESS_NETWORK_STATE
            , Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.MODIFY_AUDIO_SETTINGS
//            , Manifest.permission.WRITE_SETTINGS
            , Manifest.permission.CHANGE_WIFI_STATE
            , Manifest.permission.CHANGE_WIFI_MULTICAST_STATE};

//    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /> <!-- 用于访问wifi网络信息，wifi信息会用于进行网络定位 -->
//    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> <!-- 获取运营商信息，用于支持提供运营商信息相关的接口 -->

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (app == null) {
            app = (App) getApplication();
        }
        InternetUtil.SERVER_IP = ShareUtil.getIp(app);
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
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }

        if (Build.VERSION.SDK_INT >= 23 &&
                !isOK) {
            getRight();
        }
    }

    /**
     * 统一获取权限
     */
    private void getRight() {
        if (!EasyPermissions.hasPermissions(this, permmisons)) {
            EasyPermissions.requestPermissions(this
                    , "请允许以下权限，否则app将不能使用"
                    , 996
                    , permmisons);
        }
    }


    public void addActivity() {
        app.addActivity_(this);
    }

    public void removeActivity() {
        app.removeActivity_(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 刷新handler
     */
    @SuppressLint("HandlerLeak")
    private void resetHandler() {
        if (handler == null) {
            handler = new Handler() {
                @Override
                public String getMessageName(Message message) {
                    return super.getMessageName(message);
                }
            };
        }
    }

    public void handleRunnable(Runnable runnable) {
        resetHandler();
        handler.post(runnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(996
                , permmisons
                , grantResults
                , this);
    }

//    /**
//     * 权限请求回调
//     */
//    @AfterPermissionGranted(996)
//    public void afterGetPermission() {
//        showToast("权限请求结束");
//        getRight();
//    }

    /**
     * 显示加载中的图标
     */
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_loading
                    , null);
            loadingDialog = new Dialog(this,R.style.loadingDialog);
            loadingDialog.setContentView(layout);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(true);
            loadingDialog.setOnCancelListener(dialog -> showToast("后台将继续处理"));
        }
        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog!=null){
            loadingDialog.dismiss();
        }
    }


    /**
     * 显示加载中的图标
     */
    public void showUploadDialog() {
        if (uploadDialog == null) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_upload
                    , null);
            uploadDialog = new Dialog(this,R.style.loadingDialog);
            uploadDialog.setContentView(layout);
            uploadDialog.setCancelable(false);
            uploadDialog.setCanceledOnTouchOutside(true);
            uploadDialog.setOnCancelListener(dialog -> showToast("后台将继续处理"));
        }
        uploadDialog.show();
    }

    public void dismissUploadDialog() {
        if (uploadDialog!=null){
            uploadDialog.dismiss();
        }
    }


    /**
     * 显示加载中的图标
     */
    public void showCompressDialog() {
        if (compressDialog == null) {
            View layout = getLayoutInflater().inflate(R.layout.dialog_compress
                    , null);
            compressDialog = new Dialog(this,R.style.loadingDialog);
            compressDialog.setContentView(layout);
            compressDialog.setCancelable(false);
            compressDialog.setCanceledOnTouchOutside(false);
            compressDialog.setOnCancelListener(dialog -> showToast("后台将继续处理"));
        }
        compressDialog.show();
    }

    public void dismissCompressDialog() {
        compressDialog.dismiss();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        isOK=true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showToast("您可能无法使用部分功能");
    }
}
