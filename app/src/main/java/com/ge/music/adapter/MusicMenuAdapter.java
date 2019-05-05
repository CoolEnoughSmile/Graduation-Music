package com.ge.music.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ge.music.model.MusicModel;
import com.ge.music.R;
import java.util.List;


public class MusicMenuAdapter extends BaseQuickAdapter<MusicModel,BaseViewHolder> {

    public MusicMenuAdapter(List<MusicModel> data) {
        super(R.layout.item_music_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicModel item) {
        helper.setText(R.id.music_name_tv,item.getMusicName());
        helper.setText(R.id.singer_tv,"-"+item.getSinger());
        helper.addOnClickListener(R.id.remove_btn);
    }
}
