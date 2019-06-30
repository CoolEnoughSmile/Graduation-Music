package com.ge.music.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.R;
import com.ge.music.adapter.VideoAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.http.CallHelper;
import com.ge.music.http.GeMusicResponse;
import com.ge.music.http.HttpHelper;
import com.ge.music.media.MVActivity;
import com.ge.music.media.MessageEvent;
import com.ge.music.media.MusicConstant;
import com.ge.music.model.VideoModel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoAdapter videoAdapter;

    private int pageNum = 1;
    private final int pageSize = 4;

    @Override
    protected void initView(View view) {
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = view.findViewById(R.id.recyclerview);
        List<VideoModel> videoModelList = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoModelList);
        videoAdapter.setOnLoadMoreListener(() ->{
            loadVideoList(false);
        });
        recyclerView.setAdapter(videoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        videoAdapter.setOnItemClickListener((adapter, view1, position) -> {
            VideoModel videoModel = (VideoModel) adapter.getItem(position);
            Intent intent = new Intent(getContext(), MVActivity.class);
            intent.putExtra("video",videoModel);
            startActivity(intent);
            EventBus.getDefault().post(new MessageEvent(MusicConstant.START_PAUSE, null));
        });
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            loadVideoList(true);
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoAdapter.setEnableLoadMore(false);
        loadVideoList(true);
    }

    private void loadVideoList(boolean isRefresh){
        if (!swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(true);
        }
        Call<GeMusicResponse<List<VideoModel>>> call = HttpHelper.getGeMusicServerApi().getVideoList(pageNum, pageSize);
        call.enqueue(new CallHelper<GeMusicResponse<List<VideoModel>>>(){
            @Override
            public void onResponse(Call<GeMusicResponse<List<VideoModel>>> call, Response<GeMusicResponse<List<VideoModel>>> response) {
                super.onResponse(call, response);
                swipeRefreshLayout.setRefreshing(false);
                GeMusicResponse<List<VideoModel>> geMusicResponse = response.body();
                if (geMusicResponse.getCode() == 1){
                    List<VideoModel> videoModelList = geMusicResponse.getData();
                    LogUtils.d(videoModelList.size());
                    if (isRefresh){
                        videoAdapter.getData().clear();
                        videoAdapter.setNewData(videoModelList);
                    }else {
                        videoAdapter.addData(videoModelList);
                        videoAdapter.loadMoreComplete();
                        videoAdapter.notifyDataSetChanged();
                    }
                    pageNum++;
                    videoAdapter.setEnableLoadMore(true);
                }else {
                    videoAdapter.loadMoreEnd();
                }
            }

            @Override
            public void onFailure(Call<GeMusicResponse<List<VideoModel>>> call, Throwable t) {
                super.onFailure(call, t);
                swipeRefreshLayout.setRefreshing(false);
                videoAdapter.loadMoreFail();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_video;
    }

    @Override
    public String getName() {
        return "视频";
    }
}
