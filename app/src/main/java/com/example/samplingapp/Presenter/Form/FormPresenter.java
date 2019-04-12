package com.example.samplingapp.Presenter.Form;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.core.Entity.Data.FileData;
import com.example.core.Entity.Data.FormData;
import com.example.core.Entity.Data.FormSubmitData;
import com.example.core.Entity.message.FileMessage;
import com.example.core.Entity.message.SaveOrSubmitFormMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Activities.SamplingForm.SamplingFormActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.Location.Location;
import com.example.samplingapp.utils.ShareUtil;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPresenter extends BasePresenter<SamplingFormActivity> {

//    files参数下的FileType 说明：
//    环境照片=0,
//    采样照片=1,
//    样品照片=2,
//    采样视频=3,
//    采样人签字=4,
//    监督人签字=5

    public static final int ENVIRONMENT = 0;
    public static final int SAMPLING = 1;
    public static final int SAMPLE = 2;
    public static final int VIDEO = 3;
    public static final int SAMPLEMAN = 4;
    public static final int MONITOR = 5;

    public void beginLocation(Context context, LocationListener listener) {
        MyLocationListener myListener = new MyLocationListener(listener);
        Location.beginToGetNowLocation(context, myListener);
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
                    listener.onSuccess(bdLocation.getLatitude(), bdLocation.getLongitude());
                    break;
                case 62:
                    listener.onError("定位失败，请检查运营商网络或者WiFi网络是否正常开启");
                    break;
                case 63:
                    listener.onError("网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅");
                    break;
                case 66:
                    listener.onSuccess(bdLocation.getLatitude(), bdLocation.getLongitude());
                    break;
                case 67:
                    listener.onError("离线定位失败");
                    break;
                case 161:
                    listener.onSuccess(bdLocation.getLatitude(), bdLocation.getLongitude());
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

    /**
     * 上传文件接口
     *
     * @param path
     * @param listener
     */
    public void uploadFile(String path, FileUploadListener listener, int type) {
        File file = new File(path);
        if (!file.exists()) {
            listener.onFail("文件不存在");
        }
        Call<FileMessage> call = ApiModel
                .uploadFile(ShareUtil.getToken((App) (getView().getApplication())), file);
        if (call != null) {
            call.enqueue(new Callback<FileMessage>() {
                @Override
                public void onResponse(Call<FileMessage> call, Response<FileMessage> response) {
                    FileMessage message = response.body();
                    if (message != null && message.getSuccess()) {
                        listener.onSuccess(true, message.getData(), type, file.getName());
                    } else if (message != null) {
                        listener.onFail(message.getMessage());
                    } else {
                        listener.onFail("文件过大，上传失败");
                    }
                }

                @Override
                public void onFailure(Call<FileMessage> call, Throwable t) {
                    listener.onFail("文件上传失败，请检查网络");
                }
            });
        } else {
            listener.onFail("发生未知错误");
        }
    }


    /**
     * 表单保存接口
     * @param formData
     * @param files
     * @param listener
     * @param isSubmit
     */
    public void saveForm(FormData formData
            , List<FileData> files
            , SaveOrSubmitListener listener
            ,boolean isSubmit) {
        FormSubmitData data=new FormSubmitData();
        data.setFiles(files);
        data.setForm(formData);
        data.setSubmit(isSubmit);
        Call<SaveOrSubmitFormMessage> call=ApiModel.saveForm(ShareUtil.getToken((App) (getView().getApplication()))
                ,data);
        assert call != null;
        call.enqueue(new Callback<SaveOrSubmitFormMessage>() {
            @Override
            public void onResponse(Call<SaveOrSubmitFormMessage> call, Response<SaveOrSubmitFormMessage> response) {
                SaveOrSubmitFormMessage message=response.body();
                if (message!=null && message.getSuccess()){
                    listener.onSavedorSubmit();
                }else if (message!=null && !message.getSuccess()){
                    listener.onFailSaveOrSubmit(message.getMessage());
                }else{
                    listener.onFailSaveOrSubmit("发生未知错误!");
                }
            }

            @Override
            public void onFailure(Call<SaveOrSubmitFormMessage> call, Throwable t) {
                listener.onFailSaveOrSubmit("上传成功");
            }
        });
    }

    public interface FileUploadListener {
        void onSuccess(boolean isOk, String path, int type, String name);

        void onFail(String msg);
    }

    public interface LocationListener {
        /**
         * 经纬度信息
         *
         * @param latitude  纬度
         * @param longitude 经度
         */
        void onSuccess(double latitude, double longitude);

        /**
         * 错误信息
         *
         * @param msg
         */
        void onError(String msg);
    }

    public interface SaveOrSubmitListener {
        void onSavedorSubmit();

        void onFailSaveOrSubmit(String msg);
    }
}
