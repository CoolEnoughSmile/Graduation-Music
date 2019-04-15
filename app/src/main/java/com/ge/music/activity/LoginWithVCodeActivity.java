package com.ge.music.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.CountDownTimerUtils;
import com.ge.music.utils.ToastUtils;
import com.mob.ums.OperationCallback;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

public class LoginWithVCodeActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText vCodeEdt;
    private Button sendBtn;
    private CountDownTimerUtils countDownTimerUtils;

    @Override
    protected void initView() {
        ((TextView) findViewById(R.id.title_tv)).setText("验证码登录");
        findViewById(R.id.back_btn).setOnClickListener(this);
        sendBtn = findViewById(R.id.send_btn);
        sendBtn.setOnClickListener(this);
        findViewById(R.id.login_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        vCodeEdt = findViewById(R.id.vcode_edt);
        countDownTimerUtils = new CountDownTimerUtils(sendBtn, 60000, 1000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login_with_vcode;
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
            case R.id.login_btn:
                loginWithVCode();
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void loginWithVCode() {
        String phone = phoneEdt.getText().toString().trim();
        String vCode = vCodeEdt.getText().toString().trim();
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},0);
            return;
        }
        String simSerialNumber = manager.getSimSerialNumber();
        UMSSDK.loginWithPhoneVCode("86",phone,vCode,simSerialNumber,loginCallback);
    }

    private void sendCode() {
        String phone = phoneEdt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast(this,"请输入手机号");
            return;
        }
        UMSSDK.sendVerifyCode("86",phone, sendCodeCallback);
    }

    private OperationCallback<Boolean> sendCodeCallback = new OperationCallback<Boolean>(){
        @Override
        public void onSuccess(Boolean isSuccess) {
//            countDownTimerUtils.start();
            Log.d("sendCode","success:" + isSuccess);
        }

        @Override
        public void onFailed(Throwable throwable) {
            Log.e("sendCode",throwable.getMessage());
            throwable.printStackTrace();
        }

        @Override
        public void onCancel() {
            Log.e("sendCode","cancel");
        }
    };

    private OperationCallback<User> loginCallback = new OperationCallback<User>(){
        @Override
        public void onSuccess(User user) {
            Intent intent = new Intent(LoginWithVCodeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }

        @Override
        public void onFailed(Throwable throwable) {
            Log.e("loginWithVCode",throwable.getMessage());
            throwable.printStackTrace();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimerUtils.release();
        countDownTimerUtils = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length != 0){
            loginWithVCode();
        }
    }
}
