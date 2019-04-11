package com.example.network.RetrofitInterface;

import com.example.core.Entity.message.FileMessage;
import com.example.network.InternetUtil;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiUploadFile {

    /**
     * 文件上传接口。回调信息中包含文件在服务器的路径
     * @param body
     * @return
     */
    @Multipart
    @POST(InternetUtil.UOLOADFILE)
    Call<FileMessage> uploadFile(@Part MultipartBody.Part body);
}
