package com.ge.music.base;

import android.app.Application;

import com.ge.music.utils.ToastUtils;
import com.mob.MobSDK;

public class GraduationEraMusic extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MobSDK.init(this);
        ToastUtils.init(this);
    }
}
