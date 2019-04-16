package com.example.samplingapp.Presenter.Form;

import com.example.core.Entity.Data.SampMethodData;
import com.example.core.Entity.message.SampMethodListMessage;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Activities.SamplingForm.SelectActivitys.MethodSelectActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 采样方法选择
 */
public class MethodSelectPresenter extends BasePresenter<MethodSelectActivity> {

    /**
     * 获取方法列表
     * @param keyword
     */
    public void getMethod(String keyword,MethodListener listener){
        Call<SampMethodListMessage> call=ApiModel
                .getMethod(keyword
                        , ShareUtil.getToken((App) (getView().getApplication())));
        assert call!=null;
        call.enqueue(new Callback<SampMethodListMessage>() {
            @Override
            public void onResponse(Call<SampMethodListMessage> call, Response<SampMethodListMessage> response) {
                SampMethodListMessage methodListMessage=response.body();
                if (methodListMessage==null){
                    listener.onFail("发生未知错误");
                    return;
                }
                if (methodListMessage.getSuccess()){
                    listener.onSuccess(methodListMessage.getData());
                }else{
                    listener.onFail(methodListMessage.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SampMethodListMessage> call, Throwable t) {
                listener.onFail("请检查网络");
            }
        });
    }


    public interface MethodListener{
        void onSuccess(List<SampMethodData> dataList);
        void onFail(String msg);
    }
}
