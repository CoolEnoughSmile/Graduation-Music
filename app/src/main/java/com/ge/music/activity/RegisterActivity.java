package com.ge.music.activity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText passwordEdt;
    private EditText passwordAgainEdt;
    private Button sendBtn;
    private Button registerBtn;

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_tv)).setText("用户注册");
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        passwordEdt = findViewById(R.id.password_edt);
        passwordAgainEdt = findViewById(R.id.password_again_edt);
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
                break;
            case R.id.register_btn:
                break;
        }
    }
}