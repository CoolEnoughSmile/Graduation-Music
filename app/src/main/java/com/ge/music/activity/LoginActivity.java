package com.ge.music.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.ge.music.CESView.LoadingDialog;
import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.base.GraduationEraMusic;
import com.ge.music.http.CallHelper;
import com.ge.music.http.GeMusicResponse;
import com.ge.music.http.HttpHelper;
import com.ge.music.http.model.User;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText phoneEdt;
    private EditText passwordEdt;

    private LoadingDialog loadingDialog;

    protected void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        findViewById(R.id.qq_btn).setOnClickListener(this);
        findViewById(R.id.weibo_btn).setOnClickListener(this);
        findViewById(R.id.login_without_password_btn).setOnClickListener(this);
        findViewById(R.id.forget_password_btn).setOnClickListener(this);
        findViewById(R.id.register_btn).setOnClickListener(this);

        phoneEdt = findViewById(R.id.phone_edt);
        passwordEdt = findViewById(R.id.password_edt);
        loadingDialog = new LoadingDialog(this);

        inputAccount();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loginWithPhoneNumber();
                break;
            case R.id.qq_btn:
                loginWithQQ();
                break;
            case R.id.weibo_btn:
                loginWithWeibo();
                break;
            case R.id.login_without_password_btn:
                startActivity(new Intent(LoginActivity.this, LoginWithVCodeActivity.class));
                finish();
                break;
            case R.id.forget_password_btn:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            case R.id.register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    private void loginWithPhoneNumber() {
        String phone = phoneEdt.getText().toString().trim();
        String passWord = passwordEdt.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)) {
            Toast.makeText(this, "请输入正确的手机号和密码", Toast.LENGTH_SHORT).show();
            return;
        }
        loadingDialog.show();
        //todo 密码登录
        Call<GeMusicResponse<User>> call = HttpHelper.getGeMusicServerApi().loginWithPhoneAndPassword(phone, passWord);
        call.enqueue(new CallHelper<GeMusicResponse<User>>() {
            @Override
            public void onResponse(Call<GeMusicResponse<User>> call, Response<GeMusicResponse<User>> response) {
                GeMusicResponse<User> geMusicResponse = response.body();
                loadingDialog.dismiss();
                if (geMusicResponse.getCode() == 1) {
                    saveAccount(phone,passWord);
                    User user = geMusicResponse.getData();
                    GraduationEraMusic.setUser(user);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.showShort(geMusicResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GeMusicResponse<User>> call, Throwable t) {
                super.onFailure(call, t);
                loadingDialog.dismiss();
            }
        });
    }

    private void saveAccount(String phone, String passWord) {
        SPUtils.getInstance().put("phone",phone);
        SPUtils.getInstance().put("password",passWord);
    }

    private void inputAccount() {
        String phone = SPUtils.getInstance().getString("phone","");
        String password = SPUtils.getInstance().getString("password","");
        phoneEdt.setText(phone);
        passwordEdt.setText(password);
    }

    private void loginWithWeibo() {
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        if (weibo.isAuthValid()) {
            weibo.removeAccount(true);
        }
        weibo.setPlatformActionListener(platformActionListener);
        weibo.showUser(null);
    }

    private void loginWithQQ() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        if (qq.isAuthValid()) {
            qq.removeAccount(true);
        }
        qq.setPlatformActionListener(platformActionListener);
        qq.showUser(null);
    }

    private PlatformActionListener platformActionListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
            if (action == Platform.ACTION_USER_INFOR) {
                PlatformDb platDB = platform.getDb();//获取数平台数据DB
                //通过DB获取各种数据
                String token = platDB.getToken();
                String gender = platDB.getUserGender();
                String userIcon = platDB.getUserIcon();
                String userId = platDB.getUserId();
                String userName = platDB.getUserName();

                User user = new User(userId,userName,"","",userIcon,token,gender);
                LogUtils.i("token = " + token,
                        "gender = " + gender,
                        "userIcon = " + userIcon,
                        "userId = " + userId,
                        "userName = " + userName);
                GraduationEraMusic.setUser(user);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    };

    @Override
    protected boolean isWhiteStatusBar() {
        return true;
    }

}
