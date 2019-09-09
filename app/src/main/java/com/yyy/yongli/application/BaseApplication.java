package com.yyy.yongli.application;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private OkHttpClient okHttpClient;
    private static BaseApplication baseApplication;

    @Override
    public final void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(getApplicationContext());
        baseApplication = this;
    }

    /**
     * 返回应用程序实例
     */
    public static BaseApplication getInstance() {
        return baseApplication;
    }


    public OkHttpClient getClient() {
        if (okHttpClient == null)
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                    .build();
        return okHttpClient;
    }

    ;
}
