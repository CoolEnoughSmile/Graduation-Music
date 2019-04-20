package com.ge.music.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText passwordEdt;

    protected void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.qq_btn).setOnClickListener(this);
        findViewById(R.id.weibo_btn).setOnClickListener(this);
        findViewById(R.id.login_without_password_btn).setOnClickListener(this);
        findViewById(R.id.forget_password_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        passwordEdt = findViewById(R.id.password_edt);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                loginWithPhoneNumber();
                break;
            case R.id.qq_btn:
                loginWithQQ();
                break;
            case R.id.weibo_btn:
                loginWithWeibo();
                break;
            case R.id.login_without_password_btn:
                startActivity(new Intent(LoginActivity.this,LoginWithVCodeActivity.class));
                finish();
                break;
            case R.id.forget_password_btn:
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                break;
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    private void loginWithPhoneNumber() {
        String phone = phoneEdt.getText().toString().trim();
        String passWord = passwordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)){
            Toast.makeText(this,"请输入正确的手机号和密码",Toast.LENGTH_SHORT).show();
            return;
        }
//        UMSSDK.loginWithPhoneNumber("86",phone,passWord,loginCallback);
    }

    private void loginWithWeibo() {
    }

    private void loginWithQQ() {
    }

    @Override
    protected boolean isWhiteStatusBar() {
        return true;
    }

}
