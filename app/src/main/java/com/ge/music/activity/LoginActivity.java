package com.ge.music.activity;

import android.content.Intent;
import android.view.View;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.mob.ums.OperationCallback;
import com.mob.ums.SocialNetwork;
import com.mob.ums.UMSSDK;
import com.mob.ums.User;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    protected void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.qq_btn).setOnClickListener(this);
        findViewById(R.id.weibo_btn).setOnClickListener(this);
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
            case R.id.qq_btn:
                loginWithQQ();
                break;
            case R.id.weibo_btn:
                break;
        }
    }

    private void loginWithQQ() {
        UMSSDK.loginWithSocialAccount(SocialNetwork.QQ,new OperationCallback<User>(){
            public void onSuccess(User user) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }

            public void onCancel() {
                // 执行取消的操作
            }

            public void onFailed(Throwable t) {
                // 提示错误信息
            }
        });
    }

    @Override
    protected boolean isWhiteStatusBar() {
        return true;
    }
}
