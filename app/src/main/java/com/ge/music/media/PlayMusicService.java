package com.ge.music.media;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.ge.music.R;

public class PlayMusicService extends Service {

    private MediaPlayer mediaPlayer;
    private static final String ACTION_PLAY = "play";
    private static final int REQUEST_CODE = 100;
    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.testmp3);
        mediaPlayer.setLooping(true);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        createNotification();
        if (mediaPlayer != null) {
            String action = intent.getStringExtra("action");
            switch (action) {
                case "playOrPause":
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                    }else {
                        mediaPlayer.start();
                    }
                    break;
                case "stop":
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
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
