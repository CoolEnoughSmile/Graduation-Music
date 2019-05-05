package com.ge.music.media;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.model.MusicModel;

import java.io.IOException;


public class PlayMusicService extends Service {

    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this,Uri.parse("http://music.163.com/song/media/outer/url?id=38592976.mp3"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            Intent intent = new Intent("musicService");
            intent.putExtra("status","start");
            sendBroadcast(intent);
        });
        mediaPlayer.setOnErrorListener((mp, what, extra) -> {
            Intent intent = new Intent("musicService");
            intent.putExtra("status","error");
            sendBroadcast(intent);
            LogUtils.d(what,extra);
            return false;
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            Intent intent = new Intent("musicService");
            intent.putExtra("status","completion");
            sendBroadcast(intent);
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getStringExtra("action");
        MusicModel musicModel = intent.getParcelableExtra("musicModel");
        switch (action) {
            case "playOrPause":
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                } else {
                    if (mediaPlayer.getCurrentPosition() > 0 ){
                        mediaPlayer.start();
                    }else {
                        mediaPlayer.prepareAsync();
                    }
                }
                break;
            case "play":
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicModel.getUrl());
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
