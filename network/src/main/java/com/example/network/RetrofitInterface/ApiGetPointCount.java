package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.PointCountMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetPointCount {

    /**
     * 获取表单详细信息
     * @param projectId 任务Id
     * @return
     */
    @POST(InternetUtil.GETPOINTCOUNT)
    Call<PointCountMessage> getPointCount(@Query("projectId")String projectId);
}
