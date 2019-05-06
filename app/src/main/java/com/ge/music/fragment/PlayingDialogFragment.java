package com.ge.music.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.model.MusicModel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.zhaiyifan.lyric.LyricUtils;
import cn.zhaiyifan.lyric.widget.LyricView;


public class PlayingDialogFragment extends BottomSheetDialogFragment {

    private LyricView lyricView;
    private MainActivity mainActivity;
    private MusicModel musicModel;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;
        List<MusicModel> list = mainActivity.getList();
        if(list != null && !list.isEmpty()){
            musicModel = list.get(mainActivity.currentMusicIndex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_playing, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lyricView = view.findViewById(R.id.lrcView);
        if (musicModel != null) {
            String lrc = musicModel.getLrc();
            LogUtils.d(lrc);
            lyricView.setLyric(LyricUtils.parseLyric(new ByteArrayInputStream(lrc.getBytes()),"UTF-8"));
            lyricView.play();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT; //可以写入自己想要的高度
        }
        final View view = getView();
        view.post(() ->{
            View parent = (View) view.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
            parent.setBackgroundColor(Color.WHITE);
        });

    }

}
