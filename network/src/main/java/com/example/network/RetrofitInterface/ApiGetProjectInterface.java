package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.ProjectMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetProjectInterface {

    /**
     * 获取项目接口
     * @param type 1：查询带采样任务 2：查询已采样任务
     * @param keyWord 搜索关键字
     * @return 查询结果
     */
    @POST(InternetUtil.PROJECT)
    Call<ProjectMessage> getProject(@Query("type") String type
            , @Query("keyWord") String keyWord);
}
