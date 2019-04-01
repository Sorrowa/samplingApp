package com.example.network.model;

import com.example.core.Entity.message.LoginMessage;
import com.example.core.Entity.message.ProjectMessage;
import com.example.network.InternetUtil;
import com.example.network.RetrofitHelper;
import com.example.network.RetrofitInterface.ApiGetProjectInterface;
import com.example.network.RetrofitInterface.ApiLoginInterface;

import retrofit2.Call;
import retrofit2.Retrofit;


public class ApiModel {
    /**
     * 登陆
     */
    public static Call<LoginMessage> Login(String name, String password){
        Retrofit retrofit= RetrofitHelper.getServerRetrofit();
        ApiLoginInterface loginInterface=retrofit.create(ApiLoginInterface.class);
        return loginInterface.login(name,password);
    }


    /**
     * 获采样数据
     * @param type 1 待采样 2 已采样
     * @param keyWord 搜索关键字
     * @return 回调数据
     */
    private static Call<ProjectMessage> getProject(String type, String keyWord){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit();
        if (retrofit==null){
            return null;
        }
        ApiGetProjectInterface apiGetProjectInterface=retrofit
                .create(ApiGetProjectInterface.class);
        return apiGetProjectInterface.getProject(type,keyWord);
    }

    /**
     * 获取待采样数据
     * @param keyWord 搜索关键字
     * @return
     */
    public static Call<ProjectMessage> getUndoProject(String keyWord){
        return getProject(InternetUtil.UNDOPROJECTTYPE,keyWord);
    }

    /**
     * 获取已采样数据
     * @param keyWord 搜索关键字
     * @return
     */
    public static Call<ProjectMessage> getHaveDoneProject(String keyWord){
        return getProject(InternetUtil.HAVEDONEPROJECTTYPE,keyWord);
    }
}
