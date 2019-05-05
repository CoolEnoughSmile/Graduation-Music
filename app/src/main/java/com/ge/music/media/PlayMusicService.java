package com.ge.music.media;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.ge.music.R;
import com.ge.music.model.MusicModel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


public class PlayMusicService extends Service {

    private static final String CHANNEL_ID = "PlayMusicService";
    private MediaPlayer mediaPlayer;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createNotificationChannel();
        mediaPlayer = MediaPlayer.create(this, R.raw.testmp3);
//        try {
//            mediaPlayer.setDataSource(this,Uri.parse("http://music.163.com/song/media/outer/url?id=38592976.mp3"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "music";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        manager.createNotificationChannel(mChannel);
    }

    private Notification buildNotification(MusicModel musicModel){
        Bitmap artwork = null;
        try {
            artwork = Glide.with(this)
                    .asBitmap()
                    .load(musicModel.getUrl())
                    .into(500,500)
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.logo)
                .setLargeIcon(artwork)
                .setContentTitle(musicModel.getMusicName())
                .setContentText(musicModel.getSinger())
                .setWhen(System.currentTimeMillis())
                .build();
        return notification;
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
                    if (mediaPlayer.getCurrentPosition() >= 0 ){
                        mediaPlayer.start();
                    }else {
                        mediaPlayer.prepareAsync();
                        notificationManagerCompat.notify(hashCode(),buildNotification(musicModel));
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
