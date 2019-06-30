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
import com.blankj.utilcode.util.ToastUtils;
import com.ge.music.CESView.LrcView.LrcView;
import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.http.CallHelper;
import com.ge.music.http.HttpHelper;
import com.ge.music.http.model.LrcModel;
import com.ge.music.model.MusicModel;

import retrofit2.Call;
import retrofit2.Response;


public class PlayingDialogFragment extends BottomSheetDialogFragment {

    private LrcView lrcView;
    private MainActivity mainActivity;
    private MusicModel musicModel;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_playing, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lrcView = view.findViewById(R.id.lrc_view);
        if (musicModel != null) {
            String lrc = musicModel.getLrc();
            LogUtils.d(lrc);
            lrcView.loadLrc(musicModel.getLrc());
        }
        Call<LrcModel> call = HttpHelper.getLrcApi().getLrcModel("26060065");
        call.enqueue(new CallHelper<LrcModel>(){
            @Override
            public void onResponse(Call<LrcModel> call, Response<LrcModel> response) {
                super.onResponse(call, response);
                LrcModel lrcModel = response.body();
                if (lrcModel.getCode() == 200){
                    lrcView.loadLrc(lrcModel.getLyric());
                    lrcView.updateTime(5000);
                    ToastUtils.showShort("获取歌词完成");
                }
            }

            @Override
            public void onFailure(Call<LrcModel> call, Throwable t) {
                super.onFailure(call, t);
                ToastUtils.showShort("获取歌词失败");
            }
        });
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
