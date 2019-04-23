package com.ge.music.fragment;



import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ge.music.R;
import com.ge.music.adapter.MusicAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.model.MusicModel;
import com.ge.music.utils.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends BaseFragment {

    private Banner banner;
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    @Override
    protected void initView(View view) {
        initBanner();
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        List<MusicModel> musicModelList = new ArrayList<>(10);
        for (int i=0;i<10;i++){
            MusicModel musicModel = new MusicModel("All I Have Is Love",
                    "http://p3.music.126.net/2MsstS-M9w5-li0aRy3sUQ==/1380986606815861.jpg?param=200y200",
                    "",
                    (i+1)+"万",
                    "",
                    "");
            musicModelList.add(musicModel);
        }
        musicAdapter = new MusicAdapter(musicModelList);
        musicAdapter.addHeaderView(banner);
        musicAdapter.setHeaderViewAsFlow(true);
        recyclerView.setAdapter(musicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        musicAdapter.bindToRecyclerView(recyclerView);
    }

    private void initBanner() {
        //设置图片集合
        List list=new ArrayList();
        list.add("http://p1.music.126.net/Ks58J-AJ8sx7oJh1KRThIQ==/109951164018985269.jpg");
        list.add("http://p1.music.126.net/AnCyFQlk8M6lqMHu381afg==/109951164019008126.jpg");
        list.add("http://p1.music.126.net/4Z0Vd27keWfLz8lcx731NA==/109951164017932547.jpg");

        banner = (Banner) LayoutInflater.from(getContext()).inflate(R.layout.music_banner,(ViewGroup) getView(),false);
        banner.setImageLoader(new BannerImageLoader())
                .setImages(list)
                .setBannerAnimation(Transformer.Stack)
                .start();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    public String getName() {
        return "音乐";
    }


    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
