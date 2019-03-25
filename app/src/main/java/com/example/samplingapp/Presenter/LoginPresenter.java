package com.example.samplingapp.Presenter;

import com.example.samplingapp.Activities.LoginActivity;
import com.example.samplingapp.Base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginActivity> {



    public void getLogin(LoginLisenter lisenter){

    }



    public interface LoginLisenter{
        void onSuccess(String res);
        void onFail(String error);
    }
}
