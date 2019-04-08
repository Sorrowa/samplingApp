package com.example.samplingapp.utils.Location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * location帮助类
 */
public class Location {
    private static volatile LocationClient client=null;

    /**
     * 务必传入application对象
     * @param context
     */
    private static void onCreateClient(Context context){
        if (client==null){
            client=new LocationClient(context);
            startOption();
        }
    }

    /**
     * 设置
     */
    private static void startOption() {
        LocationClientOption option=new LocationClientOption();
        //高精度点位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //高精度模式下，必须为true
        option.setOpenGps(true);
        //设置是否为流氓进程
        option.setIgnoreKillProcess(true);
        //坐标系默认国家坐标系
        //定位请求时间
        option.setScanSpan(1000);

        client.setLocOption(option);
    }

    /**
     * 定位SDK
     * @param context application对象
     * @param listener = =
     */
    public static void beginToGetNowLocation(Context context, BDAbstractLocationListener listener){
        onCreateClient(context);
        client.registerLocationListener(listener);
        client.start();
    }
}
