package com.example.network.model;

import com.example.core.Entity.Data.FormSubmitData;
import com.example.core.Entity.message.DeleteFormMessage;
import com.example.core.Entity.message.FileMessage;
import com.example.core.Entity.message.FormDetailMessage;
import com.example.core.Entity.message.LoginMessage;
import com.example.core.Entity.message.PointCountMessage;
import com.example.core.Entity.message.PointListMessage;
import com.example.core.Entity.message.PointRecyListMessage;
import com.example.core.Entity.message.ProjectMessage;
import com.example.core.Entity.message.SampMethodListMessage;
import com.example.core.Entity.message.SamplingMessage;
import com.example.core.Entity.message.SaveOrSubmitFormMessage;
import com.example.network.InternetUtil;
import com.example.network.RetrofitHelper;
import com.example.network.RetrofitInterface.ApiDeleteFormInterface;
import com.example.network.RetrofitInterface.ApiFormInterface;
import com.example.network.RetrofitInterface.ApiGetFormDetailInterface;
import com.example.network.RetrofitInterface.ApiGetPointCount;
import com.example.network.RetrofitInterface.ApiGetPointInterface;
import com.example.network.RetrofitInterface.ApiGetPointListInterface;
import com.example.network.RetrofitInterface.ApiGetProjectInterface;
import com.example.network.RetrofitInterface.ApiGetSampByPoint;
import com.example.network.RetrofitInterface.ApiGetSampMethodList;
import com.example.network.RetrofitInterface.ApiLoginInterface;
import com.example.network.RetrofitInterface.ApiUploadFile;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class ApiModel {
    /**
     * 登陆
     */
    public static Call<LoginMessage> Login(String name, String password){
        Retrofit retrofit= RetrofitHelper.getServerRetrofit();
        ApiLoginInterface loginInterface=retrofit.create(ApiLoginInterface.class);
        return loginInterface.login(name,password);
    }


    /**
     * 获采样数据
     * @param type 1 待采样 2 已采样
     * @param keyWord 搜索关键字
     * @return 回调数据
     */
    private static Call<ProjectMessage> getProject(String type
            , String keyWord
            ,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetProjectInterface apiGetProjectInterface=retrofit
                .create(ApiGetProjectInterface.class);
        return apiGetProjectInterface.getProject(type,keyWord);
    }

    /**
     * 获取待采样数据
     * @param keyWord 搜索关键字
     * @return
     */
    public static Call<ProjectMessage> getUndoProject(String keyWord,String Token){
        return getProject(InternetUtil.UNDOPROJECTTYPE,keyWord,Token);
    }

    /**
     * 获取已采样数据
     * @param keyWord 搜索关键字
     * @return
     */
    public static Call<ProjectMessage> getHaveDoneProject(String keyWord,String Token){
        return getProject(InternetUtil.HAVEDONEPROJECTTYPE,keyWord,Token);
    }

    /**
     * 获取采样点位数据
     * todo:改改改改改！！！！
     * @return
     */
    public static Call<PointListMessage> getPointList(String type,String projectId,String keyWord,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetPointInterface apiGetPointInterface=retrofit.create(ApiGetPointInterface.class);
        return apiGetPointInterface.getPointList(type,projectId,keyWord);
    }

    /**
     * 获取对应项目点位信息（主要关于各种显示信息）
     * @param type
     * @param projectId
     * @param Token
     * @return
     */
    public static Call<PointRecyListMessage> getProjectPointList(String type, String projectId, String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetPointListInterface apiGetPointListInterface=retrofit.create(ApiGetPointListInterface.class);
        return apiGetPointListInterface.getPointList(type,projectId);
    }

    /**
     * 根据点位获取样品信息
     * @param projectPointId 点位ID
     * @param Token
     * @return
     */
    public static Call<SamplingMessage> getSampByPoint(String projectPointId,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetSampByPoint apiGetSampByPoint=retrofit.create(ApiGetSampByPoint.class);
        return apiGetSampByPoint.getSampByPoint(projectPointId);
    }

    /**
     * 保存表单信息
     * @param Token
     * @param data
     * @return
     */
    public static Call<SaveOrSubmitFormMessage> saveForm(String Token, FormSubmitData data){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiFormInterface apiFormInterface=retrofit.create(ApiFormInterface.class);
        return apiFormInterface.SaveForm(data);
    }

    /**
     * 上传文件
     * @param Token
     * @param file
     * @return
     */
    public static Call<FileMessage> uploadFile(String Token, File file){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiUploadFile apiUploadFile=retrofit.create(ApiUploadFile.class);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return apiUploadFile.uploadFile(body);
    }

    /**
     * 删除表单
     * @param formId
     * @param Token
     * @return
     */
    public static Call<DeleteFormMessage> deleteForm(String formId,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiDeleteFormInterface apiDeleteFormInterface=retrofit.create(ApiDeleteFormInterface.class);
        return apiDeleteFormInterface.deleteForm(formId);
    }

    /**
     * 获取方法列表
     * @param keyWord 查找关键字
     * @param Token
     * @return
     */
    public static Call<SampMethodListMessage> getMethod(String keyWord,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetSampMethodList apiGetSampMethodList=retrofit.create(ApiGetSampMethodList.class);
        return apiGetSampMethodList.getMethodList(keyWord);
    }

    /**
     * 获取表单详细信息
     * @param formId
     * @param Token
     * @return
     */
    public static Call<FormDetailMessage> getForm(String formId,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetFormDetailInterface apiGetFormDetailInterface=retrofit
                .create(ApiGetFormDetailInterface.class);
        return apiGetFormDetailInterface.getFormDetail(formId);
    }

    /**
     * 获取点位统计数据
     * @param projectId
     * @param Token
     * @return
     */
    public static Call<PointCountMessage> getPointCount(String projectId,String Token){
        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
        if (retrofit==null){
            return null;
        }
        ApiGetPointCount apiGetPointCount=retrofit.create(ApiGetPointCount.class);
        return apiGetPointCount.getPointCount(projectId);
    }

//    /**
//     * 获取点位信息（偏ID类ID）
//     * @param projectId
//     * @param Token
//     * @return
//     */
//    public static Call<PointSelectListMessage> getPointList(String projectId,String Token){
//        Retrofit retrofit=RetrofitHelper.getTokenRetrofit(Token);
//        if (retrofit==null){
//            return null;
//        }
//        ApiPointList apiPointList=retrofit.create(ApiPointList.class);
//        return apiPointList.getPointList(projectId);
//    }
}
