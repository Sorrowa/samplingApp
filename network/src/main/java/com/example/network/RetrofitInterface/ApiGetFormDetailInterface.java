package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.FormDetailMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGetFormDetailInterface {


    /**
     * 获取表单详细信息
     * @param formId 表单的id
     * @return
     */
    @POST(InternetUtil.GETFORMDETAIL)
    Call<FormDetailMessage> getFormDetail(@Query("formId")String formId);
}
