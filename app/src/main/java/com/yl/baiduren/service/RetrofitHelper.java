package com.yl.baiduren.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author 王锋 on 2017/11/9.
 */

public class RetrofitHelper {

    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    OkHttpClient okHttpClient = null;

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper(context);
                }
            }
        }
        return instance;
    }

    private RetrofitHelper(Context mContext) {
        Context mContext1 = mContext;


        mRetrofit = new Retrofit.Builder()
                .baseUrl(ServiceUrl.MAIN_PATH)//主网址
                .client(initOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }

    public OkHttpClient initOkHttpClient() {
        okHttpClient = new OkHttpClient.Builder()
                 /*
            这里可以添加一个HttpLoggingInterceptor，因为Retrofit封装好了从Http请求到解析，
            出了bug很难找出来问题，添加HttpLoggingInterceptor拦截器方便调试接口
             */
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.e("--&&--RetrofitHelper--", message);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(20, TimeUnit.SECONDS)//连接超时（单位/秒）
                .writeTimeout(20, TimeUnit.SECONDS)//写入超时
                .readTimeout(20, TimeUnit.SECONDS)//读取超时
                .build();
        return okHttpClient;
    }

    public OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            return null;
        }
        return okHttpClient;
    }
}
