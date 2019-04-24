package com.ge.music.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ge.music.R;
import com.ge.music.adapter.VideoAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        List<VideoModel> videoModelList = new ArrayList<>(10);
        for (int i = 0; i <10; i++) {
            videoModelList.add(new VideoModel());
        }
        videoAdapter = new VideoAdapter(videoModelList);
        recyclerView.setAdapter(videoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
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
