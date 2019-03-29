package com.example.samplingapp.Presenter;

import com.example.core.Entity.message.LoginMessage;
import com.example.core.Entity.User;
import com.example.network.model.ApiModel;
import com.example.samplingapp.Activities.LoginActivity;
import com.example.samplingapp.Base.App;
import com.example.samplingapp.Base.BasePresenter;
import com.example.samplingapp.utils.ShareUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter extends BasePresenter<LoginActivity> {



    public void getLogin(String name,String password,LoginLisenter lisenter){
        Call<LoginMessage> call= ApiModel.Login(name,password);
        call.enqueue(new Callback<LoginMessage>() {
            @Override
            public void onResponse(Call<LoginMessage> call, Response<LoginMessage> response) {
                LoginMessage msg=response.body();
                assert msg != null;
                if (msg.getSuccess()){
                    App.user=new User(msg.getData().getAccount()
                            ,msg.getData().getOrgName());
                    ShareUtil.setToken((App) getView().getApplication()
                            ,msg.getData().getToken());
                }
                lisenter.onSuccess(msg.getMessage(),msg.getSuccess());
            }

            @Override
            public void onFailure(Call<LoginMessage> call, Throwable t) {
                lisenter.onFail(call.toString());
            }
        });
    }



    public interface LoginLisenter{
        void onSuccess(String res, Boolean success);
        void onFail(String error);
    }
}
