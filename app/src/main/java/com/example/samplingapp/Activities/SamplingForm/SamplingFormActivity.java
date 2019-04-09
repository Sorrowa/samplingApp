package com.example.samplingapp.Activities.SamplingForm;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.example.core.Entity.Data.PointDetailData;
import com.example.samplingapp.Activities.SamplingForm.SelectActivitys.SelectPointActivity;
import com.example.samplingapp.Activities.SamplingForm.ShowDataActivitys.SamplingStatusActivity;
import com.example.samplingapp.Adapter.RecycleViewAdapters.PhotoSelect.EnvironmentAdapter;
import com.example.samplingapp.Adapter.RecycleViewAdapters.PhotoSelect.SampleAdapter;
import com.example.samplingapp.Adapter.RecycleViewAdapters.PhotoSelect.SamplingAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.FormPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.mvp.ui.PreviewActivity;
import com.example.samplingapp.utils.BaseUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.wildma.pictureselector.PictureSelector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SamplingFormActivity extends BaseActivity
        implements OnDateSetListener, FormPresenter.LocationListener {

    public static final int POINTGET = 0;
    //图片选择
    public static final int ENVIRONMENT = 10;
    public static final int SAMPLING = 11;
    public static final int SAMPLE=12;

    private String projectId = null;
    private PointDetailData pointData = null;

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
    //照片
    //环境照片
    @BindView(R.id.add_environment_photo)
    ImageView add_environment_photo;
    @BindView(R.id.environment_photo)
    RecyclerView environment_photo;
    List<String> environmentPhotos = new ArrayList<>();
    EnvironmentAdapter environmentAdapter;
    //采样照片
    @BindView(R.id.add_sampling_photo)
    ImageView add_sampling_photo;
    @BindView(R.id.sampling_photo)
    RecyclerView sampling_photo;
    List<String> samplingPhotos = new ArrayList<>();
    SamplingAdapter samplingAdapter;
    //样品照片
    @BindView(R.id.add_sample_photo)
    ImageView add_sample_photo;
    @BindView(R.id.sample_photo)
    RecyclerView sample_photo;
    List<String> samplePhotos=new ArrayList<>();
    SampleAdapter sampleAdapter;
    //备注
    @BindView(R.id.samp_desc)
    EditText samp_desc;
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
        data = new FormData();
        projectId = getIntent().getStringExtra("projectId");

        presenter = new FormPresenter();
        presenter.attachView(this);

        initView();
    }

    private void initView() {
        initToolBar();
        initPointSelect();
        initEditText();
        initWeatherPicker();
        initTimePicker(0);
        //查看样品信息
        initgetPointStatus();
        initPictureSelect();
    }

    /**
     * 查看样品信息
     */
    private void initgetPointStatus() {
        //todo:跳转到样品查看界面
        //必须要先选择点位，不然不能得到点位ID
        getPointStatus.setOnClickListener(view -> {
            if (pointData == null) {
                showToast("请先选择点位!");
                return;
            }
            Intent intent = new Intent(SamplingFormActivity.this, SamplingStatusActivity.class);
            intent.putExtra("pointId"
                    , pointData.getProjectPointId());
            startActivity(intent);
        });
    }


    /**
     * 天气选择器
     */
    private void initWeatherPicker() {

    }

    private void initTimePicker(int i) {
        time_pick.setOnClickListener(view -> {
            TimePickerDialog dialogMonthDayHourMin = new TimePickerDialog.Builder()
                    .setType(Type.YEAR_MONTH_DAY)
                    .setCallBack(SamplingFormActivity.this)
                    .setTitleStringId("请选择时间")
                    .setThemeColor(ContextCompat.getColor(this, R.color.generate))
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
        data.setProjectPointId(pointData.getProjectPointId());
        data.setActSampTime(sampling_time_text.getText().toString());
        data.setActSamper(person_name.getText().toString());
        data.setPointSatus(sampling_status.getText().toString());
        data.setSampMethod(sampling_method.getText().toString());
        data.setWeather(Weather.getText().toString());
        data.setTempature(Climate.getText().toString());
        //GTempature:不明意义，暂时不写
        data.setPressure(Pressure.getText().toString() + "MPa");
        data.setTransMethod(transparent_way.getText().toString());
        data.setSampDesc(samp_desc.getText().toString());//备注
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


    /**
     * 初始化图片选择器
     */
    private void initPictureSelect() {
        initEnvironment();//初始化环境
        initSampling();//初始化采样照片
        initSample();//初始化样品照片
    }

    private void initSample() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        sample_photo.setLayoutManager(manager);
        sampleAdapter=new SampleAdapter(this,samplingPhotos);
        sample_photo.setAdapter(sampleAdapter);

        add_sample_photo.setOnClickListener(view -> {
            if (samplePhotos.size()>=20){
                showToast("样品照片数量达到上限!");
                return;
            }
            PictureSelector
                    .create(SamplingFormActivity.this, SAMPLE)
                    .selectPicture(true, 600, 600, 1, 1);

        });
    }


    private void initSampling() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        sampling_photo.setLayoutManager(manager);
        samplingAdapter=new SamplingAdapter(this,samplingPhotos);
        sampling_photo.setAdapter(samplingAdapter);

        add_sampling_photo.setOnClickListener(view -> {
            if (samplingPhotos.size()>=20){
                showToast("采样照片数量达到上限!");
                return;
            }
            PictureSelector
                    .create(SamplingFormActivity.this, SAMPLING)
                    .selectPicture(true, 600, 600, 1, 1);
        });
    }


    private void initEnvironment() {
        /**
         * 初始化RecycleView
         */
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        environment_photo.setLayoutManager(manager);
        environmentAdapter = new EnvironmentAdapter(this, environmentPhotos);
        environment_photo.setAdapter(environmentAdapter);

        /**
         * create()方法参数一是上下文，在activity中传activity.this，在fragment中传fragment.this。参数二为请求码，用于结果回调onActivityResult中判断
         * selectPicture()方法参数分别为 是否裁剪、裁剪后图片的宽(单位px)、裁剪后图片的高、宽比例、高比例。都不传则默认为裁剪，宽200，高200，宽高比例为1：1。
         */
        add_environment_photo.setOnClickListener(view -> {
            if (environmentPhotos.size()>=10){
                showToast("环境照片数量达到上限!");
                return;
            }
            PictureSelector
                    .create(SamplingFormActivity.this, ENVIRONMENT)
                    .selectPicture(true, 600, 600, 1, 1);
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理回调结果
        switch (resultCode) {
            case POINTGET:
                if (data != null && data.getParcelableExtra("pointData") != null) {
                    pointData = data.getParcelableExtra("pointData");
                    point_name.setText(pointData.getName());
                    if (this.data.getActLatitude() != 0 && this.data.getActLatitude() != 0) {
                        getAndShowDistance();
                    }
                }
                break;
            case PreviewActivity
                    .PREVIEWACTIVITY:
                if (data != null) {
                    String path = data.getStringExtra("path");
                    String type = data.getStringExtra("type");
                    if (type.equals("1")) {
                        environmentPhotos.remove(path);
                        environmentAdapter.setPhotos(environmentPhotos);
                        runOnUiThread(() -> {
                            environmentAdapter.notifyDataSetChanged();
                        });
                    } else if (type.equals("2")) {
                        //采样照片
                        samplingPhotos.remove(path);
                        samplingAdapter.setPhotos(samplingPhotos);
                        runOnUiThread(() -> {
                            samplingAdapter.notifyDataSetChanged();
                        });
                    } else if (type.equals("3")) {
                        //样品照片
                        samplePhotos.remove(path);
                        sampleAdapter.setPhotos(samplePhotos);
                        runOnUiThread(() -> {
                            sampleAdapter.notifyDataSetChanged();
                        });
                    }
                }
                break;
        }
        switch (requestCode) {
            case ENVIRONMENT:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    environmentPhotos.add(picturePath);
                    environmentAdapter.setPhotos(environmentPhotos);
                    runOnUiThread(() -> {
                        environmentAdapter.notifyDataSetChanged();
                    });
                }
                break;
            case SAMPLING:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    samplingPhotos.add(picturePath);
                    samplingAdapter.setPhotos(samplingPhotos);
                    runOnUiThread(() -> {
                        samplingAdapter.notifyDataSetChanged();
                    });
                }
                break;
            case SAMPLE:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    samplePhotos.add(picturePath);
                    sampleAdapter.setPhotos(samplePhotos);
                    runOnUiThread(() -> {
                        sampleAdapter.notifyDataSetChanged();
                    });
                }
                break;
        }
    }

    /**
     * 显示距离信息
     */
    @SuppressLint("SetTextI18n")
    private void getAndShowDistance() {
        this.data.setDistance(String.valueOf(BaseUtil.getDistance(Double.parseDouble(pointData.getLatitude())
                , Double.parseDouble(pointData.getLongitude())
                , this.data.getActLatitude()
                , this.data.getActLongitude())));
        place.setText(nowLocation + " 距离为:" + this.data.getDistance());
    }


    /**
     * 时间选择器的回调接口
     *
     * @param timePickerView
     * @param millseconds
     */
    @Override
    @SuppressLint("SimpleDateFormat")
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date date = new Date(millseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time = format.format(date);
        sampling_time_text.setText(time);
        TimePickerDialog dialogMonthDayHourMin = new TimePickerDialog.Builder()
                .setType(Type.HOURS_MINS)
                .setTitleStringId("请选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.generate))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    @SuppressLint("SimpleDateFormat")
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        Date date = new Date(millseconds);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                        time = time + " " + format.format(date);
                        sampling_time_text.setText(time);
                    }
                })
                .build();
        dialogMonthDayHourMin.show(getSupportFragmentManager(), "HOUR_MIN");
    }

    @OnClick({R.id.get_place, R.id.location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_place:
                //获取位置
                showToast("开始定位！");
                presenter.beginLocation(getApplicationContext(), this);
                break;
            case R.id.location:
                //获取位置
                showToast("开始定位！");
                presenter.beginLocation(getApplicationContext(), this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        presenter = null;
        super.onDestroy();
    }

    /**
     * 定位回调接口
     *
     * @param latitude  纬度
     * @param longitude 经度
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onSuccess(double latitude, double longitude) {
        place.setText("纬度为:" + latitude + " 经度为:" + longitude);
        nowLocation = "纬度为:" + latitude + " 经度为:" + longitude;
        data.setActLatitude(latitude);
        data.setActLongitude(longitude);
        if (pointData != null) {
            getAndShowDistance();
        }
    }

    /**
     * 定位回调接口
     *
     * @param msg
     */
    @Override
    public void onError(String msg) {
        showToast(msg);
    }


    //todo:询问是否退出，保存内容
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
