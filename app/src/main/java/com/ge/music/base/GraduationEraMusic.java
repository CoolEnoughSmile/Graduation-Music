package com.ge.music.base;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.ge.music.http.model.User;
import com.mob.MobSDK;

public class GraduationEraMusic extends Application {

    private static User user;

    @Override
    public void onCreate() {
        super.onCreate();

        //日志开关
        LogUtils.getConfig().setLogSwitch(true);
        Utils.init(this);
        MobSDK.init(this);
        setUser(User.get());
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        GraduationEraMusic.user = user;
        User.save(user);
    }
}
