package com.ge.music.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView loginBtn;


    protected void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
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
                break;
        }
    }

    @Override
    protected boolean isWhilteStatusBar() {
        return true;
    }
}
