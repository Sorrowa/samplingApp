package com.example.samplingapp.Presenter.Form;

import com.example.core.Entity.Data.SamplingData;
import com.example.core.Entity.message.SamplingMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Activities.SamplingForm.ShowDataActivitys.SamplingStatusActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SamplingStatusPresenter extends BasePresenter<SamplingStatusActivity> {


    /**
     * 查询状态信息
     * @param projectId
     * @param app
     * @param listener
     */
    public void getStatus(String projectId, App app, StatusListener listener){
        Call<SamplingMessage> call= ApiModel.getSampByPoint(projectId, ShareUtil.getToken(app));
        call.enqueue(new Callback<SamplingMessage>() {
            @Override
            public void onResponse(Call<SamplingMessage> call, Response<SamplingMessage> response) {
                if (response.body() != null) {
                    List<SamplingData> data=response.body().getData();
                    listener.onSuccess(data);
                }else{
                    listener.onFail();
                }
            }

            @Override
            public void onFailure(Call<SamplingMessage> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    public interface StatusListener {
        void onSuccess(List<SamplingData> data);
        void onFail();
    }
}
