package com.ge.music.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.http.CallHelper;
import com.ge.music.http.GeMusicResponse;
import com.ge.music.http.HttpHelper;
import com.ge.music.http.model.User;
import com.ge.music.utils.CountDownTimerUtils;
import com.ge.music.utils.SMSEventHandler;

import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Response;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText newPasswordEdt;
    private EditText vCodeEdt;
    private Button sendBtn;
    private ProgressDialog progressDialog;

    private SMSEventHandler smsEventHandler;
    private CountDownTimerUtils countDownTimerUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsEventHandler = new SMSEventHandler(smsEventListener);
        countDownTimerUtils = new CountDownTimerUtils(sendBtn, 60000, 1000);
        SMSSDK.registerEventHandler(smsEventHandler);
    }

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText("重置密码");
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.reset_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        newPasswordEdt = findViewById(R.id.new_password_edt);
        vCodeEdt = findViewById(R.id.vcode_edt);
        sendBtn = findViewById(R.id.send_btn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("修改中");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_reset_password;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.send_btn:
                sendCode();
                break;
            case R.id.reset_btn:
                checkVCode();
                break;
        }
    }

    private void resetPassword(){
        //todo 重置密码
        String phone = phoneEdt.getText().toString().trim();
        String password = newPasswordEdt.getText().toString().trim();
        Call<GeMusicResponse<User>> call = HttpHelper.getGeMusicServerApi().resetPassword(phone,password);
        call.enqueue(new CallHelper<GeMusicResponse<User>>(){
            @Override
            public void onResponse(Call<GeMusicResponse<User>> call, Response<GeMusicResponse<User>> response) {
                super.onResponse(call, response);
                progressDialog.dismiss();
                GeMusicResponse<User> geMusicResponse = response.body();
                ToastUtils.showShort(geMusicResponse.getMessage());
                if (geMusicResponse.getCode() == 1){
                    finish();
                }
            }

            @Override
            public void onFailure(Call<GeMusicResponse<User>> call, Throwable t) {
                super.onFailure(call, t);
                progressDialog.dismiss();
            }
        });
    }

    private void checkVCode() {
        progressDialog.show();
        String phone = phoneEdt.getText().toString().trim();
        String vCode = vCodeEdt.getText().toString().trim();
        SMSSDK.submitVerificationCode(SMSEventHandler.COUNTRY,phone,vCode);
    }

    private void sendCode() {
        String phone = phoneEdt.getText().toString().trim();
        String password = newPasswordEdt.getText().toString().trim();
        if (!RegexUtils.isMobileExact(phone)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("密码不能为空");
            return;
        }
        countDownTimerUtils.start();
        SMSSDK.getVerificationCode(SMSEventHandler.COUNTRY, phone);
    }

    private SMSEventHandler.SMSEventListener smsEventListener = new SMSEventHandler.SMSEventListener() {
        @Override
        public void onVCodeSendSuccess() {
            countDownTimerUtils.start();
            ToastUtils.showShort("发送成功");
        }

        @Override
        public void onVCodeCheckSuccess() {
            resetPassword();
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
}
