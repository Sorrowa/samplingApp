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
    private static volatile LocationClient client2=null;

    /**
     * 务必传入application对象
     * @param context
     */
    private static void onCreateClient(Context context,int i){
        if (i==0){
            client=new LocationClient(context);
            startOption();
        }
        if (i==1){
            client2=new LocationClient(context);
            startOption2();
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
        //坐标系默认国家坐标系_百度
        option.setCoorType("bd09ll");
        //定位请求时间
        option.setScanSpan(0);

        client.setLocOption(option);
    }

    /**
     * 多次定位设置
     */
    private static void startOption2() {
        LocationClientOption option=new LocationClientOption();
        //高精度点位模式
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //高精度模式下，必须为true
        option.setOpenGps(true);
        //设置是否为流氓进程
        option.setIgnoreKillProcess(true);
        option.setCoorType("bd09ll");
        //坐标系默认国家坐标系
        //定位请求时间
        option.setScanSpan(0);

        client2.setLocOption(option);
    }

    /**
     * 单次定位SDK
     * @param context application对象
     * @param listener = =
     */
    public static void beginToGetNowLocation(Context context, BDAbstractLocationListener listener){
        onCreateClient(context,0);
        client.registerLocationListener(listener);
        client.start();
    }

    /**
     * 定位SDK 多次定位
     * @param context application对象
     * @param listener = =
     */
    public static void beginToGetNowLocation2(Context context, BDAbstractLocationListener listener){
        onCreateClient(context,1);
        client2.registerLocationListener(listener);
        client2.start();
    }

}
