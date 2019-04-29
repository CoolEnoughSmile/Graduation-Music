package com.ge.music.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.base.GraduationEraMusic;


public class SplashActivity extends BaseActivity {

    private static Handler handler;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gotoMainActivity();
    }



    private void gotoMainActivity() {
        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent;
            if (GraduationEraMusic.getUser() == null){
                intent=new Intent(this, LoginActivity.class);
            }else {
                intent=new Intent(this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }, 1500);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.avtivity_splash;
    }

    @Override
    protected boolean isWhiteStatusBar() {
        return false;
    }


}
