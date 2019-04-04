package com.example.network.RetrofitInterface;

//GetProjectPoint

import com.example.core.Entity.message.PointRecyListMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetPointListInterface {

    /**
     * 获取当前项目下所有的
     * @param
     * @return
     */
    @POST(InternetUtil.PROJECTPOINTLIST)
    Call<PointRecyListMessage> getPointList(@Query("type")String type
            , @Query("projectId")String projectId);
}
