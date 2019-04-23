package com.example.samplingapp.Activities.SamplingForm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.core.Entity.Data.FileData;
import com.example.core.Entity.Data.FileDetailData;
import com.example.core.Entity.Data.FormData;
import com.example.core.Entity.Data.FormDetailData;
import com.example.core.Entity.Data.FormDetailSubmitData;
import com.example.core.Entity.Data.PointDetailData;
import com.example.core.Entity.Data.SampMethodData;
import com.example.core.Others.GetPathFromUri;
import com.example.core.Others.Rom;
import com.example.network.InternetUtil;
import com.example.samplingapp.Activities.SamplingForm.SelectActivitys.MethodSelectActivity;
import com.example.samplingapp.Activities.SamplingForm.SelectActivitys.SelectPointActivity;
import com.example.samplingapp.Activities.SamplingForm.ShowDataActivitys.SamplingStatusActivity;
import com.example.samplingapp.Adapter.RecycleViewAdapters.Select.PhotoSelect.EnvironmentAdapter;
import com.example.samplingapp.Adapter.RecycleViewAdapters.Select.PhotoSelect.SampleAdapter;
import com.example.samplingapp.Adapter.RecycleViewAdapters.Select.PhotoSelect.SamplingAdapter;
import com.example.samplingapp.Adapter.RecycleViewAdapters.Select.VideoSelecte.VideoAdapter;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Presenter.Form.FormPresenter;
import com.example.samplingapp.R;
import com.example.samplingapp.mvp.ui.Dialog.BottomFullDialog;
import com.example.samplingapp.mvp.ui.Dialog.BottomSelectDialog;
import com.example.samplingapp.mvp.ui.DrawActivity;
import com.example.samplingapp.mvp.ui.PreviewActivity;
import com.example.samplingapp.mvp.ui.VideoActivity;
import com.example.samplingapp.utils.BaseUtil;
import com.example.samplingapp.utils.FileUtil;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.vincent.videocompressor.VideoCompress;
import com.wildma.pictureselector.PictureSelector;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("NonAtomicOperationOnVolatileField")
public class SamplingFormActivity extends BaseActivity
        implements OnDateSetListener, FormPresenter.LocationListener
        , FormPresenter.FileUploadListener, FormPresenter.SaveOrSubmitListener, FormPresenter.PreviousListener {

    public static boolean canDelete = true;

    public static final int POINTGET = 0;
    //图片选择
    public static final int ENVIRONMENT = 10;
    public static final int SAMPLING = 11;
    public static final int SAMPLE = 12;

    //签名
    public static final int SAMPLEMANONE = 21;
    public static final int SAMPLEMANTWO = 22;
    public static final int MONITOR = 23;

    //视频选择
    public static final int REQUEST_CODE_PICK = 30;
    public static final int REQUEST_CODE_SHOOT = 31;

    //方法选择
    public static final int METHOD = 40;

    private String projectId = null;
    private PointDetailData pointData = null;

    private boolean isSaved = false;

    private Handler handler = new Handler();

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
    //位置获取
//    R.id.get_place, R.id.location
    @BindView(R.id.get_place)
    ImageView get_place;
    @BindView(R.id.location)
    View location;
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
    @BindView(R.id.weahter)
    TextView Weather;
    @BindView(R.id.weahter_arrows)
    ImageView getWeatherList;
    String weather;
    private int nowWeather = 0;
    //采样方法
    @BindView(R.id.sampling_method)
    TextView sampling_method;
    @BindView(R.id.sampling_method_arrows)
    ImageView sampling_method_arrows;
    SampMethodData samplingMethod;
    @BindView(R.id.sampling_method_select)
    View sampling_method_select;//选择采样方法
    //采样状态
    @BindView(R.id.choose_status)
    View choose_status;
    @BindView(R.id.sampling_status)
    TextView sampling_status;
    @BindView(R.id.sampling_status_arrows)
    ImageView getPointStatus;
    @BindView(R.id.get_point_status)
    ImageView get_point_status;
    String samplingStatus;
    private int nowStatus = 0;
    //运输方法
    @BindView(R.id.transparent_way_select)
    View transparent_way_select;
    @BindView(R.id.transparent_way)
    TextView transparent_way;
    @BindView(R.id.transparent_way_arrows)
    ImageView transparent_way_arrows;
    int transparentWay;
    //采样时间
    @BindView(R.id.time_pick)
    View time_pick;
    @BindView(R.id.sampling_time_text)
    TextView sampling_time_text;
    String time;
    //照片
    @BindView(R.id.shrink_picture)
    ImageView shrink_picture;
    @BindView(R.id.picture_domain)
    View picture_domain;
    //环境照片
    @BindView(R.id.add_environment_photo)
    ImageView add_environment_photo;
    @BindView(R.id.environment_photo)
    RecyclerView environment_photo;
    List<String> environmentPhotos = new ArrayList<>();
    EnvironmentAdapter environmentAdapter;
    private volatile int environmentPictureNum = 0;
    //采样照片
    @BindView(R.id.add_sampling_photo)
    ImageView add_sampling_photo;
    @BindView(R.id.sampling_photo)
    RecyclerView sampling_photo;
    List<String> samplingPhotos = new ArrayList<>();
    SamplingAdapter samplingAdapter;
    private volatile int samplingPictureNum = 0;
    //样品照片
    @BindView(R.id.add_sample_photo)
    ImageView add_sample_photo;
    @BindView(R.id.sample_photo)
    RecyclerView sample_photo;
    List<String> samplePhotos = new ArrayList<>();
    SampleAdapter sampleAdapter;
    private volatile int samplePictureNum = 0;
    //视频
    @BindView(R.id.add_sample_video)
    ImageView add_sample_video;
    @BindView(R.id.sample_video)
    RecyclerView sample_video;
    List<String> sampleVideo = new ArrayList<>();
    VideoAdapter videoAdapter;
    private volatile int videoNum = 0;
    //备注
    @BindView(R.id.samp_desc)
    EditText samp_desc;
    //签名
    @BindView(R.id.shrink_sign)
    ImageView shrink_sign;
    @BindView(R.id.sign_domain)
    View sign_domain;
    //一号采样签名
    @BindView(R.id.sample_man_sign)
    ImageView sample_man_sign;
    String sampleManOnePath = null;
    private volatile int sampleManOneNum = 0;
    //二号采样签名
    @BindView(R.id.sample_man_sign_two)
    ImageView sample_man_sign_two;
    String sampleManTwoPath = null;
    private volatile int sampleManTwoNum = 0;
    //监督人签名
    @BindView(R.id.monitor_man_sign)
    ImageView monitor_man_sign;
    String monitorManSignPath = null;
    private volatile int monitorManSignNum = 0;
    //所属单位
    @BindView(R.id.company_info)
    EditText company_info;
    //当前位置
    @BindView(R.id.place)
    TextView place;
    String nowLocation;

    //保存与提交
    //保存
    @BindView(R.id.save)
    View save;
    @BindView(R.id.save_sampling)
    View save_sampling;

    private Dialog pictureUploadDialog;
    private Dialog formUploadDialog;

    FormPresenter presenter;
    FormData data;//上传的form数据

    List<FileData> files = new ArrayList<>();

    private boolean isSubmit = false;

    private boolean canGoback = true;//判断是否可以退出当前活动

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_form);
        ButterKnife.bind(this);

        data = new FormData();//存储表单信息
        presenter = new FormPresenter();
        presenter.attachView(this);

        //0:待提交 1:已提交
        //待提交可以修改，已提交无法修改界面
        Intent intent = getIntent();
        String status = intent.getStringExtra("status");
        String formId = intent.getStringExtra("formId");
        if (formId != null && status != null) {
            //获取已经存储的表单数据
            presenter.getPreviousForm(formId, this);
            showLoadingDialog();
        }

        projectId = getIntent().getStringExtra("projectId");


        initView();

        if (status != null && status.equals("1")) {
            unEditableView();
            canGoback = false;
        }
        if (status != null) {
            get_place.setVisibility(View.GONE);
            place.setClickable(false);
            location.setClickable(false);
        }
        canGoback = false;
        if (status == null) {
            canGoback = true;
            showToast("开始定位");
            presenter.beginLocation(getApplicationContext(), this);
        }
    }

    /**
     * 将view全部变为不可修改模式
     */
    private void unEditableView() {

        isSaved = true;//返回时不会弹出提示信息

        rightItem.setVisibility(View.GONE);

        person_name.setFocusable(false);
        person_name.setFocusableInTouchMode(false);

        location.setClickable(false);

        get_place.setClickable(false);

        point_select.setClickable(false);

        Climate.setFocusable(false);
        Climate.setFocusableInTouchMode(false);

        Pressure.setFocusable(false);
        Pressure.setFocusableInTouchMode(false);

        Weather.setClickable(false);
        getWeatherList.setClickable(false);

        sampling_method_select.setClickable(false);
        sampling_method_arrows.setClickable(false);

        choose_status.setClickable(false);
        getPointStatus.setClickable(false);
        sampling_status.setClickable(false);

        transparent_way_select.setClickable(false);
        transparent_way_arrows.setClickable(false);
        transparent_way.setClickable(false);

        time_pick.setClickable(false);
        sampling_time_text.setClickable(false);

        add_environment_photo.setClickable(false);
        add_environment_photo.setVisibility(View.GONE);

        add_sampling_photo.setClickable(false);
        add_sampling_photo.setVisibility(View.GONE);

        add_sample_photo.setClickable(false);
        add_sample_photo.setVisibility(View.GONE);

        add_sample_video.setClickable(false);
        add_sample_video.setVisibility(View.GONE);

        samp_desc.setFocusable(false);
        samp_desc.setFocusableInTouchMode(false);

        sample_man_sign.setClickable(false);
        sample_man_sign_two.setClickable(false);
        monitor_man_sign.setClickable(false);

        company_info.setFocusable(false);
        company_info.setFocusableInTouchMode(false);

        save_sampling.setVisibility(View.GONE);
    }

    private void initView() {
        initToolBar();
        initPointSelect();
        initTimePicker(0);
        //查看样品信息
        initgetPointStatus();
        initPictureSelect();
        initSign();
        initVideo();
        initWeather();
        initTransport();
        initSampleMethod();
        initSave();

        initEdit();
    }

    private void initEdit() {
        person_name.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                person_name.clearFocus();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    InputMethodManager imm = null;
                    imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(person_name.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
        Climate.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                Climate.clearFocus();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    InputMethodManager imm = null;
                    imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(Climate.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
        Pressure.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                Pressure.clearFocus();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    InputMethodManager imm = null;
                    imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(Pressure.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
        company_info.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                company_info.clearFocus();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    InputMethodManager imm = null;
                    imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
                    if (manager != null)
                        manager.hideSoftInputFromWindow(company_info.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return true;
            }
            return false;
        });
    }

    /**
     * 初始化采样方法选择
     */
    private void initSampleMethod() {
        sampling_method.setOnClickListener(view -> {
            Intent intent = new Intent(SamplingFormActivity.this
                    , MethodSelectActivity.class);
            startActivityForResult(intent, METHOD);
        });
    }

    /**
     * 初始化交通
     */
    private void initTransport() {
        transparent_way.setOnClickListener(view -> showTransList());
        transparent_way_arrows.setOnClickListener(view -> showTransList());
    }

    private void showTransList() {
        List<String> list = new ArrayList<>();
        list.add("空运");
        list.add("汽运");
        list.add("步行");
        list.add("海运");
        list.add("自行车");

        BottomFullDialog bottomFullDialog =
                new BottomFullDialog(SamplingFormActivity.this
                        , R.style.BottomFullDialog
                        , list
                        , item -> {
                    if (item < 0) {
                        return;
                    }
                    transparentWay = item;
                    transparent_way.setText(list.get(item));
                }
                        , transparentWay);
        bottomFullDialog.setCancelable(true);
        bottomFullDialog.setCanceledOnTouchOutside(true);
        bottomFullDialog.show();
    }

    /**
     * 初始化天气选择器
     */
    private void initWeather() {

        Weather.setOnClickListener(view -> showWeatherList());
        getWeatherList.setOnClickListener(view -> showWeatherList());

    }

    private void showWeatherList() {
        List<String> list = new ArrayList<>();
        list.add("晴天");
        list.add("多云");
        list.add("阴天");
        list.add("小雨");
        list.add("中雨");
        list.add("暴雨");
        list.add("雪天");
        list.add("冰雹");

        BottomFullDialog bottomFullDialog =
                new BottomFullDialog(SamplingFormActivity.this
                        , R.style.BottomFullDialog
                        , list
                        , item -> {
                    if (item < 0) {
                        return;
                    }
                    nowWeather = item;
                    Weather.setText(list.get(item));
                }
                        , nowWeather);
        bottomFullDialog.setCancelable(true);
        bottomFullDialog.setCanceledOnTouchOutside(true);
        bottomFullDialog.show();
    }

    /**
     * 保存逻辑
     */
    private void initSave() {
        save.setOnClickListener(view
                -> saveBehavior(false));
    }

    private void saveBehavior(boolean isSubmit) {
        if (!isEnough()) {
            return;
        }

        environmentPictureNum = environmentPhotos.size();
        samplingPictureNum = samplingPhotos.size();
        videoNum = sampleVideo.size();
        samplePictureNum = samplePhotos.size();

        if (isSubmit && !CanSubmit()) {
            return;
        }

        if ( isSubmit && (environmentPictureNum <= 0
                || samplingPictureNum <= 0
                || samplePictureNum <= 0
                || videoNum <= 0
                || sampleManOneNum <= 0
                || sampleManTwoNum <= 0
                || monitorManSignNum <= 0)) {
            //如果没有图片上传
            showToast("每种图片都需要提交");
            return;
        }
        showDialog("1");
        this.isSubmit = isSubmit;
        if (environmentPictureNum <= 0
                && samplingPictureNum <= 0
                && samplePictureNum <= 0
                && videoNum <= 0
                && sampleManOneNum <= 0
                && sampleManTwoNum <= 0
                && monitorManSignNum <= 0) {
            //如果没有图片上传
            runOnUiThread(() -> pictureUploadDialog.dismiss());
            saveAllData(this.isSubmit);
        }

        if (environmentPictureNum > 0) {
            for (String path : environmentPhotos) {
                presenter.uploadFile(path, this, FormPresenter.ENVIRONMENT);
            }
        }
        if (samplingPictureNum > 0) {
            for (String path : samplingPhotos) {
                presenter.uploadFile(path, this, FormPresenter.SAMPLING);
            }
        }
        if (samplePictureNum > 0) {
            for (String path : samplePhotos) {
                presenter.uploadFile(path, this, FormPresenter.SAMPLE);
            }
        }
        if (videoNum > 0) {
            for (String path : sampleVideo) {
                presenter.uploadFile(path, this, FormPresenter.VIDEO);
            }
        }
        if (sampleManOneNum > 0) {
            presenter.uploadFile(sampleManOnePath, this, FormPresenter.SAMPLEMAN);
        }
        if (sampleManTwoNum > 0) {
            presenter.uploadFile(sampleManTwoPath, this, FormPresenter.SAMPLEMAN);
        }
        if (monitorManSignNum > 0) {
            presenter.uploadFile(monitorManSignPath, this, FormPresenter.MONITOR);
        }
    }

    private boolean CanSubmit() {
        if (person_name.getText().toString().equals("")
                || Climate.getText().toString().equals("")
                || Pressure.getText().toString().equals("")
                || sampling_method.getText().equals("请选择")
                || sampling_status.getText().equals("请选择")
                || transparent_way.getText().equals("请选择")
                || samp_desc.getText().toString().equals("")
                || company_info.getText().toString().equals("")
                || Weather.getText().equals("")) {
            showToast("表单没有填写完全!");
            return false;
        }

        return true;
    }

    /**
     * 判断是否可以保存
     */
    private boolean isEnough() {
        if (pointData == null
                || person_name.getText().toString().equals("")
                || time == null
                || sampling_status.getText().toString().equals("")) {
            if (pointData == null)
                showToast("点位未选择");
            if (person_name.getText().toString().equals(""))
                showToast("请输入采样人");
            if (time == null)
                showToast("请选择时间");
            if (sampling_status.getText().toString().equals(""))
                showToast("请选择状态");
            return false;
        }
        return true;
    }

    /**
     * 初始化视频列表
     */
    private void initVideo() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        sample_video.setLayoutManager(manager);
        videoAdapter = new VideoAdapter(sampleVideo, this);
        sample_video.setAdapter(videoAdapter);
        add_sample_video.setOnClickListener(view -> {


            if (sampleVideo.size() > 1) {
                showToast("超出视频可接受上限！");
                return;
            }

            BottomSelectDialog bottomSelectDialog = new BottomSelectDialog
                    (SamplingFormActivity.this
                            , R.style.BottomSelectDialog
                            , new BottomSelectDialog.OnClickListener() {
                        @Override
                        public void shoot() {
                            //代码冲突，所以放到BottomSelect中去
                            com.luck.picture.lib.PictureSelector.create(SamplingFormActivity.this)
                                    .openCamera(PictureMimeType.ofVideo())
                                    .compress(true)
                                    .synOrAsy(true)
                                    .videoQuality(1)
                                    .recordVideoSecond(60)
                                    .forResult(REQUEST_CODE_SHOOT);
                        }

                        @Override
                        public void select() {
                            if (Rom.isMiui()) {//是否是小米设备,是的话用到弹窗选取入口的方法去选取视频
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
                                startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), REQUEST_CODE_PICK);
                            } else {//直接跳到系统相册去选取视频
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("video/*");
                                startActivityForResult(Intent.createChooser(intent, "选择要导入的视频"), REQUEST_CODE_PICK);
                            }
                        }
                    });
            bottomSelectDialog.show();
        });
    }


    /**
     * 查看样品信息
     */
    private void initgetPointStatus() {
        //必须要先选择点位，不然不能得到点位ID
        get_point_status.setOnClickListener(view -> {
            if (pointData == null) {
                showToast("请先选择点位!");
                return;
            }
            Intent intent = new Intent(SamplingFormActivity.this, SamplingStatusActivity.class);
            intent.putExtra("pointId"
                    , pointData.getProjectPointId());
            startActivity(intent);
        });
//        getPointStatus.setOnClickListener(view -> {
//            if (pointData == null) {
//                showToast("请先选择点位!");
//                return;
//            }
//            Intent intent = new Intent(SamplingFormActivity.this, SamplingStatusActivity.class);
//            intent.putExtra("pointId"
//                    , pointData.getProjectPointId());
//            startActivity(intent);
//        });
        //todo:点击事件处理
        List<String> list = new ArrayList<>();
        list.add("正常");
        list.add("异常");
        sampling_status.setOnClickListener(view -> {
            BottomFullDialog bottomFullDialog =
                    new BottomFullDialog(SamplingFormActivity.this
                            , R.style.BottomFullDialog
                            , list
                            , item -> {
                        if (item < 0) {
                            return;
                        }
                        nowStatus = item;
                        sampling_status.setText(list.get(item));
                    }
                            , nowStatus);
            bottomFullDialog.setCancelable(true);
            bottomFullDialog.setCanceledOnTouchOutside(true);
            bottomFullDialog.show();
        });
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

    /**
     * 提交和保存调用这个方法
     * 将所有的内容保存到data中去
     */
    private void saveAllData(boolean isSubmit) {
        //只能传入数据
        showDialog("2");//显示dialog
        if (pointData != null && pointData.getPointSampPlan() != null && pointData.getProjectPointId() != null) {
            data.setPointSampPlan(pointData.getPointSampPlan());
            data.setProjectPointId(pointData.getProjectPointId());
        }
        data.setActSampTime(sampling_time_text.getText().toString());
        data.setActSamper(person_name.getText().toString());
        if (sampling_status.getText().toString().equals("正常"))
            data.setPointSatus("0");
        else
            data.setPointSatus("1");
        if (samplingMethod != null)
            data.setSampMethod(samplingMethod.getSampMethod());
        data.setWeather(Weather.getText().toString());
        data.setTempature(Climate.getText().toString() + " ℃");
        data.setGTempature(Climate.getText().toString() + " ℃");
        data.setPressure(Pressure.getText().toString() + " MPa");
        data.setTransMethod(transparent_way.getText().toString());
        data.setSampDesc(samp_desc.getText().toString());//备注
        data.setUserUnit(company_info.getText().toString());//所属单位
        presenter.saveForm(data, files, this, isSubmit);
        isSaved = true;
    }

    /**
     * 点位选择
     */
    private void initPointSelect() {
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
        leftItem.setOnClickListener(view -> doBack());
        //todo:提交的逻辑
        rightItem.setOnClickListener(view -> {
            AlertDialog.Builder normalDialog =
                    new AlertDialog.Builder(SamplingFormActivity.this);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("提交之后将不能更改，确认提交吗？");
            normalDialog.setPositiveButton("确认",
                    (dialog, which) -> {
                        saveBehavior(true);
                        dialog.dismiss();
                    });
            normalDialog.setNegativeButton("取消",
                    (dialog, which) -> dialog.dismiss());
            // 显示
            normalDialog.show();
        });
    }


    /**
     * 初始化图片选择器
     */
    private void initPictureSelect() {

        //收起View
        shrink_picture.setOnClickListener(view -> {
            if (picture_domain.getVisibility() == View.GONE) {
                picture_domain.setVisibility(View.VISIBLE);
                shrink_picture.setImageDrawable(getDrawable(R.drawable.down_arrow));
            } else {
                picture_domain.setVisibility(View.GONE);
                shrink_picture.setImageDrawable(getDrawable(R.drawable.right_arrows));
            }
        });

        initEnvironment();//初始化环境
        initSampling();//初始化采样照片
        initSample();//初始化样品照片
    }

    private void initSample() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        sample_photo.setLayoutManager(manager);
        sampleAdapter = new SampleAdapter(this, samplePhotos);
        sample_photo.setAdapter(sampleAdapter);

        add_sample_photo.setOnClickListener(view -> {
            if (samplePhotos.size() >= 20) {
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
        samplingAdapter = new SamplingAdapter(this, samplingPhotos);
        sampling_photo.setAdapter(samplingAdapter);

        add_sampling_photo.setOnClickListener(view -> {
            if (samplingPhotos.size() >= 20) {
                showToast("采样照片数量达到上限!");
                return;
            }
            PictureSelector
                    .create(SamplingFormActivity.this, SAMPLING)
                    .selectPicture(true, 500, 500, 0, 0);

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
            if (environmentPhotos.size() >= 10) {
                showToast("环境照片数量达到上限!");
                return;
            }
            PictureSelector
                    .create(SamplingFormActivity.this, ENVIRONMENT)
                    .selectPicture(true, 600, 600, 1, 1);
        });

    }

    /**
     * 签名信息初始化
     */
    private void initSign() {

        shrink_sign.setOnClickListener(view -> {
            if (sign_domain.getVisibility() == View.GONE) {
                sign_domain.setVisibility(View.VISIBLE);
                shrink_sign.setImageDrawable(getDrawable(R.drawable.down_arrow));
            } else {
                sign_domain.setVisibility(View.GONE);
                shrink_sign.setImageDrawable(getDrawable(R.drawable.right_arrows));
            }
        });

        initSampleManSign();
        initSampleManSignTow();
        initMonitor();
    }

    /**
     * 初始化监督人
     */
    private void initMonitor() {
        monitor_man_sign.setOnClickListener(view -> {
            Intent intent = new Intent(SamplingFormActivity.this, DrawActivity.class);
            intent.putExtra("type", MONITOR);
            startActivityForResult(intent, MONITOR);
        });
    }

    /**
     * 初始化二号采样人
     */
    private void initSampleManSignTow() {
        sample_man_sign_two.setOnClickListener(view -> {
            Intent intent = new Intent(SamplingFormActivity.this, DrawActivity.class);
            intent.putExtra("type", SAMPLEMANTWO);
            startActivityForResult(intent, SAMPLEMANONE);
        });
    }

    /**
     * 初始化一号采样人
     */
    private void initSampleManSign() {
        sample_man_sign.setOnClickListener(view -> {
            //跳转到绘图界面
            Intent intent = new Intent(SamplingFormActivity.this, DrawActivity.class);
            intent.putExtra("type", SAMPLEMANONE);
//            intent.putExtra("path",sampleManOnePath);
            startActivityForResult(intent, SAMPLEMANONE);
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
                    switch (type) {
                        case "1":
                            environmentPhotos.remove(path);
//                            environmentAdapter.setPhotos(environmentPhotos);
                            runOnUiThread(() -> environmentAdapter.notifyDataSetChanged());
                            environmentPictureNum--;
                            break;
                        case "2":
                            //采样照片
                            samplingPhotos.remove(path);
//                            samplingAdapter.setPhotos(samplingPhotos);
                            runOnUiThread(() -> samplingAdapter.notifyDataSetChanged());
                            samplingPictureNum--;
                            break;
                        case "3":
                            //样品照片
                            samplePhotos.remove(path);
//                            sampleAdapter.setPhotos(samplePhotos);
                            runOnUiThread(() -> sampleAdapter.notifyDataSetChanged());
                            samplePictureNum--;
                            break;
                    }
                }
                break;
            case SAMPLEMANONE:
                if (data != null) {
                    sampleManOnePath = data.getStringExtra("path");
                    File file = new File(sampleManOnePath);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(sampleManOnePath);
                        sample_man_sign.setImageBitmap(BaseUtil.rotateBitmap(bm, 90f));
                        bm.recycle();
                        sampleManOneNum = 1;
                    }
                }
                break;
            case SAMPLEMANTWO:
                if (data != null) {
                    sampleManTwoPath = data.getStringExtra("path");
                    File file = new File(sampleManTwoPath);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(sampleManTwoPath);
                        sample_man_sign_two.setImageBitmap(BaseUtil.rotateBitmap(bm, 90f));
                        bm.recycle();
                        sampleManTwoNum = 1;
                    }
                }
                break;
            case MONITOR:
                if (data != null) {
                    monitorManSignPath = data.getStringExtra("path");
                    File file = new File(monitorManSignPath);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile(monitorManSignPath);
                        monitor_man_sign.setImageBitmap(BaseUtil.rotateBitmap(bm, 90f));
                        bm.recycle();
                        monitorManSignNum = 1;
                    }
                }
                break;

            case VideoActivity
                    .VIDEO_ACTIVITY:
                //删除的回调
                if (data != null) {
                    String path = data.getStringExtra("path");
                    File file = new File(path);
                    if (file.exists()) {
                        sampleVideo.remove(path);
                        videoAdapter.setPaths(sampleVideo);
                        videoAdapter.notifyDataSetChanged();
                        videoNum--;
                    }
                }
                break;

            case METHOD:
                //获取方法信息
//                samplingMethod
                if (data != null && data.getParcelableExtra("Method") != null) {
                    samplingMethod = data.getParcelableExtra("Method");
                    showToast("选择的方法为:" + samplingMethod.getSampMethodName());
                    sampling_method.setText(samplingMethod.getSampMethodName());
                }
                break;
        }
        switch (requestCode) {
            case ENVIRONMENT:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    String newPath = getCacheDir().getPath() + "/enviroment" + environmentPictureNum + ".png";
                    if (!BaseUtil.copyFile(picturePath, newPath)) {
                        showToast("请检查文件访问权限");
                        break;
                    }

                    environmentPhotos.add(newPath);
//                    environmentAdapter.setPhotos(environmentPhotos);
                    runOnUiThread(() -> environmentAdapter.notifyDataSetChanged());
                    environmentPictureNum++;
                }
                break;
            case SAMPLING:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    String newPath = getCacheDir().getPath() + "/sampling" + samplingPictureNum + ".png";
                    if (!BaseUtil.copyFile(picturePath, newPath)) {
                        showToast("请检查文件访问权限");
                        break;
                    }
                    samplingPhotos.add(newPath);
//                    samplingAdapter.setPhotos(samplingPhotos);
                    runOnUiThread(() -> {
                        samplingAdapter.notifyDataSetChanged();
                    });
                    samplingPictureNum++;
                }
                break;
            case SAMPLE:
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    String newPath = getCacheDir().getPath() + "/sample" + samplePictureNum + ".png";
                    if (!BaseUtil.copyFile(picturePath, newPath)) {
                        showToast("请检查文件访问权限");
                        break;
                    }
                    samplePhotos.add(newPath);
