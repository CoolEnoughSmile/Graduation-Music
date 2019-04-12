package com.ge.music.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());
        setDarkStatusIcon(isWhiteStatusBar());
        initView();
    }

    public void setDarkStatusIcon(boolean bDark) {
        if (bDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = getWindow().getDecorView();
                getWindow().setStatusBarColor(Color.WHITE);//这里对应的是状态栏的颜色，就是style中colorPrimaryDark的颜色
                if (decorView != null) {
                    int vis = decorView.getSystemUiVisibility();
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    decorView.setSystemUiVisibility(vis);
                }
            }
        }
    }

    protected abstract void initView();

    protected abstract int getLayout();

    protected boolean isWhiteStatusBar(){
        return false;
    }
}
