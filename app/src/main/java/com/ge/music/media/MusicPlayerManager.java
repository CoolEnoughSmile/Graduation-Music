package com.ge.music.media;

import android.media.MediaPlayer;

import com.ge.music.model.MusicModel;

import java.io.IOException;

public class MusicPlayerManager {

    private MediaPlayer mediaPlayer;
    private MusicPlayerStatusChangeListener listener;
    private MusicModel musicModel;

    public MusicPlayerManager(MusicPlayerStatusChangeListener listener){
        this.listener = listener;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnPreparedListener((mp)->{
            mp.start();
            listener.onMusicChange(musicModel);
        });

        mediaPlayer.setOnErrorListener((mp, what, extra) ->{
            listener.onPlayerError();
            return false;
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            listener.onComplete();
        });
    }

    public void play(MusicModel musicModel){
        try {
            this.musicModel = musicModel;
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicModel.getUrl());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startOrPause(){
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            if (mediaPlayer.getCurrentPosition() >= 0 ){
                mediaPlayer.start();
            }else {
                mediaPlayer.prepareAsync();
            }
        }
    }

    public void seekTo(int progress){
        mediaPlayer.seekTo(progress);
    }

    public long getDuration(){
        return mediaPlayer.getDuration();
    }

    public long getCurrentPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    public void release(){
        if (mediaPlayer !=null){
            mediaPlayer.release();
        }
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}
