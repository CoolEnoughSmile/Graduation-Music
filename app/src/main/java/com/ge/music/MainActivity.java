package com.ge.music;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ge.music.CESView.MainTopTabView;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.ToastUtils;


public class MainActivity extends BaseActivity {

    private MainTopTabView mainTopTabView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mainTopTabView = findViewById(R.id.top_tab_bar);

        mainTopTabView.setListener(new MainTopTabView.Listener() {
            @Override
            public void onLeftClick(View view) {
                ToastUtils.showToast("onLeftClick");
                Log.e("mainTopTabView","onLeftClick");
            }

            @Override
            public void onRightClick(View view) {
                ToastUtils.showToast("onRightClick");
                Log.e("mainTopTabView","onRightClick");
            }

            @Override
            public void onFirstTabSelected(View view) {
                ToastUtils.showToast("onFirstTabSelected");
                Log.e("mainTopTabView","onFirstTabSelected");
            }

            @Override
            public void onSecondTabSelected(View view) {
                ToastUtils.showToast("onSecondTabSelected");
                Log.e("mainTopTabView","onSecondTabSelected");
            }

            @Override
            public void onThirdTabSelected(View view) {
                ToastUtils.showToast("onThirdTabSelected");
                Log.e("mainTopTabView","onThirdTabSelected");
            }

            @Override
            public void onFourthTabSelected(View view) {
                ToastUtils.showToast("onFourthTabSelected");
                Log.e("mainTopTabView","onFourthTabSelected");
            }
        });
    }


}
