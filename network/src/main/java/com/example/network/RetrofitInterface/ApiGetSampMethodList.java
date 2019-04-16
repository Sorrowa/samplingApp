package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.SampMethodListMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetSampMethodList {

    /**
     * 关键字查找采样方法
     * @param keyWord
     * @return
     */
    @POST(InternetUtil.GETMETHODLIST)
    Call<SampMethodListMessage> getMethodList(@Query("keyWord") String keyWord);
}
