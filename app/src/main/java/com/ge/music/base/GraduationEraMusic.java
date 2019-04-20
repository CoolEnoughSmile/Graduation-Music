package com.ge.music.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.mob.MobSDK;

public class GraduationEraMusic extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        MobSDK.init(this);
    }
}
