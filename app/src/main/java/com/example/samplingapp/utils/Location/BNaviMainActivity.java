package com.example.samplingapp.utils.Location;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.bikenavi.BikeNavigateHelper;
import com.baidu.mapapi.bikenavi.adapter.IBEngineInitListener;
import com.baidu.mapapi.bikenavi.adapter.IBRoutePlanListener;
import com.baidu.mapapi.bikenavi.model.BikeRoutePlanError;
import com.baidu.mapapi.bikenavi.params.BikeNaviLaunchParam;
import com.baidu.mapapi.bikenavi.params.BikeRouteNodeInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BNaviMainActivity extends BaseActivity {


    private BitmapDescriptor bdStart = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_start);
    private BitmapDescriptor bdEnd = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_end);

    private double Latitude;
    private double Longitude;
    private double Latitude1;
    private double Longitude1;

    @BindView(R.id.baidu_map)
    MapView mMapView;
    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView left_item;
    @BindView(R.id.begin_nav)
    TextView begin_nav;
    private BaiduMap mBaiduMap;

    /*导航起终点Marker，可拖动改变起终点的坐标*/
    private Marker mStartMarker;
    private Marker mEndMarker;

    private LatLng startPt;
    private LatLng endPt;

    private BikeNaviLaunchParam bikeParam;
    private boolean isFirstLoc = true; // 是否首次定位

    private static boolean isPermissionRequested = false;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnavi_main);
        mUnbinder = ButterKnife.bind(this);

        initToolbar();
        initMapStatusFirst();
        initLocation();
//        startPt = new LatLng(Latitude, Longitude);
        /*开始骑行导航*/
        begin_nav.setOnClickListener(view -> startBikeNavi());
    }

    private void initLocation() {
        Latitude1 = getIntent().getDoubleExtra("Latitude1", 0);
        Longitude1 = getIntent().getDoubleExtra("Longitude1", 0);
        endPt = new LatLng(Latitude1, Longitude1);
        getStartData();
    }

    /**
     * 获取起点位置
     */
    private void getStartData() {
        Location.beginToGetNowLocation(this, new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                startPt = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                initMapStatus();
                /*构造导航起终点参数对象*/
                BikeRouteNodeInfo bikeStartNode = new BikeRouteNodeInfo();
                bikeStartNode.setLocation(startPt);
                BikeRouteNodeInfo bikeEndNode = new BikeRouteNodeInfo();
                bikeEndNode.setLocation(endPt);
                bikeParam = new BikeNaviLaunchParam().startNodeInfo(bikeStartNode).endNodeInfo(bikeEndNode);
                /* 初始化起终点Marker */
                initOverlay();
            }
        });
    }

    private void initToolbar() {
        title.setText("导航");
        left_item.setImageDrawable(getDrawable(R.drawable.go_back));
        left_item.setOnClickListener(view -> {
            BNaviMainActivity.this.onBackPressed();
        });
    }


    /**
     * 初始化地图状态
     */
    private void initMapStatus() {
        if (isFirstLoc) {
            isFirstLoc = false;
            mBaiduMap = mMapView.getMap();
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(startPt).zoom(18.0f);
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }

    }

    /**
     * 初始化导航起终点Marker
     */
    public void initOverlay() {
        MarkerOptions ooA = new MarkerOptions().position(startPt).icon(bdStart)
                .zIndex(9).draggable(true);

        mStartMarker = (Marker) (mBaiduMap.addOverlay(ooA));
        mStartMarker.setDraggable(false);
        MarkerOptions ooB = new MarkerOptions().position(endPt).icon(bdEnd)
                .zIndex(5);
        mEndMarker = (Marker) (mBaiduMap.addOverlay(ooB));
        mEndMarker.setDraggable(false);
    }

    /**
     * 初始化地图状态
     */
    private void initMapStatusFirst() {
        mBaiduMap = mMapView.getMap();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(40.048424, 116.313513)).zoom(15);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 开始骑行导航
     */
    private void startBikeNavi() {
        Log.d("zzh", "startBikeNavi");
        try {
            BikeNavigateHelper.getInstance().initNaviEngine(this, new IBEngineInitListener() {
                @Override
                public void engineInitSuccess() {
                    Log.d("zzh", "BikeNavi engineInitSuccess");
                    routePlanWithBikeParam();
                }

                @Override
                public void engineInitFail() {
                    Log.d("zzh", "BikeNavi engineInitFail");
                    BikeNavigateHelper.getInstance().unInitNaviEngine();
                }
            });
        } catch (Exception e) {
            Log.d("zzh", "startBikeNavi Exception");
            e.printStackTrace();
        }
    }

    /**
     * 发起骑行导航算路
     */
    private void routePlanWithBikeParam() {
        BikeNavigateHelper.getInstance().routePlanWithRouteNode(bikeParam, new IBRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
            }

            @Override
            public void onRoutePlanSuccess() {
                Intent intent = new Intent();
                intent.setClass(BNaviMainActivity.this, BNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(BikeRoutePlanError error) {
                showToast("距离太远，无法导航");
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
//        bdStart.recycle();
//        bdEnd.recycle();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
    }
}
