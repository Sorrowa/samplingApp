package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.PointListMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetPointInterface {

    /**
     * 获取当前项目下所有的
     * @param
     * @return
     */
    @POST(InternetUtil.POINTLIST)
    Call<PointListMessage> getPointList(@Query("type")String type
            ,@Query("projectId")String projectId,@Query("keyWord")String keyWord);
}
