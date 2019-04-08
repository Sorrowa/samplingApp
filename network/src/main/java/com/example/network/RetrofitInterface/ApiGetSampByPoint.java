package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.SamplingMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetSampByPoint {

    /**
     * 根据项目点位ID获取样品信息
     * @param projectPointId 点位ID
     * @return 样品信息
     */
    @POST(InternetUtil.SAMPLINGSTATUSLIST)
    Call<SamplingMessage> getSampByPoint(@Query("projectPointId") String projectPointId);
}
