package com.ge.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.ge.music.base.BaseActivity;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class SplashActivity extends BaseActivity {

    private static Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler=new Handler();
        handler.postDelayed(()->{
//            startActivity(new Intent(this,MainActivity.class));
            sendCode(this);
            finish();
        },2000);

    }

    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                    Toast.makeText(context, "验证成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(context,MainActivity.class));
                } else{
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(context);
    }
}
