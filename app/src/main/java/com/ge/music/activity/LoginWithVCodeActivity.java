package com.ge.music.activity;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.CountDownTimerUtils;
import com.ge.music.utils.ToastUtils;

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

    private void loginWithVCode() {
        String phone = phoneEdt.getText().toString().trim();
        String vCode = vCodeEdt.getText().toString().trim();

    }

    private void sendCode() {
        String phone = phoneEdt.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast(this,"请输入手机号");
            return;
        }
    }

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
