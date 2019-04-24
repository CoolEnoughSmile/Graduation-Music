package com.ge.music.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ge.music.R;
import com.ge.music.model.VideoModel;
import com.ge.music.utils.VideoFirstFrameLoader;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoAdapter extends BaseQuickAdapter<VideoModel, BaseViewHolder> {

    public VideoAdapter(List<VideoModel> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoModel item) {
        JzvdStd jzvdStd = helper.getView(R.id.videoplayer);
        jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                , "左手右手一起撸", Jzvd.SCREEN_WINDOW_NORMAL);
        VideoFirstFrameLoader.load(mContext,
                "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4",
                jzvdStd.thumbImageView,1);
        helper.setText(R.id.video_name_tv,"左右手一起撸");
    }
}
