package com.ge.music.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;

public class LoginWithVCodeActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText vCodeEdt;

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_tv)).setText("验证码登录");
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.send_btn).setOnClickListener(this);
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
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.send_btn:
                break;
            case R.id.login_btn:
                break;
        }
    }
}
