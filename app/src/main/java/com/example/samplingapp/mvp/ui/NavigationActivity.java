package com.example.samplingapp.mvp.ui;


import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.example.samplingapp.Base.BaseActivity;

/**
 * 导航
 */
public class NavigationActivity extends BaseActivity {

    BikeNavigateHelper mNaviHelper;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNaviHelper = BikeNavigateHelper.getInstance();
        view = mNaviHelper.onCreate(NavigationActivity.this);
        if (view != null) {
            setContentView(view);
        }
        mNaviHelper.startBikeNavi(NavigationActivity.this);
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
    protected void onDestroy() {
        super.onDestroy();
        mNaviHelper.quit();
    }

}
