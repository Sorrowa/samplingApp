package com.example.samplingapp.Presenter.Form;


import com.example.core.Entity.Data.PointDetailData;
import com.example.core.Entity.message.PointRecyListMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BaseActivity;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 获取点位的presenter
 */
public class PointSelectPresenter extends BasePresenter<BaseActivity> {

    public void getProjectListInfo(String type, String projectId, onNetListener listener) {
        Call<PointRecyListMessage> call = ApiModel.getProjectPointList(type
                , projectId
                , ShareUtil.getToken((App) getView().getApplication()));
        if (call != null) {
            call.enqueue(new Callback<PointRecyListMessage>() {
                @Override
                public void onResponse(Call<PointRecyListMessage> call, Response<PointRecyListMessage> response) {
                    if (response.body() != null && response.body().getSuccess()) {
                        listener.onSuccess(response.body().getData(),true);
                    } else {
                        listener.onFail();
                    }
                }

                @Override
                public void onFailure(Call<PointRecyListMessage> call, Throwable t) {
                    listener.onFail();
                }
            });
        } else {
            listener.onFail();
        }
    }


    public interface onNetListener {
        void onSuccess(List<PointDetailData> datas, boolean isOk);

        void onFail();
    }
}
