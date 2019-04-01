package com.example.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile Retrofit ServerRetrofit;
    private static volatile Retrofit TokenRetrofit;
    private static Boolean isSetToken=false;

    public static Retrofit getServerRetrofit(){
        if (ServerRetrofit==null){
            ServerRetrofit=new Retrofit
                    .Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(InternetUtil.SERVER_IP)
                    .build();
        }

        return ServerRetrofit;
    }

    /**
     * 登录之后设置Token
     * @param Token
     */
    public static void setToken(final String Token){
        if (ServerRetrofit==null && Token!=null){
            OkHttpClient.Builder builder=new OkHttpClient.Builder();
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request=chain.request();
                    Request request1=request.newBuilder()
                            .header("token",Token)
                            .build();
                    return chain.proceed(request1);
                }
            });
            ServerRetrofit=new Retrofit
                    .Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(InternetUtil.SERVER_IP)
                    .build();
            isSetToken=true;
        }
    }


    public static Retrofit getTokenRetrofit(){
        if (!RetrofitHelper.isSetToken){
            return null;
        }
        return TokenRetrofit;
    }
}
