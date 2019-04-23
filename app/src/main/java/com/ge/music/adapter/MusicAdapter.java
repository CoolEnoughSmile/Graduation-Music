package com.ge.music.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ge.music.model.MusicModel;
import com.ge.music.R;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.Holer> {



    private Context context;
    private List<MusicModel> musicList;

    public MusicAdapter(Context context, List<MusicModel> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public Holer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music,parent,false);
        return new Holer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holer holer, int postion) {
        MusicModel musicModel = musicList.get(postion);
        Glide.with(context)
                .load(musicModel.getPoster())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(holer.posterIv);
        holer.musicNameTv.setText(musicModel.getMusicName());
        holer.playCountTv.setText(musicModel.getPalyCount());
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    class Holer extends RecyclerView.ViewHolder{

        ImageView posterIv;
        TextView musicNameTv;
        TextView playCountTv;

        public Holer(@NonNull View itemView) {
            super(itemView);
            posterIv = itemView.findViewById(R.id.poster_iv);
            musicNameTv = itemView.findViewById(R.id.music_name_tv);
            playCountTv = itemView.findViewById(R.id.play_count_tv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
