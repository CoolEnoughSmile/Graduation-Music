package com.ge.music.media;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.R;
import com.ge.music.model.MusicModel;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class PlayMusicService extends Service {

    public static final String CHANNEL_ID = "music_notification";
    private static final int NOTIFY_MODE_NONE = 0;
    private static final int NOTIFY_MODE_FOREGROUND = 1;
    private static final int NOTIFY_MODE_BACKGROUND = 2;

    private MediaPlayer mediaPlayer;
    private MusicBinder binder = new MusicBinder();
    private static final String ACTION_PLAY = "play";
    private Queue<MusicModel> musicModelQueue = new LinkedList<>();

    private NotificationManagerCompat notificationManager;


    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();
        mediaPlayer = MediaPlayer.create(this, R.raw.testmp3);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "GE-Music";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        manager.createNotificationChannel(mChannel);
    }

    private void updateNotification() {
        final int newNotifyMode;
        if (mediaPlayer.isPlaying()) {
            newNotifyMode = NOTIFY_MODE_FOREGROUND;
        } else {
            newNotifyMode = NOTIFY_MODE_BACKGROUND;
        }

        int notificationId = hashCode();
        if (mNotifyMode != newNotifyMode) {
            if (mNotifyMode == NOTIFY_MODE_FOREGROUND) {
                stopForeground(newNotifyMode == NOTIFY_MODE_NONE);
            } else if (newNotifyMode == NOTIFY_MODE_NONE) {
                notificationManager.cancel(notificationId);
            }
        }

        if (newNotifyMode == NOTIFY_MODE_FOREGROUND) {
            startForeground(notificationId, buildNotification());
        } else if (newNotifyMode == NOTIFY_MODE_BACKGROUND) {
            notificationManager.notify(notificationId, buildNotification());
        }
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        createNotification();
//        if (mediaPlayer != null) {
//            String action = intent.getStringExtra("action");
//            switch (action) {
//                case "stop":
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                    mediaPlayer.release();
//                    mediaPlayer = null;
//                    break;
//            }
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MusicBinder extends Binder {
        public PlayMusicService getService() {
            return PlayMusicService.this;
        }
    }

    public void start() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void next() {

    }

    public void addNewAndPlay(MusicModel musicModel) {
        LogUtils.d(musicModel);
        try {
            mediaPlayer.setDataSource(musicModel.getUrl());
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
//        stopForeground(true);
        super.onDestroy();
    }
}
