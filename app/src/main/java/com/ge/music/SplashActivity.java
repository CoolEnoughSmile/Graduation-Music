package com.ge.music;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ge.music.base.BaseActivity;



public class SplashActivity extends BaseActivity {

    private static Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_splash);

        handler = new Handler();
        handler.postDelayed(() ->{
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        },2000);
    }


}
