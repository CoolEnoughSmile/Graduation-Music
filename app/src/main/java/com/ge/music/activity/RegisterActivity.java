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
import com.ge.music.utils.CountDownTimerUtils;
import com.ge.music.utils.SMSEventHandler;

import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText passwordEdt;
    private EditText passwordAgainEdt;
    private EditText vCodeEdt;
    private Button sendBtn;
    private ProgressDialog progressDialog;

    private SMSEventHandler smsEventHandler;
    private CountDownTimerUtils countDownTimerUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        smsEventHandler = new SMSEventHandler(smsEventListener);
        SMSSDK.registerEventHandler(smsEventHandler);
        countDownTimerUtils = new CountDownTimerUtils(sendBtn, 60000, 1000);
    }

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_tv)).setText("用户注册");
        findViewById(R.id.back_btn).setOnClickListener(this);
        sendBtn = findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        passwordEdt = findViewById(R.id.password_edt);
        passwordAgainEdt = findViewById(R.id.password_again_edt);
        vCodeEdt = findViewById(R.id.vcode_edt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("注册中");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.send_btn:
                sendCode();
                break;
            case R.id.register_btn:
                checkVCode();
                break;
        }
    }

    private void register(){
        //todo 注册
        progressDialog.dismiss();
        finish();
    }

    private void checkVCode() {
        progressDialog.show();
        String phone = phoneEdt.getText().toString().trim();
        String vCode = vCodeEdt.getText().toString().trim();
        SMSSDK.submitVerificationCode(SMSEventHandler.COUNTRY,phone,vCode);
    }

    private void sendCode() {
        String phone = phoneEdt.getText().toString().trim();
        String password = passwordEdt.getText().toString().trim();
        String passwordAgain = passwordAgainEdt.getText().toString().trim();
        if (!RegexUtils.isMobileExact(phone)){
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(password) || !password.equals(passwordAgain)){
            ToastUtils.showShort("密码不能为空且需一致");
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
            register();
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
