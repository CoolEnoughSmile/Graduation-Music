package com.ge.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ge.music.base.BaseActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView loginBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setDarkStatusIcon(true);
        initView();
    }

    private void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;
        }
    }
}
