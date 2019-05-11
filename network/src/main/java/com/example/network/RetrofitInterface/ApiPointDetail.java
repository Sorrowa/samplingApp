package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.PointDetailMessage;
import com.example.core.Entity.message.PointSelectListMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiPointDetail {
    /**
     * 获取点位列表
     * @param pointId
     * @return
     */
    @POST(InternetUtil.getPointDetail)
    Call<PointDetailMessage> getPointDetail(@Query("pointId")String pointId);
}
