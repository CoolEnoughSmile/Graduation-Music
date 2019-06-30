package com.ge.music.http;

import com.ge.music.http.model.User;
import com.ge.music.model.MusicModel;
import com.ge.music.model.VideoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GeMusicServerApi {

    @FormUrlEncoded
    @POST("user/loginWithPhoneAndPassword")
    Call<GeMusicResponse<User>> loginWithPhoneAndPassword(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/loginWithVCode ")
    Call<GeMusicResponse<User>> loginWithVCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/register ")
    Call<GeMusicResponse<User>> register(@Field("phone") String phone, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/resetPassword ")
    Call<GeMusicResponse<User>> resetPassword(@Field("phone") String phone, @Field("password") String password);

    @GET("music/getMusicList")
    Call<GeMusicResponse<List<MusicModel>>> getMusicList(@Query("pageNum") int PageNum,@Query("pageSize") int pageSize);

    @GET("video/getVideoList ")
    Call<GeMusicResponse<List<VideoModel>>> getVideoList(@Query("pageNum") int PageNum, @Query("pageSize") int pageSize);
}
