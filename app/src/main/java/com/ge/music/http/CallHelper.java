package com.ge.music.http;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallHelper<GeMusicResponse> implements Callback<GeMusicResponse> {


    @Override
    public void onResponse(Call<GeMusicResponse> call, Response<GeMusicResponse> response) {

    }

    @Override
    public void onFailure(Call<GeMusicResponse> call, Throwable t) {
        LogUtils.e(t);
        ToastUtils.showShort(t.getMessage());
    }
}
