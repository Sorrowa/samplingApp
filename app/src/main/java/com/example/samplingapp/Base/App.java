package com.example.samplingapp.Base;

import android.app.Application;
import android.app.Activity;
import java.util.ArrayList;

public class App extends Application {

    private ArrayList<Activity> activities;


    @Override
    public void onCreate() {
        super.onCreate();
        activities=new ArrayList<>();

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
