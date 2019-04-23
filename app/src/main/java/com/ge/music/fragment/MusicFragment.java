package com.ge.music.fragment;



import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ge.music.R;
import com.ge.music.adapter.MusicAdapter;
import com.ge.music.base.BaseFragment;
import com.ge.music.model.MusicModel;
import com.youth.banner.Banner;

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
        musicAdapter = new MusicAdapter(getContext(),musicModelList);
        recyclerView.setAdapter(musicAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addView(banner);
    }

    private void initBanner() {
        banner = new Banner(getContext());

//        int width = ViewGroup.LayoutParams.MATCH_PARENT;
//        int height =(int) (getContext().getResources().getDisplayMetrics().density * 100);
//        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width,height);
//        banner.setLayoutParams(lp);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_music;
    }

    @Override
    public String getName() {
        return "音乐";
    }


}