//                    sampleAdapter.setPhotos(samplePhotos);
                    runOnUiThread(() -> {
                        sampleAdapter.notifyDataSetChanged();
                    });
                    samplePictureNum++;
                }
                break;

            case REQUEST_CODE_PICK:
                if (resultCode == RESULT_OK && data != null) {
                    String videoPath = GetPathFromUri.getPath(this, data.getData());
                    if (FileUtil.getFileOrFilesSize(videoPath, FileUtil.SIZETYPE_MB) >= 120) {
                        showToast("您的文件太大了!");
                        return;
                    }
                    sampleVideo.add(videoPath);
                    videoAdapter.setPaths(sampleVideo);
                    videoAdapter.notifyDataSetChanged();
                    videoNum++;
                }
                break;

            case REQUEST_CODE_SHOOT:
                // 图片、视频、音频选择结果回调
                List<LocalMedia> selectList = com.luck.picture.lib.PictureSelector.obtainMultipleResult(data);
                if (selectList.size() <= 0)
                    break;
                LocalMedia localMedia = selectList.get(0);
//                if (FileUtil.getFileOrFilesSize(videoPath, FileUtil.SIZETYPE_MB) >= 85) {
//                    showToast("您的文件太大了!");
//                    return;
//                }
                String videoPath = localMedia.getPath();
                //todo:获得路径之后进行一轮压缩
                String desPath = getCacheDir().getPath() + "/video" + videoNum + ".mp4";
                VideoCompress.compressVideoLow(videoPath, desPath, new VideoCompress.CompressListener() {
                    @Override
                    public void onStart() {
                        //Start Compress
                        showCompressDialog();
                    }

                    @Override
                    public void onSuccess() {
                        //Finish successfully
                        sampleVideo.add(desPath);
                        videoAdapter.setPaths(sampleVideo);
                        videoAdapter.notifyDataSetChanged();
                        videoNum++;
                        dismissCompressDialog();
                    }

                    @Override
                    public void onFail() {
                        //Failed
                        showToast("文件压缩出现错误");
                    }

                    @Override
                    public void onProgress(float percent) {
                        //Progress
                    }
                });
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
        if (nowLocation != null) {
            place.setText(nowLocation + "\n 距离为:" + BaseUtil.FomatNumber(Double.parseDouble(this.data.getDistance())));
        } else {
            presenter.beginLocation(this, this);
        }
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
        PictureFileUtils.deleteCacheDirFile(SamplingFormActivity.this);
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
        place.setText("N" + BaseUtil.FomatNumber(latitude) + "; E" + BaseUtil.FomatNumber(longitude));
        nowLocation = "N" + BaseUtil.FomatNumber(latitude) + "; E" + BaseUtil.FomatNumber(longitude);
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


    @Override
    public void onBackPressed() {
        doBack();
    }

    /**
     * 退出操作
     */
    private void doBack() {
        if (isSaved) {
            finish();
            return;
        }
        if (!canGoback) {
            showToast("文件还未完全下载，请勿退出");
            return;
        }
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(SamplingFormActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("如果未保存，数据将会丢失");
        normalDialog.setPositiveButton("退出",
                (dialog, which) -> finish());
        normalDialog.setNegativeButton("取消",
                (dialog, which) -> dialog.dismiss());
        // 显示
        normalDialog.show();
    }

    /**
     * 文件上传回调接口
     *
     * @param isOk
     */
    @SuppressWarnings("NonAtomicOperationOnVolatileField")
    @Override
    public void onSuccess(boolean isOk, String path, int type, String fileName) {
        if (isOk) {
            switch (type) {
                case FormPresenter.ENVIRONMENT:
                    environmentPictureNum--;
                    break;
                case FormPresenter.SAMPLING:
                    samplingPictureNum--;
                    break;
                case FormPresenter.SAMPLE:
                    samplePictureNum--;
                    break;
                case FormPresenter.VIDEO:
                    videoNum--;
                    break;
                case FormPresenter.SAMPLEMAN:
                    if (sampleManOneNum != 0) {
                        sampleManOneNum--;
                        break;
                    } else {
                        sampleManTwoNum--;
                    }
                    break;
                case FormPresenter.MONITOR:
                    monitorManSignNum--;
                    break;
            }
            saveAsAFile(fileName, type, path);
            Log.e("zzh", "图片地址为:" + path + " 图片种类为:" + type);
            if (environmentPictureNum <= 0
                    && samplingPictureNum <= 0
                    && samplePictureNum <= 0
                    && videoNum <= 0
                    && sampleManOneNum <= 0
                    && sampleManTwoNum <= 0
                    && monitorManSignNum <= 0) {
                //如果所有的图片都上传了，那么开始生成表单逻辑并上传表单
                showToast("图片上传完毕");
                runOnUiThread(() -> pictureUploadDialog.dismiss());
                saveAllData(isSubmit);
            }
        } else {
            showToast("发生错误");
            runOnUiThread(() -> pictureUploadDialog.dismiss());
        }
    }

    private void saveAsAFile(String name, int type, String path) {
        FileData data = new FileData();
        data.setFileName(name);
        data.setFilePath(path);
        data.setFileType(String.valueOf(type));
        files.add(data);
    }

    @Override
    public void onFail(String msg) {
        runOnUiThread(() -> pictureUploadDialog.dismiss());
        showToast(msg);
    }


    /**
     * 显示加载信息
     *
     * @param i 1:图片上传 2:表单上传
     */
    private void showDialog(String i) {
        switch (i) {
            case "1":
                if (pictureUploadDialog == null) {
                    View layout = getLayoutInflater().inflate(R.layout.dialog_picture_upload, null);
                    pictureUploadDialog = new Dialog(this);
                    pictureUploadDialog.setContentView(layout);
                    pictureUploadDialog.setCancelable(false);
                    pictureUploadDialog.setCanceledOnTouchOutside(true);
                    pictureUploadDialog.setOnCancelListener(dialog -> showToast("后台将继续上传信息"));
                }
                pictureUploadDialog.show();
                break;
            case "2":
                if (formUploadDialog == null) {
                    View layout = getLayoutInflater().inflate(R.layout.dialog_form_upload
                            , null);
                    formUploadDialog = new Dialog(this);
                    formUploadDialog.setContentView(layout);
                    formUploadDialog.setCancelable(false);
                    formUploadDialog.setCanceledOnTouchOutside(true);
                    formUploadDialog.setOnCancelListener(dialog -> showToast("后台将继续上传信息"));
                }
                formUploadDialog.show();
                break;
        }
    }

    /**
     * 表单保存回调接口
     */
    @Override
    public void onSavedorSubmit() {
        showToast("表单上传成功");
        formUploadDialog.dismiss();
        finish();
    }

    @Override
    public void onFailSaveOrSubmit(String msg) {
        showToast(msg);
        formUploadDialog.dismiss();
    }

    /**
     * 获取以保存的表单信息
     *
     * @param data
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onGetPrevious(FormDetailSubmitData data) {
        FormDetailData detailData = data.getDetail();

        projectId = detailData.getProjectId();

        this.data.setProjectPointId(detailData.getProjectPointId());
        this.data.setPointSampPlan(detailData.getPointSampPlan());
        this.data.setId(detailData.getId());
        this.data.setDistance(detailData.getDistance());
        this.data.setActLatitude(Double.parseDouble(detailData.getActLatitude()));
        this.data.setActLongitude(Double.parseDouble(detailData.getActLongitude()));
        this.data.setSampMethod(detailData.getSampMethod());
        this.data.setProjectPointId(detailData.getProjectPointId());
        this.data.setSampDesc(detailData.getSampDesc());


        samp_desc.setText(this.data.getSampDesc());
        person_name.setText(detailData.getActSamper());
        point_name.setText(detailData.getPointName());
        place.setText("N" + BaseUtil
                .FomatNumber(Double.parseDouble(detailData.getActLatitude()))
                + "; E" + BaseUtil.FomatNumber(Double.parseDouble(detailData.getActLongitude()))
                + "\n距离为:" + BaseUtil.FomatNumber(Double.parseDouble(detailData.getDistance())));
        Climate.setText(detailData.getGTempature().substring(0, detailData.getGTempature().length() - 1));
        Pressure.setText(detailData.getPressure().substring(0, detailData.getPressure().length() - 3));
        Weather.setText(detailData.getWeather());
        sampling_method.setText(detailData.getMethodName());
        if (detailData.getStatus().equals("0")) {
            sampling_status.setText("正常");
        } else {
            get_point_status.setVisibility(View.GONE);
            sampling_status.setText("异常");
        }
        transparent_way.setText(detailData.getTransMethod());
        sampling_time_text.setText(detailData.getActSampTime());
        time = detailData.getActSampTime();
        company_info.setText(detailData.getUserUnit());
        pointData = new PointDetailData();
        pointData.setProjectPointId(detailData.getProjectPointId());
//        injectFiles(data.getFiles());

        new Thread() {
            @Override
            public void run() {
                injectFiles(data.getFiles());
            }
        }.start();

    }

    /**
     * 文件注入
     *
     * @param files
     */
    private void injectFiles(List<FileDetailData> files) {
        String Ip = BaseUtil.removeLastChar(InternetUtil.SERVER_IP);
        int i = 0;
        for (FileDetailData detailData : files) {
            File file = BaseUtil.saveUrlAs(BaseUtil.to_Chanese(Ip + detailData.getFilePath())
                    , Objects.requireNonNull(this.getExternalCacheDir()).getAbsolutePath()
                    , "GET"
                    , detailData.getFileName());
//            File file=BaseUtil.downloadPicture(Ip + detailData.getFilePath()
//                    ,Objects.requireNonNull(this.getExternalCacheDir()).getAbsolutePath()+"/"
//                    ,detailData.getFileName());
            switch (detailData.getFileType()) {
                case "0":
                    environmentPhotos.add(file.getPath());
//                    environmentAdapter.setPhotos(environmentPhotos);
                    handler.post(() -> environmentAdapter.notifyDataSetChanged());
                    break;
                case "1":
                    samplingPhotos.add(file.getPath());
//                    samplingAdapter.setPhotos(samplingPhotos);
                    handler.post(() -> samplingAdapter.notifyDataSetChanged());
                    break;
                case "2":
                    samplePhotos.add(file.getPath());
//                    sampleAdapter.setPhotos(samplePhotos);
                    handler.post(() -> sampleAdapter.notifyDataSetChanged());
                    break;
                case "3":
                    //采样视频
                    sampleVideo.add(file.getPath());
                    videoAdapter.setPaths(sampleVideo);
                    handler.post(() -> videoAdapter.notifyDataSetChanged());
                    break;
                case "4":
                    if (sampleManOnePath == null) {
                        sampleManOnePath = file.getPath();
                        runOnUiThread(() -> {
                            if (SamplingFormActivity.this.isDestroyed() || SamplingFormActivity.this.isFinishing())
                                return;
                            Glide.with(SamplingFormActivity.this)
                                    .load(sampleManOnePath)
                                    .into(sample_man_sign);
                        });
                        sampleManOneNum++;
                        break;
                    }
                    if (sampleManTwoPath == null) {
                        sampleManTwoPath = file.getPath();
                        runOnUiThread(() -> {
                            if (SamplingFormActivity.this.isDestroyed() || SamplingFormActivity.this.isFinishing())
                                return;
                            Glide.with(SamplingFormActivity.this)
                                    .load(sampleManTwoPath)
                                    .into(sample_man_sign_two);
                        });
                        sampleManTwoNum++;
                        break;
                    }
                    break;
                case "5":
                    monitorManSignPath = file.getPath();
                    runOnUiThread(() -> {
                        if (SamplingFormActivity.this.isDestroyed() || SamplingFormActivity.this.isFinishing())
                            return;
                        Glide.with(SamplingFormActivity.this)
                                .load(monitorManSignPath)
                                .into(monitor_man_sign);
                    });
                    monitorManSignNum++;
                    break;
            }
            i++;
        }
        canGoback = true;
        handler.post(() -> dismissLoadingDialog());
    }

    @Override
    public void onFailGetPrevious(String msg) {
        showToast(msg);
    }
}
