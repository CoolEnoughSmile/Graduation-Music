package com.ge.music.http;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpHelper {

    private static GeMusicServerApi geMusicServerApi;
    private static LrcApi lrcApi;

    private HttpHelper() {
    }

    private static void buildGeMusicServerApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        geMusicServerApi = new Retrofit.Builder().baseUrl("http://106.13.145.146:8081/ge-music/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeMusicServerApi.class);
    }

    private static void buildLrcApi(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        lrcApi = new Retrofit.Builder().baseUrl("http://music.163.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LrcApi.class);
    }

    public static GeMusicServerApi getGeMusicServerApi() {
        if (geMusicServerApi == null) {
            synchronized (HttpHelper.class) {
                if (geMusicServerApi == null) {
                    buildGeMusicServerApi();
                }
            }
        }
        return geMusicServerApi;
    }

    public static LrcApi getLrcApi() {
        if (lrcApi == null) {
            synchronized (HttpHelper.class) {
                if (lrcApi == null){
                    buildLrcApi();
                }
            }
        }
        return lrcApi;
    }
}
