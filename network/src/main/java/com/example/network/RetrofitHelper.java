package com.example.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile Retrofit ServerRetrofit;
    private static volatile Retrofit TokenRetrofit;
    private static Boolean isSetToken=true;

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
        if (TokenRetrofit==null && Token!=null){
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
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            TokenRetrofit=new Retrofit
                    .Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(InternetUtil.SERVER_IP)
                    .build();
            isSetToken=true;
        }
    }


    public static Retrofit getTokenRetrofit(String Token){
        if (!RetrofitHelper.isSetToken){
            return null;
        }
        setToken(Token);
        return TokenRetrofit;
    }
}
