package com.example.samplingapp.Presenter;

import com.example.core.Entity.Data.ProjectData;
import com.example.core.Entity.message.ProjectMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter extends BasePresenter<MainActivity> {

    public void getHaveDoneProject(String keyWord,ProjectListener listener){
        Call<ProjectMessage> call=ApiModel.getHaveDoneProject(keyWord);
        call.enqueue(new Callback<ProjectMessage>() {
            @Override
            public void onResponse(Call<ProjectMessage> call
                    , Response<ProjectMessage> response) {

                List<ProjectData> data=response.body().getData();
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(Call<ProjectMessage> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    public void getUndoProject(String keyWord,ProjectListener listener){
        Call<ProjectMessage> call=ApiModel.getUndoProject(keyWord);
        call.enqueue(new Callback<ProjectMessage>() {
            @Override
            public void onResponse(Call<ProjectMessage> call, Response<ProjectMessage> response) {
                List<ProjectData> data=response.body().getData();
                listener.onSuccess(data);
            }

            @Override
            public void onFailure(Call<ProjectMessage> call, Throwable t) {
                listener.onFail();
            }
        });
    }


    public interface ProjectListener{
        void onSuccess(List<ProjectData> data);
        void onFail();
    }
}
