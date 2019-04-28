package com.ge.music.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ge.music.R;
import com.ge.music.model.MusicModel;

import java.util.List;

public class MusicAdapter extends BaseQuickAdapter<MusicModel, BaseViewHolder> {

    private List<MusicModel> musicList;

    public MusicAdapter(List<MusicModel> musicList) {
        super(R.layout.item_music,musicList);
    }



    @Override
    protected void convert(BaseViewHolder helper, MusicModel musicModel) {
        Glide.with(mContext)
                .load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into((ImageView)helper.getView(R.id.poster_iv));
        helper.setText(R.id.music_name_tv,musicModel.getMusicName());
        helper.setText(R.id.play_count_tv,musicModel.getPalyCount());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);

            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    //注意，这里的position返回的是item在recyclerview中的位置，不是item的数据在数据列表中的位置，是把header和footer算进去的
                    if (position == 0) {
                        return gridManager.getSpanCount();
                    }else {
                        return 1;
                    }
                }
            });
        }
    }
}
