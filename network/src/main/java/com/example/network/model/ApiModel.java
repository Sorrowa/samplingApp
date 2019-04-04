package com.example.network.model;

import com.example.core.Entity.message.LoginMessage;
import com.example.core.Entity.message.PointListMessage;
import com.example.core.Entity.message.PointRecyListMessage;
import com.example.core.Entity.message.ProjectMessage;
import com.example.network.InternetUtil;
import com.example.network.RetrofitHelper;
import com.example.network.RetrofitInterface.ApiGetPointInterface;
import com.example.network.RetrofitInterface.ApiGetPointListInterface;
import com.example.network.RetrofitInterface.ApiGetProjectInterface;
import com.example.network.RetrofitInterface.ApiLoginInterface;

import okhttp3.ResponseBody;
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
    private static Call<ProjectMessage> getProject(String type
            , String keyWord
            ,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
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
    public static Call<ProjectMessage> getUndoProject(String keyWord,String Token){
        return getProject(InternetUtil.UNDOPROJECTTYPE,keyWord,Token);
    }

    /**
     * 获取已采样数据
     * @param keyWord 搜索关键字
     * @return
     */
    public static Call<ProjectMessage> getHaveDoneProject(String keyWord,String Token){
        return getProject(InternetUtil.HAVEDONEPROJECTTYPE,keyWord,Token);
    }

    /**
     * 获取采样点位数据
     * todo:改改改改改！！！！
     * @return
     */
    public static Call<PointListMessage> getPointList(String type,String projectId,String keyWord,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetPointInterface apiGetPointInterface=retrofit.create(ApiGetPointInterface.class);
        return apiGetPointInterface.getPointList(type,projectId,keyWord);
    }

    /**
     * 获取对应项目点位信息
     * @param type
     * @param projectId
     * @param Token
     * @return
     */
    public static Call<PointRecyListMessage> getProjectPointList(String type, String projectId, String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetPointListInterface apiGetPointListInterface=retrofit.create(ApiGetPointListInterface.class);
        return apiGetPointListInterface.getPointList(type,projectId);
    }
}
