package com.ge.music.fragment;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.adapter.MusicAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.http.CallHelper;
import com.ge.music.http.GeMusicResponse;
import com.ge.music.http.HttpHelper;
import com.ge.music.media.PlayMusicService;
import com.ge.music.model.MusicModel;
import com.ge.music.utils.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MusicFragment extends BaseFragment {

    private Banner banner;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MusicAdapter musicAdapter;

    private int pageNum = 1;
    private final int pageSize = 15;
    private PlayMusicService musicService;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadMusicList(true);
    }

    @Override
    protected void initView(View view) {
        initBanner();
        initRecyclerView(view);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            loadMusicList(true);
        });
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        initMusicAdapter();
        recyclerView.setAdapter(musicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        musicAdapter.bindToRecyclerView(recyclerView);
    }

    private void initMusicAdapter() {
        List<MusicModel> musicModelList = new ArrayList<>();
        musicAdapter = new MusicAdapter(musicModelList);
        musicAdapter.addHeaderView(banner);
        musicAdapter.setHeaderViewAsFlow(true);

        musicAdapter.setOnLoadMoreListener(() ->{
            loadMusicList(false);
        });
    }

    private void initBanner() {
        //设置图片集合
        List list=new ArrayList();
        list.add("http://p1.music.126.net/Ks58J-AJ8sx7oJh1KRThIQ==/109951164018985269.jpg");
        list.add("http://p1.music.126.net/AnCyFQlk8M6lqMHu381afg==/109951164019008126.jpg");
        list.add("http://p1.music.126.net/4Z0Vd27keWfLz8lcx731NA==/109951164017932547.jpg");

        banner = (Banner) LayoutInflater.from(getContext()).inflate(R.layout.view_music_banner,(ViewGroup) getView(),false);
        banner.setImageLoader(new BannerImageLoader())
                .setImages(list)
                .setBannerAnimation(Transformer.Stack)
                .start();
    }

    private void loadMusicList(boolean isRefresh){
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Call<GeMusicResponse<List<MusicModel>>> call = HttpHelper.getGeMusicServerApi().getMusicList(pageNum,pageSize);
        call.enqueue(new CallHelper<GeMusicResponse<List<MusicModel>>>(){
            @Override
            public void onResponse(Call<GeMusicResponse<List<MusicModel>>> call, Response<GeMusicResponse<List<MusicModel>>> response) {
                super.onResponse(call, response);
                swipeRefreshLayout.setRefreshing(false);
                GeMusicResponse<List<MusicModel>> geMusicResponse = response.body();
                if (geMusicResponse.getCode() == 1){
                    List<MusicModel> musicModelList = geMusicResponse.getData();
                    if (isRefresh){
                        musicAdapter.setNewData(musicModelList);
                    }else {
                        musicAdapter.addData(musicModelList);
                        musicAdapter.loadMoreComplete();
                        pageNum++;
                    }
                    musicAdapter.notifyDataSetChanged();
                }else {
                    ToastUtils.showShort(geMusicResponse.getMessage());
                    musicAdapter.loadMoreEnd();
                }
            }

            @Override
            public void onFailure(Call<GeMusicResponse<List<MusicModel>>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                musicAdapter.loadMoreFail();
            }
        });
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

    public void onMusicServiceCreated(PlayMusicService musicService){
        musicAdapter.setOnItemClickListener((adapter,itemView,position) -> {
            MusicModel music = (MusicModel) adapter.getItem(position);
            ToastUtils.showLong(((MusicModel)adapter.getItem(position)).getMusicName());
            musicService.addNewAndPlay(music);
        });
    }

}
