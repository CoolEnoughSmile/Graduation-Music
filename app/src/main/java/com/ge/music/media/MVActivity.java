package com.ge.music.media;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.VideoFirstFrameLoader;

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
        Jzvd.SAVE_PROGRESS = false;
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "左手右手一起撸", Jzvd.SCREEN_WINDOW_NORMAL);
        VideoFirstFrameLoader.load(this,"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",jzvdStd.thumbImageView);
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
}
