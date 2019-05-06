package com.ge.music.media;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.model.MusicModel;

import java.io.IOException;


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

    private NotificationCompat.Builder buildNotificationBuilder(MusicModel musicModel){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.
                getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(musicModel.getMusicName())
                .setContentText(musicModel.getSinger())
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);
        return builder;
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
                    }
                }
                break;
            case "play":
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(musicModel.getUrl());
                    mediaPlayer.prepareAsync();
                    NotificationCompat.Builder builder = buildNotificationBuilder(musicModel);
                    Glide.with(this)
                            .asBitmap()
                            .load(musicModel.getPoster())
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    builder.setLargeIcon(resource);
                                    startForeground(hashCode(),builder.build());
                                }
                            });
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
        stopForeground(true);
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
