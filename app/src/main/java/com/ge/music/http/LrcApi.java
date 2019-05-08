package com.ge.music.http;

import com.ge.music.http.model.LrcModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LrcApi {

    @GET("api/song/media")
    Call<LrcModel> getLrcModel(@Query("id") String id);
}
