package com.ge.music.media;

import android.support.v7.widget.RecyclerView;

import com.ge.music.R;
import com.ge.music.base.BaseActivity;
import com.ge.music.utils.VideoFirstFrameLoader;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class MVActivity extends BaseActivity {

    private JzvdStd jzvdStd;
    private RecyclerView recyclerView;


    @Override
    protected void initView() {
        jzvdStd = findViewById(R.id.videoplayer);
        recyclerView = findViewById(R.id.recyclerview);

        Jzvd.SAVE_PROGRESS = false;
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "左手右手一起撸", Jzvd.SCREEN_WINDOW_NORMAL);
        VideoFirstFrameLoader.load(this,"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",jzvdStd.thumbImageView,1);

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
