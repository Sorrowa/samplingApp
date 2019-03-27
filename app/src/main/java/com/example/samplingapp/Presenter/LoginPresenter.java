package com.example.samplingapp.Presenter;

import com.example.core.Entity.Message;
import com.example.core.Entity.User;
import com.example.network.model.ApiLoginModel;
import com.example.samplingapp.Activities.LoginActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginActivity> {



    public void getLogin(String name,String password,LoginLisenter lisenter){
        Call<Message> call=ApiLoginModel.Login(name,password);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                Message msg=response.body();
                assert msg != null;
                if (msg.getSuccess()){
                    //todo:存储用户Token逻辑
                    App.user=new User(msg.getData().getAccount()
                            ,msg.getData().getOrgName());
                    ShareUtil.setToken((App) getView().getApplication()
                            ,msg.getData().getToken());
                }
                lisenter.onSuccess(msg.getMessage(),msg.getSuccess());
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                lisenter.onFail(call.toString());
            }
        });
    }



    public interface LoginLisenter{
        void onSuccess(String res, Boolean success);
        void onFail(String error);
    }
}
