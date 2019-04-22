package com.example.samplingapp.Base;

import android.app.Application;
import android.app.Activity;

import com.baidu.mapapi.SDKInitializer;
import com.example.core.Entity.User;
import com.example.samplingapp.utils.ShareUtil;

import java.util.ArrayList;

public class App extends Application {

    private ArrayList<Activity> activities;
    public static User user;

    @Override
    public void onCreate() {
        super.onCreate();
        activities=new ArrayList<>();
        SDKInitializer.initialize(this);
    }

    /**
     * 获取user，如果user信息为空，那么从sharePreference中获取
     * @return
     */
    public static User getUser(App app){
        if (null==user){
            //初始化user
            user=ShareUtil.getUserInfo(app);
        }

        return user;
    }

    public void addActivity_(Activity activity){
        activities.add(activity);
    }

    public void removeActivity_(Activity activity){
        if (activities.contains(activity)){
            activities.remove(activity);
            activity.finish();
        }
    }

    public void removeAllActivity_(){
        for (Activity activity : activities){
            activities.remove(activity);
            activity.finish();
        }
    }

    //todo:数据库处理

}
