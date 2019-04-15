package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.DeleteFormMessage;
import com.example.network.InternetUtil;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiDeleteFormInterface {

    /**
     * 删除表单
     * @param formId 删除的表单的ID
     * @return
     */
    @POST(InternetUtil.DELETEFORM)
    Call<DeleteFormMessage> deleteForm(@Query("formId")String formId);
}
