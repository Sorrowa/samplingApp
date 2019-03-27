package com.example.network.model;

import com.example.core.Entity.Message;
import com.example.network.RetrofitHelper;
import com.example.network.RetrofitInterface.ApiLoginInterface;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 登陆网络行为
 */
public class ApiLoginModel {
    public static Call<Message> Login(String name, String password){
        Retrofit retrofit= RetrofitHelper.getServerRetrofit();
        ApiLoginInterface loginInterface=retrofit.create(ApiLoginInterface.class);
        return loginInterface.login(name,password);
    }
}
