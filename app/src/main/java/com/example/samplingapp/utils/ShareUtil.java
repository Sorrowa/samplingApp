package com.example.samplingapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.samplingapp.Base.App;

/**
 * 持久化类
 */
public class ShareUtil {


    private static String getData(App app, String userName,String key){
        SharedPreferences preferences=app.getSharedPreferences(userName
                , Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }

    @SuppressLint("CommitPrefEdits")
    private static void setData(App app,String userName,String key,String content){
        SharedPreferences preferences=app.getSharedPreferences(userName
                ,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(key,content);
        editor.apply();
    }

    public static void setToken(App app,String token){
        setData(app,App.user.getOrgName(),"Token",token);
    }

    public static String getToken(App app){
        return getData(app,App.user.getOrgName(),"Token");
    }
}
