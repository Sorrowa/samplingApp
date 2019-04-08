package com.example.samplingapp.Activities.SamplingForm;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.core.Entity.Data.FormData;
import com.example.samplingapp.Activities.SamplingForm.SelectActivitys.SelectPointActivity;
import com.example.samplingapp.Activities.SamplingForm.ShowDataActivitys.SamplingStatusActivity;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.FormPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.utils.BaseUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SamplingFormActivity extends BaseActivity
        implements OnDateSetListener, FormPresenter.LocationListener {

    public static final int POINTGET = 0;

    private String projectId = null;

    @BindView(R.id.center_title)
    TextView title;
    @BindView(R.id.left_item)
    ImageView leftItem;
    @BindView(R.id.right_item)
    ImageView rightItem;
    //采样人员
    @BindView(R.id.person_name)
    EditText person_name;
    String personName;
    //点位选择
    @BindView(R.id.point_select)
    View point_select;//点位选择
    @BindView(R.id.point_name)
    TextView point_name;
    String pointName;
    //气温
    @BindView(R.id.climate)
    EditText Climate;
    String climate;
    //气压
    @BindView(R.id.pressure)
    EditText Pressure;
    String pressure;
    //天气
    @BindView(R.id.weather)
    TextView Weather;
    String weather;
    //采样方法
    @BindView(R.id.sampling_method)
    TextView sampling_method;
    String samplingMethod;
    @BindView(R.id.sampling_method_select)
    View sampling_method_select;//选择采样方法
    //采样状态
    @BindView(R.id.choose_status)
    View choose_status;
    @BindView(R.id.sampling_status)
    TextView sampling_status;
    @BindView(R.id.get_point_status)
    ImageView getPointStatus;
    String samplingStatus;
    //运输方法
    @BindView(R.id.transparent_way_select)
    View transparent_way_select;
    @BindView(R.id.transparent_way)
    TextView transparent_way;
    String transparentWay;
    //采样时间
    @BindView(R.id.time_pick)
    View time_pick;
    @BindView(R.id.sampling_time_text)
    TextView sampling_time_text;
    String time;
    //现场照片

    //备注

    //签名

    //当前位置
    @BindView(R.id.location)
    View location;
    @BindView(R.id.place)
    TextView place;
    @BindView(R.id.get_place)
    ImageView getPlace;
    String nowLocation;
    String ActLongitude;
    String ActLatitude;


    FormPresenter presenter;
    FormData data;//上传的form数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_form);
        ButterKnife.bind(this);
        data=new FormData();
        projectId = getIntent().getStringExtra("projectId");

        presenter=new FormPresenter();
        presenter.attachView(this);

        initView();
    }

    private void initView() {
        initToolBar();

        initPointSelect();

        initEditText();

        initWeatherPicker();

        initTimePicker();

        //查看样品信息
        initgetPointStatus();
    }

    /**
     * 查看样品信息
     */
    private void initgetPointStatus() {
        //todo:跳转到样品查看界面
        //必须要先选择点位，不然不能得到点位ID
        //todo:这里暂时写死 ：86aafe92-2fcd-4dc8-b19d-1fcc211e2b87
        getPointStatus.setOnClickListener(view -> {
            Intent intent=new Intent(SamplingFormActivity.this, SamplingStatusActivity.class);
            intent.putExtra("pointId"
                    ,"86aafe92-2fcd-4dc8-b19d-1fcc211e2b87");
            startActivity(intent);
        });
    }


    /**
     * 天气选择器
     */
    private void initWeatherPicker() {

    }

    private void initTimePicker(){
        time_pick.setOnClickListener(view -> {
            TimePickerDialog dialogMonthDayHourMin = new TimePickerDialog.Builder()
                    .setType(Type.YEAR_MONTH_DAY)
                    .setCallBack(SamplingFormActivity.this)
                    .setTitleStringId("请选择时间")
                    .setThemeColor(ContextCompat.getColor(this,R.color.generate))
                    .build();
            dialogMonthDayHourMin.show(getSupportFragmentManager(), "MONTH_DAY_HOUR_MIN");
        });
    }

    private void initEditText() {
    }

    /**
     * 提交和保存调用这个方法
     * 将所有的内容保存到data中去
     */
    private void saveAllData() {
//        personName = person_name.getText().toString();
        pointName = (String) point_name.getText();
        //nowLocation:

        data.setPointSampPlan(null);
        data.setProjectPointId(null);
        data.setActSampTime(sampling_time_text.getText().toString());
        data.setActSamper(person_name.getText().toString());
        data.setPointSatus(sampling_status.getText().toString());
        data.setSampMethod(sampling_method.getText().toString());
        data.setWeather(Weather.getText().toString());
        data.setTempature(Climate.getText().toString());
        //GTempature:不明意义，暂时不写
        data.setPressure(Pressure.getText().toString()+"MPa");
        data.setTransMethod(transparent_way.getText().toString());
        //还没加入备注信息
        //定位信息在定位结束之后直接加入data
    }

    /**
     * 点位选择
     */
    private void initPointSelect() {
        //todo:开启下一个活动选择对应的点位
        point_select.setOnClickListener(view -> {
            Intent intent = new Intent(SamplingFormActivity.this, SelectPointActivity.class);
            intent.putExtra("projectId", projectId);
            startActivityForResult(intent, POINTGET);
        });
    }


    private void initToolBar() {
        title.setText("采样详情");
        leftItem.setImageDrawable(getDrawable(R.drawable.go_back));
        rightItem.setImageDrawable(getDrawable(R.drawable.submit));
        leftItem.setOnClickListener(view -> finish());
        //todo:提交的逻辑

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理回调结果
    }


    /**
     * 时间选择器的回调接口
     * @param timePickerView
     * @param millseconds
     */
    @Override
    @SuppressLint("SimpleDateFormat")
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//精确到分钟
        String time = format.format(date);
        sampling_time_text.setText(time);
    }

    @OnClick({R.id.get_place,R.id.location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.get_place:
                //获取位置
                showToast("开始定位！");
                presenter.beginLocation(getApplicationContext(),this);
                break;
            case R.id.location:
                //获取位置
                showToast("开始定位！");
                presenter.beginLocation(getApplicationContext(),this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        presenter=null;
        super.onDestroy();
    }

    /**
     * 定位回调接口
     * @param latitude 纬度
     * @param longitude 经度
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(double latitude, double longitude) {
        place.setText("纬度为:"+latitude+" 经度为:"+longitude);
        nowLocation="纬度为:"+latitude+" 经度为:"+longitude;
        //todo:选择点位然后计算距离
        data.setActLatitude(String.valueOf(latitude));
        data.setActLongitude(String.valueOf(longitude));
    }

    /**
     * 定位回调接口
     * @param msg
     */
    @Override
    public void onError(String msg) {
        showToast(msg);
    }
}
