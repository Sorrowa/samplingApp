package com.example.network.RetrofitInterface;

import com.example.core.Entity.Data.FormSubmitData;
import com.example.core.Entity.message.SaveOrSubmitFormMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiFormInterface {

    /**
     * 将表单内容和文件信息一并上传
     * @param body
     * @return
     */
    @POST(InternetUtil.SAVEFORM)
    Call<SaveOrSubmitFormMessage> SaveForm(@Body FormSubmitData body);
}
