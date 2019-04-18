package com.ge.music.fragment;


import android.view.View;

import com.ge.music.CESView.CountDownTimerButton;
import com.ge.music.R;
import com.ge.music.base.BaseFragment;
import com.ge.music.utils.ToastUtils;

public class VideoFragment extends BaseFragment {

    private CountDownTimerButton countDownTimerButton;

    @Override
    protected void initView(View view) {
        countDownTimerButton = view.findViewById(R.id.countDownTimerButton);
        countDownTimerButton.setOnClickListener(v -> {
            ToastUtils.showToast(getContext(),"倒计时开始");
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public String getName() {
        return "视频";
    }
}
