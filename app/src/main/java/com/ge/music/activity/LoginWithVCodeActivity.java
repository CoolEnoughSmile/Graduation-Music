package com.ge.music.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.base.GraduationEraMusic;
import com.ge.music.http.CallHelper;
import com.ge.music.http.GeMusicResponse;
import com.ge.music.http.HttpHelper;
import com.ge.music.http.model.User;
import com.ge.music.utils.CountDownTimerUtils;
import com.ge.music.utils.SMSEventHandler;

import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Response;

public class LoginWithVCodeActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText vCodeEdt;
    private Button sendBtn;
    private CountDownTimerUtils countDownTimerUtils;
    private SMSEventHandler smsEventHandler;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsEventHandler = new SMSEventHandler(smsEventListener);
        SMSSDK.registerEventHandler(smsEventHandler);
        countDownTimerUtils = new CountDownTimerUtils(sendBtn, 60000, 1000);
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("登录中");

        ((TextView) findViewById(R.id.title_tv)).setText("验证码登录");
        findViewById(R.id.back_btn).setOnClickListener(this);
        sendBtn = findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        vCodeEdt = findViewById(R.id.vcode_edt);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login_with_vcode;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                back();
                break;
            case R.id.send_btn:
                sendCode();
                break;
            case R.id.login_btn:
                checkVCode();
                break;
        }
    }

    private void back() {
        startActivity(new Intent(LoginWithVCodeActivity.this,LoginActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        finish();
    }

    private void loginWithVCode() {
        //todo 免密登录
        String phone = phoneEdt.getText().toString().trim();
        Call<GeMusicResponse<User>> call = HttpHelper.getGeMusicServerApi().loginWithVCode(phone);
        call.enqueue(new CallHelper<GeMusicResponse<User>>(){
            @Override
            public void onResponse(Call<GeMusicResponse<User>> call, Response<GeMusicResponse<User>> response) {
                super.onResponse(call, response);
                progressDialog.dismiss();
                GeMusicResponse<User> geMusicResponse = response.body();
                if (geMusicResponse.getCode() == 1) {
                    User user = geMusicResponse.getData();
                    GraduationEraMusic.setUser(user);
                    startActivity(new Intent(LoginWithVCodeActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showShort(geMusicResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GeMusicResponse<User>> call, Throwable t) {
                super.onFailure(call, t);
                progressDialog.dismiss();
            }
        });


    }

    private void checkVCode(){
        progressDialog.show();
        String phone = phoneEdt.getText().toString().trim();
        String vCode = vCodeEdt.getText().toString().trim();
        SMSSDK.submitVerificationCode(SMSEventHandler.COUNTRY,phone,vCode);
    }

    private void sendCode() {
        String phone = phoneEdt.getText().toString().trim();
        if (!RegexUtils.isMobileExact(phone)){
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        SMSSDK.getVerificationCode(SMSEventHandler.COUNTRY,phone);
    }

    private SMSEventHandler.SMSEventListener smsEventListener = new SMSEventHandler.SMSEventListener() {
        @Override
        public void onVCodeSendSuccess() {
            countDownTimerUtils.start();
            ToastUtils.showShort("发送成功");
        }

        @Override
        public void onVCodeCheckSuccess() {
            loginWithVCode();
        }

        @Override
        public void onError() {
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.release();
        countDownTimerUtils = null;
        SMSSDK.unregisterEventHandler(smsEventHandler);
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
