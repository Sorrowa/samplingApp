package com.example.samplingapp.Presenter;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.mvp.ui.NavigationActivity;
import com.example.samplingapp.utils.Location.Location;

public class NavigationPresenter extends BasePresenter<NavigationActivity> {


    public void beginLocation(Context context, LocationListener listener) {
        MyLocationListener myListener = new MyLocationListener(listener);
        Location.beginToGetNowLocation2(context, myListener);
    }

    /**
     * 定位回调接口
     */
    private class MyLocationListener extends BDAbstractLocationListener {

        LocationListener listener;

        MyLocationListener(LocationListener listener) {
            this.listener = listener;
        }

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            switch (bdLocation.getLocType()) {
                case 61:
                    listener.onSuccess(bdLocation);
                    break;
                case 62:
                    listener.onError("定位失败，请检查运营商网络或者WiFi网络是否正常开启");
                    break;
                case 63:
                    listener.onError("网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅");
                    break;
                case 66:
                    listener.onSuccess(bdLocation);
                    break;
                case 67:
                    listener.onError("离线定位失败");
                    break;
                case 161:
                    listener.onSuccess(bdLocation);
                    break;
                case 162:
                    listener.onError("app发生未知错误");
                    break;
                case 167:
                    listener.onError("请您检查是否禁用获取位置信息权限");
                    break;
                case 505:
                    listener.onError("app发生未知错误");
                    break;
            }
        }
    }


    public interface LocationListener {
        /**
         * 经纬度信息
         */
        void onSuccess(BDLocation bdLocation);

        /**
         * 错误信息
         *
         * @param msg
         */
        void onError(String msg);
    }
}
