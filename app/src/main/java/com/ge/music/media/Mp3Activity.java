package com.ge.music.media;

import com.bumptech.glide.Glide;
import com.ge.music.base.BaseActivity;
import com.ge.music.R;

import cn.jzvd.Jzvd;

public class Mp3Activity extends BaseActivity {

    private JzvdStdMp3 jzvdStdMp3;

    @Override
    protected void initView() {
        jzvdStdMp3 = findViewById(R.id.jz_videoplayer_mp3);
        jzvdStdMp3.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4", "饺子摇摆", Jzvd.SCREEN_WINDOW_NORMAL);
//        Glide.with(this)
//                .load(VideoConstant.videoThumbs[0][1])
//                .into(jzvdStdMp3.thumbImageView);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mp3;
    }

}
