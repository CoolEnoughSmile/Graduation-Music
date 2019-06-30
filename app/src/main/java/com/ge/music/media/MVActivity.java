package com.ge.music.media;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.model.VideoModel;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class MVActivity extends BaseActivity {

    private JzvdStd jzvdStd;


    @Override
    protected void initView() {
        jzvdStd = findViewById(R.id.videoplayer);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.BLACK);
        }
        Jzvd.SAVE_PROGRESS = true;
        VideoModel videoModel = getIntent().getParcelableExtra("video");
        jzvdStd.setUp(videoModel.getUrl()
                , videoModel.getVideoName(), Jzvd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(videoModel.getPoster()).into(jzvdStd.thumbImageView);
//        VideoFirstFrameLoader.load(this,videoModel.getUrl(),jzvdStd.thumbImageView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mv;
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
