package com.example.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static volatile Retrofit ServerRetrofit;

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
}
