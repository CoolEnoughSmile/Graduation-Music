package com.ge.music.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ge.music.R;
import com.ge.music.model.VideoModel;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoModel, BaseViewHolder> {

    public VideoAdapter(List<VideoModel> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoModel videoModel) {
        ImageView posterIv = helper.getView(R.id.poster_iv);
        Glide.with(mContext)
                .load(videoModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)).placeholder(R.mipmap.ic_qq))
                .into(posterIv);
        helper.setText(R.id.video_name_tv,videoModel.getVideoName());
    }
}