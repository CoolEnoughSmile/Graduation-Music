package com.ge.music.media;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ge.music.R;
import com.ge.music.activity.MainActivity;
import com.ge.music.model.MusicModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;


public class PlayMusicService extends Service {

    private static final String CHANNEL_ID = "PlayMusicService";
    private MyBinder myBinder = new MyBinder();
    private MusicPlayerManager musicPlayerManager;


    private MusicPlayerStatusChangeListener listener = new MusicPlayerStatusChangeListener() {
        @Override
        public void onMusicChange(MusicModel musicModel) {
            //开启或更新通知栏
            NotificationCompat.Builder builder = buildNotificationBuilder(musicModel);
            Glide.with(PlayMusicService.this)
                    .asBitmap()
                    .load(musicModel.getPoster())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            builder.setLargeIcon(resource);
                            startForeground(PlayMusicService.this.hashCode(),builder.build());
                        }
                    });
            //发送广播通知
//            Intent intent = new Intent(MusicConstant.ACTION);
//            intent.putExtra("musicModel",musicModel);
//            intent.putExtra("event",MusicConstant.MUSIC_CHANGE);
//            sendBroadcast(intent);
            Map<String,Object> map = new HashMap<>();
            map.put("musicModel",musicModel);
            map.put("duration",(int)musicPlayerManager.getDuration());
            MessageEvent messageEvent = new MessageEvent(MusicConstant.MUSIC_CHANGE,map);
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().postSticky(messageEvent);
            handler.post(runnable);
        }

        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int position = (int) musicPlayerManager.getCurrentPosition();
                Map<String, Object> m = new HashMap<>();
                m.put("progress", position);
                MessageEvent event = new MessageEvent(MusicConstant.PROGRESS, m);
                EventBus.getDefault().post(event);
                handler.postDelayed(this, 300);
            }
        };

        @Override
        public void onPlayerError() {
//            Intent intent = new Intent(MusicConstant.ACTION);
//            intent.putExtra("event",MusicConstant.PLAYER_ERROR);
//            sendBroadcast(intent);
            handler.removeCallbacks(runnable);
            EventBus.getDefault().post(new MessageEvent(MusicConstant.PLAYER_ERROR,null));
        }

        @Override
        public void onComplete() {
//            Intent intent = new Intent(MusicConstant.ACTION);
//            intent.putExtra("event",MusicConstant.COMPLETE);
//            sendBroadcast(intent);
            handler.removeCallbacks(runnable);
            EventBus.getDefault().post(new MessageEvent(MusicConstant.COMPLETE,null));
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MusicConstant.START_PAUSE:
                    musicPlayerManager.startOrPause();
                    break;
                case MusicConstant.PLAY:
                    MusicModel musicModel = (MusicModel) msg.obj;
                    musicPlayerManager.play(musicModel);
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        musicPlayerManager = new MusicPlayerManager(listener);
        EventBus.getDefault().register(this);
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "music";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            manager.createNotificationChannel(mChannel);
        }
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
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {
        public Handler getMusicServiceHandler(){
            return handler;
        }

        public boolean isPlaying(){
            return musicPlayerManager.isPlaying();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event){
        switch (event.getEvent()){
            case MusicConstant.SEEK_PROGRESS:
                musicPlayerManager.seekTo((Integer) event.getData().get("progress"));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
        musicPlayerManager.release();
        musicPlayerManager = null;
        EventBus.getDefault().unregister(this);
    }
}
