package com.example.samplingapp.Presenter;

import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.message.PointListMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Activities.TaskDetailActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskPresenter extends BasePresenter<TaskDetailActivity> {

    public void getPointList(String type , String projectId,String keyWord,listener listener){
        Call<PointListMessage> call=ApiModel.getPointList(type
                ,projectId
                ,keyWord
                , ShareUtil.getToken((App) getView().getApplication()));
        call.enqueue(new Callback<PointListMessage>() {
            @Override
            public void onResponse(Call<PointListMessage> call, Response<PointListMessage> response) {
                if (response.body() != null&&response.body().getSuccess()){
                    List<PointData> data=response.body().getData();
                    listener.onSuccess(data,true);
                }else {
                    listener.onSuccess(null,false);
                }

            }

            @Override
            public void onFailure(Call<PointListMessage> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    public interface listener{
        void onSuccess(List<PointData> data,boolean isOk);
        void onFail();
    }
}
