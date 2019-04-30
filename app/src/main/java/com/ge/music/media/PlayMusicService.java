package com.ge.music.media;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.ge.music.R;
import com.ge.music.model.MusicModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayMusicService extends Service {

    private MediaPlayer mediaPlayer;
    private MusicBinder binder = new MusicBinder();
    private static final String ACTION_PLAY = "play";
    private List<MusicModel> musicList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.testmp3);
//        mediaPlayer.set
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
        public PlayMusicService getService(){
            return PlayMusicService.this;
        }
    }

    public void start(){
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public void pause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    public void next(){

    }

    public void addNewAndPlay(MusicModel musicModel){
        LogUtils.d(musicModel);
        try {
            mediaPlayer.setDataSource(musicModel.getUrl());
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private void createNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel(manager,"musicService","musicService",NotificationManager.IMPORTANCE_DEFAULT);
        RemoteViews remoteView = new  RemoteViews(this.getPackageName(),R.layout.view_music_service);
        Intent intentPlay = new Intent(ACTION_PLAY);
        PendingIntent pIntentPlay = PendingIntent.getBroadcast(this.getApplicationContext(),
                REQUEST_CODE, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteView.setOnClickPendingIntent(R.id.poster_iv,pIntentPlay);
        Notification notification = new NotificationCompat.Builder(this,"musicService")
                .setContent(remoteView)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.logo)
                .build();
        startForeground(111,notification);
    }*/

   /* @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager manager,String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        manager.createNotificationChannel(channel);
    }*/

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
