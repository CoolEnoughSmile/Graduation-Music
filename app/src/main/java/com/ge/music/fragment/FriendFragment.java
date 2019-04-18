package com.ge.music.fragment;


import android.view.View;

import com.ge.music.R;
import com.ge.music.base.BaseFragment;

public class FriendFragment extends BaseFragment {

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    public String getName() {
        return "朋友";
    }
}
