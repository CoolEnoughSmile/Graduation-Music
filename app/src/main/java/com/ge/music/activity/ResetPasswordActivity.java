package com.ge.music.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;

public class ResetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText newPasswordEdt;
    private EditText vCodeEdt;

    @Override
    protected void initView() {
        ((TextView)findViewById(R.id.title_tv)).setText("重置密码");
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.send_btn).setOnClickListener(this);
        findViewById(R.id.reset_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        newPasswordEdt = findViewById(R.id.new_password_edt);
        vCodeEdt = findViewById(R.id.vcode_edt);
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
        }
    }
}
