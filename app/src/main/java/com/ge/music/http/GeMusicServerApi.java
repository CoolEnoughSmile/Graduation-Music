package com.ge.music.http;

import com.ge.music.http.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GeMusicServerApi {

    @FormUrlEncoded
    @POST("user/loginWithPhoneAndPassword")
    Call<GeMusicResponse<User>> loginWithPhoneAndPassword(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/loginWithVCode ")
    Call<GeMusicResponse<User>> loginWithVCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/register ")
    Call<GeMusicResponse> register(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/resetPassword ")
    Call<GeMusicResponse> resetPassword(@Field("phone") String phone, @Field("password") String password);

}
