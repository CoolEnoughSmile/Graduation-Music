package com.ge.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.R;
import com.ge.music.media.PlayMusicService;

import me.wcy.lrcview.LrcView;

public class LrcActivity extends AppCompatActivity {

    private LrcView lrcView;
    private PlayMusicService playMusicService;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dialog_playing);
        bindService(new Intent(this,PlayMusicService.class),serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playMusicService = ((PlayMusicService.MyBinder) service).getService();
            mediaPlayer = playMusicService.getMediaPlayer();
            initView();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            playMusicService = null;
        }
    };

    private void initView() {
        lrcView = findViewById(R.id.lrc_view);
        seekBar = findViewById(R.id.progress_bar);
        if(playMusicService.getCurMusicModel() != null) {
            lrcView.loadLrc(playMusicService.getCurMusicModel().getLrc());
        }
        lrcView.updateTime(0);
        lrcView.setOnPlayClickListener(time -> {
            mediaPlayer.seekTo((int) time);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                handler.post(runnable);
            }
            return true;
        });
        if (mediaPlayer.isPlaying()) {
            handler.post(runnable);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                LogUtils.d("time = " + time);
//                seekBar.setProgress((int) time);
            }

            handler.postDelayed(this, 300);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
