package com.ge.music.fragment;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ge.music.R;
import com.ge.music.adapter.VideoAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.media.MVActivity;
import com.ge.music.model.VideoModel;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        List<VideoModel> videoModelList = new ArrayList<>(10);
        for (int i = 0; i <10; i++) {
            videoModelList.add(new VideoModel("my heart will go on",
                    "http://img0.c.yinyuetai.com/video/mv/180606/0/-M-3d975d37eb0a23a0add7412f3fb98280_240x135.png",
                    "url",(i+1)+"万","Celine Dion"));
        }
        videoAdapter = new VideoAdapter(videoModelList);
        recyclerView.setAdapter(videoAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        videoAdapter.setOnItemClickListener((adapter, view1, position) ->
                startActivity(new Intent(getContext(),MVActivity.class))
//                        Jzvd.startFullscreen(getContext(),Jzvd.class,"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4","dfsa")
        );
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
