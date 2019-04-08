package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.PointSelectListMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiPointList {

    /**
     * 获取点位列表
     * @param projectId
     * @return
     */
    @POST(InternetUtil.GETPOINTLIST)
    Call<PointSelectListMessage> getPointList(@Query("projectId")String projectId);
}
