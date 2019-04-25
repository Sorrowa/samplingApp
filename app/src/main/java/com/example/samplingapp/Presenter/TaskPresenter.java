package com.example.samplingapp.Presenter;

import com.example.core.Entity.Data.PointData;
import com.example.core.Entity.message.DeleteFormMessage;
import com.example.core.Entity.message.PointCountMessage;
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

    public void getPointList(String type, String projectId, String keyWord, listener listener) {
        Call<PointListMessage> call = ApiModel.getPointList(type
                , projectId
                , keyWord
                , ShareUtil.getToken((App) getView().getApplication()));
        assert call != null;
        call.enqueue(new Callback<PointListMessage>() {
            @Override
            public void onResponse(Call<PointListMessage> call, Response<PointListMessage> response) {
                if (response.body() != null && response.body().getSuccess()) {
                    List<PointData> data = response.body().getData();
                    listener.onSuccess(data, true);
                } else {
                    listener.onSuccess(null, false);
                }

            }

            @Override
            public void onFailure(Call<PointListMessage> call, Throwable t) {
                listener.onFail();
            }
        });
    }

    /**
     * 删除表单
     *
     * @param id
     * @param deleteListener
     */
    public void deleteForm(String id, deleteListener deleteListener) {
        Call<DeleteFormMessage> messageCall = ApiModel.deleteForm(id
                , ShareUtil.getToken((App) getView().getApplication()));
        assert messageCall != null;
        messageCall.enqueue(new Callback<DeleteFormMessage>() {
            @Override
            public void onResponse(Call<DeleteFormMessage> call, Response<DeleteFormMessage> response) {
                DeleteFormMessage message = response.body();
                if (message != null && message.getSuccess()) {
                    deleteListener.onSuccess();
                } else if (message != null) {
                    deleteListener.onFail(message.getMessage());
                } else {
                    deleteListener.onFail("发生未知错误");
                }
            }

            @Override
            public void onFailure(Call<DeleteFormMessage> call, Throwable t) {
                deleteListener.onFail("请检查网络");
            }
        });
    }

    /**
     * 获取点位采集数据
     * @param projectId
     * @param listener
     */
    public void getPointCount(String projectId, countListener listener) {
        Call<PointCountMessage> call = ApiModel.getPointCount(projectId
                , ShareUtil.getToken((App) getView().getApplication()));
        assert call != null;
        call.enqueue(new Callback<PointCountMessage>() {
            @Override
            public void onResponse(Call<PointCountMessage> call, Response<PointCountMessage> response) {
                PointCountMessage message = response.body();
                if (message != null && message.getSuccess()) {
                    listener.onSuccess(message.getData());
                } else if (message != null) {
                    listener.onFailCount(message.getMessage());
                } else {
                    listener.onFailCount("发生未知错误");
                }
            }

            @Override
            public void onFailure(Call<PointCountMessage> call, Throwable t) {
                listener.onFailCount("请检查网络");
            }
        });
    }


    public interface listener {
        void onSuccess(List<PointData> data, boolean isOk);

        void onFail();
    }

    public interface deleteListener {
        void onSuccess();

        void onFail(String info);
    }

    public interface countListener {
        void onSuccess(String count);

        void onFailCount(String info);
    }
}
