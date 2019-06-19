package com.example.samplingapp.utils.Location;

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.example.samplingapp.Base.BaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.jessyan.autosize.internal.CancelAdapt;

public class BNaviGuideActivity extends BaseActivity implements CancelAdapt {
    private final static String TAG = BNaviGuideActivity.class.getSimpleName();

    private BikeNavigateHelper mNaviHelper;

    BikeNaviLaunchParam param;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNaviHelper.quit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNaviHelper.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNaviHelper.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNaviHelper = BikeNavigateHelper.getInstance();

        View view = mNaviHelper.onCreate(BNaviGuideActivity.this);

        if (view != null) {
            setContentView(view);
        }

//        mNaviHelper.setBikeNaviStatusListener(() -> Log.d("zzh", "onNaviExit"));

//        mNaviHelper.setTTsPlayer((s, b) -> {
//            Log.d("zzh", s);
//            return 0;
//        });

        mNaviHelper.startBikeNavi(BNaviGuideActivity.this);
    }
}
