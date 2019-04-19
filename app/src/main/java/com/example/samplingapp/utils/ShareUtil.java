package com.example.samplingapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.core.Entity.User;
import com.example.samplingapp.Base.App;

/**
 * 持久化类
 */
public class ShareUtil {

    private static final String LOADSTATE = "LOAD";
    private static final String LOADKEY = "LOAD_KEY";
    private static final String SAVED = "1";
    private static final String UNSAVE = "2";
    private static final String PACKAGENAME = "SAMPLINGAPP";

    private static String getData(App app, String userName, String key) {
        SharedPreferences preferences = app.getSharedPreferences(userName
                , Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    @SuppressLint("CommitPrefEdits")
    private static void setData(App app, String userName, String key, String content) {
        SharedPreferences preferences = app.getSharedPreferences(userName
                , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, content);
        editor.apply();
    }

    public static void setToken(App app, String token) {
        setData(app, App.getUser(app).getAccount(), "Token", token);
    }

    public static String getToken(App app) {
        return getData(app, App.getUser(app).getAccount(), "Token");
    }

    /**
     * 设置登录状态
     *
     * @param app
     */
    public static void setLoadState(App app) {
        SharedPreferences preferences = app.getSharedPreferences(LOADSTATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOADKEY, SAVED);
        editor.apply();
    }

    /**
     * 获得登录状态
     *
     * @param app
     * @return true为已登录
     */
    public static Boolean getLoadState(App app) {
        SharedPreferences preferences = app.getSharedPreferences(LOADSTATE, Context.MODE_PRIVATE);
        String res = preferences.getString(LOADKEY, "0");
        return res != null && res.equals(SAVED);
    }

    /**
     * 设置为未登录
     *
     * @param app
     */
    public static void resetLoadState(App app) {
        SharedPreferences preferences = app.getSharedPreferences(LOADSTATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LOADKEY, UNSAVE);
        editor.apply();
    }

    /**
     * 存储user对象
     */
    public static void setUserInfo(App app, String account, String orgName) {
        setData(app,PACKAGENAME,"account",account);
        setData(app,PACKAGENAME,"orgName",orgName);
    }

    /***
     * 获取user信息
     * @return
     */
    public static User getUserInfo(App app) {
        return new User(getData(app,PACKAGENAME,"account")
                ,getData(app,PACKAGENAME,"orgName"));
    }

    /**
     * 保存首页输入的Ip
     * @param app
     * @param Ip
     */
    public static void saveIp(App app,String Ip){
        setDataInsteadUser(app,PACKAGENAME,"ServerIp",Ip);
    }

    public static String getIp(App app){
        return getDataInsteadUser(app,PACKAGENAME,"ServerIp");
    }

    /**
     * 存储全局数据（用户无关）
     */
    private static void setDataInsteadUser(App app,String identification,String key,String content){
        SharedPreferences preferences = app.getSharedPreferences(identification
                , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, content);
        editor.apply();
    }

    /**
     * 返回全局存储数据
     * @param app
     * @param identification
     * @param key
     * @return
     */
    private static String getDataInsteadUser(App app, String identification, String key){
        SharedPreferences preferences = app.getSharedPreferences(identification
                , Context.MODE_PRIVATE);
        return preferences.getString(key, "http://192.168.1.124:129/");
    }
}
