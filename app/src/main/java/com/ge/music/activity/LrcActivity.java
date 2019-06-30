package com.ge.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.SeekBar;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.CESView.LrcView.LrcView;
import com.ge.music.R;
import com.ge.music.http.HttpHelper;
import com.ge.music.http.model.LrcModel;
import com.ge.music.media.MessageEvent;
import com.ge.music.media.MusicConstant;
import com.ge.music.model.MusicModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LrcActivity extends AppCompatActivity{

    private LrcView lrcView;
    private SeekBar seekBar;
    private CheckBox playPauseBtn;
    private ImageButton preBtn;
    private ImageButton nextBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dialog_playing);

        lrcView = findViewById(R.id.lrc_view);
        seekBar = findViewById(R.id.progress_bar);
        playPauseBtn = findViewById(R.id.play_pause_btn);
        preBtn = findViewById(R.id.pre_btn);
        nextBtn = findViewById(R.id.next_btn);

        EventBus.getDefault().register(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                lrcView.updateTime(seekBar.getProgress());
                Map<String,Object> map = new HashMap<>();
                map.put("progress",seekBar.getProgress());
                EventBus.getDefault().post(new MessageEvent(MusicConstant.SEEK_PROGRESS,map));
            }
        });

        preBtn.setOnClickListener(v -> {
            EventBus.getDefault().post(new MessageEvent(MusicConstant.PRE, null));
        });
        nextBtn.setOnClickListener(v -> {
            EventBus.getDefault().post(new MessageEvent(MusicConstant.NEXT, null));
        });
        playPauseBtn.setOnClickListener(v ->{
            EventBus.getDefault().post(new MessageEvent(MusicConstant.START_PAUSE, null));
        });

        lrcView.setOnPlayClickListener(time -> {
            seekBar.setProgress((int) time);
            Map<String,Object> map = new HashMap<>();
            map.put("progress",seekBar.getProgress());
            EventBus.getDefault().post(new MessageEvent(MusicConstant.SEEK_PROGRESS,map));
            return true;
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(MessageEvent event){
        switch (event.getEvent()){
            case MusicConstant.MUSIC_CHANGE:
                MusicModel musicModel = (MusicModel) event.getData().get("musicModel");
                int duration = (int) event.getData().get("duration");
                seekBar.setMax(duration);
                seekBar.setProgress(0);
                loadLrc(musicModel.getLrc());
                playPauseBtn.setChecked(true);
                break;
            case MusicConstant.PROGRESS:
                int progress = (int) event.getData().get("progress");
                seekBar.setProgress(progress);
                lrcView.updateTime(progress);
                break;
            case MusicConstant.PLAYER_ERROR:
            case MusicConstant.COMPLETE:
                lrcView.updateTime(0);
                seekBar.setProgress(0);
                playPauseBtn.setChecked(false);
                break;
        }
    }

    private void loadLrc(String id) {
        Call<LrcModel> call = HttpHelper.getLrcApi().getLrcModel(id);
        call.enqueue(new Callback<LrcModel>() {
            @Override
            public void onResponse(Call<LrcModel> call, Response<LrcModel> response) {
                LrcModel lrcModel = response.body();
                if (lrcModel.getCode() == 200){
                    lrcView.loadLrc(lrcModel.getLyric());
                }
                LogUtils.d(lrcModel);
            }

            @Override
            public void onFailure(Call<LrcModel> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
        EventBus.getDefault().unregister(this);
    }
}
