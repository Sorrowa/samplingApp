package com.example.samplingapp.utils;

import android.content.Context;
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
}
