package com.ge.music.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.ToastUtils;

import java.util.Arrays;


public class SplashActivity extends BaseActivity {

    private static Handler handler;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},0);
            return;
        }

        gotoMainActivity();
    }

    private void gotoMainActivity() {
        handler = new Handler();
        handler.postDelayed(() ->{
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        },2000);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("permissions","requestCode = " + requestCode);
        Log.d("permissions","permissions = " + Arrays.toString(permissions));
        Log.d("permissions","grantResults = " + Arrays.toString(grantResults));
        if (grantResults.length < 0){
            gotoMainActivity();
        }else {
            finish();
        }
    }
}
