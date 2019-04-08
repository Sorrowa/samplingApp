package com.example.samplingapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

/**
 * 各种工具类
 */
public class BaseUtil {
    public static void showToast(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(Context context,String text){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("注意");
        builder.setMessage(text);
        builder.setCancelable(true);
        builder.show();
    }

    /**
     * 判断网络是否正常
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 关闭软键盘
     * @param activity
     */
    public static void softInput(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 计算两个坐标之间的距离
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离M为单位
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 角度转换为弧度
     * @param d
     * @return 弧度
     */
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

}
