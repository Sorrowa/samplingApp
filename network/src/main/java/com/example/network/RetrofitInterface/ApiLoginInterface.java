package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.LoginMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiLoginInterface {

    /**
     * 登录接口
     * @param name 用户名
     * @param password 密码
     * @return 登录回调内容
     */
    @POST(InternetUtil.LOGIN)
    Call<LoginMessage> login(@Query("loginId") String name, @Query("pwd") String password);
}
