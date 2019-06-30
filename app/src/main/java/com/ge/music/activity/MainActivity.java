package com.ge.music.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ge.music.R;
import com.ge.music.adapter.TabLayoutPageAdapter;
import com.ge.music.base.BaseActivity;
import com.ge.music.base.BaseFragment;
import com.ge.music.base.GraduationEraMusic;
import com.ge.music.fragment.MusicFragment;
import com.ge.music.fragment.MusicMenuDialogFragment;
import com.ge.music.fragment.PlayingDialogFragment;
import com.ge.music.fragment.VideoFragment;
import com.ge.music.http.model.User;
import com.ge.music.media.MessageEvent;
import com.ge.music.media.MusicConstant;
import com.ge.music.media.PlayMusicService;
import com.ge.music.model.MusicModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutPageAdapter tabLayoutPageAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageView mainTabBarLeft;
    private ImageView mainTabBarRight;

    private ImageView avatorIv;
    private TextView nickNameTv;

    public View musicPlayController;
    private ImageView posterIv;
    private CheckBox playOrPauseBtn;
    private TextView musicNameTv;
    private TextView singerTv;

    private PlayingDialogFragment playingDialogFragment;
    private MusicMenuDialogFragment musicMenuDialogFragment;


    private MusicFragment musicFragment;

    private List<MusicModel> history = new ArrayList<>();
    public int currentMusicIndex = 0;
    private User user = GraduationEraMusic.getUser();

    public Handler serviceHandler;
    private ServiceConnection connection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayMusicService.MyBinder myBinder = (PlayMusicService.MyBinder) service;
            serviceHandler = myBinder.getMusicServiceHandler();


//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(MusicConstant.ACTION);
//            registerReceiver(broadcastReceiver,intentFilter);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceHandler = null;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        Intent intent = new Intent(this, PlayMusicService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
    }

    @Override
    protected void initView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayoutPageAdapter = new TabLayoutPageAdapter(getSupportFragmentManager(),prepareFragments());
        viewPager.setAdapter(tabLayoutPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);
//        tabLayout.getTabAt(1).select();

        playingDialogFragment = new PlayingDialogFragment();
        musicMenuDialogFragment = new MusicMenuDialogFragment();

        mainTabBarLeft = findViewById(R.id.main_tab_bar_left);
        mainTabBarRight = findViewById(R.id.main_tab_bar_right);

        mainTabBarLeft.setOnClickListener(this);
        mainTabBarRight.setOnClickListener(this);

        musicPlayController = findViewById(R.id.music_play_controller);
        musicPlayController.setOnClickListener(this);
        findViewById(R.id.music_menu_btn).setOnClickListener(this);

        findViewById(R.id.play_or_pause_btn).setOnClickListener(this);
        posterIv = findViewById(R.id.poster_iv);
        playOrPauseBtn = findViewById(R.id.play_or_pause_btn);
        musicNameTv = findViewById(R.id.music_name_tv);
        singerTv = findViewById(R.id.singer_tv);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_logout:
                    logout();
                    break;
                case R.id.nav_exit:
                    finish();
                    break;
            }
            return false;
        });

        avatorIv = navigationView.getHeaderView(0).findViewById(R.id.avator_iv);
        nickNameTv = navigationView.getHeaderView(0).findViewById(R.id.nickname_tv);
        nickNameTv.setText(user == null ? user.getPhone() : user.getUserName());
        navigationView.getMenu().findItem(R.id.nav_gender).setTitle(user.getGender().equals("m")?"男":(user.getGender().equals("f")?"女":user.getGender()));
//        http://img5.duitang.com/uploads/item/201506/07/20150607110911_kY5cP.jpeg
        Glide.with(this)
                .load(user.getAvator())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.ic_qq))
                .into(avatorIv);

        Glide.with(this)
                .load(R.mipmap.placeholder_music_poster)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(posterIv);
    }

    private BaseFragment[] prepareFragments() {
        BaseFragment[] fragments = new BaseFragment[2];
        musicFragment = new MusicFragment();
        fragments[0] = musicFragment;
        fragments[1] = new VideoFragment();
        return fragments;
    }

    private void logout(){
        SPUtils.getInstance().remove("user");
        GraduationEraMusic.setUser(null);
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
        finish();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isWhiteStatusBar() {
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        updateTabView(tab,true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        updateTabView(tab,false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private  void updateTabView(TabLayout.Tab tab,boolean isSelected){
        TextView title = (TextView)(((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(tab.getPosition())).getChildAt(1));
        if (isSelected){
            title.setTextAppearance(this, R.style.TabLayoutTextSelected);
        }else {
            title.setTextAppearance(this, R.style.TabLayoutTextNormal);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_tab_bar_left:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.main_tab_bar_right:
                break;
            case R.id.play_or_pause_btn:
                if (serviceHandler != null){
                    serviceHandler.sendEmptyMessage(MusicConstant.START_PAUSE);
                }
                break;
            case R.id.music_menu_btn:
                musicMenuDialogFragment.show(getSupportFragmentManager(),"MusicMenuDialogFragment");
                break;
            case R.id.music_play_controller:
//                playingDialogFragment.show(getSupportFragmentManager(),"PlayingDialogFragment");
                startActivity(new Intent(this,LrcActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(broadcastReceiver);
        EventBus.getDefault().unregister(this);
    }

    //监听手机的物理按键点击事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtils.startHomeActivity();
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getEvent()){
            case MusicConstant.MUSIC_CHANGE:
                MusicModel musicModel = (MusicModel) event.getData().get("musicModel");
                playOrPauseBtn.setChecked(true);
                Glide.with(this)
                        .load(musicModel.getPoster())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.placeholder_music_poster))
                        .into(posterIv);
                musicNameTv.setText(musicModel.getMusicName());
                singerTv.setText(musicModel.getSinger());
                break;
            case MusicConstant.PLAYER_ERROR:
                ToastUtils.showShort("链接失效");
                playOrPauseBtn.setChecked(false);
                break;
            case MusicConstant.COMPLETE:
                playOrPauseBtn.setChecked(false);
                playNext();
                break;
            case MusicConstant.PRE:
                playOrPauseBtn.setChecked(false);
                playLast();
                break;
            case MusicConstant.NEXT:
                playOrPauseBtn.setChecked(false);
                playNext();
                break;
            case MusicConstant.START_PAUSE:
                if (serviceHandler != null){
                    serviceHandler.sendEmptyMessage(MusicConstant.START_PAUSE);
                }
                playOrPauseBtn.setChecked(!playOrPauseBtn.isChecked());
                break;
        }
    }

    private void playNext() {
        if (currentMusicIndex < history.size()-1) {
            MusicModel music= history.get(++currentMusicIndex);
            Message message = serviceHandler.obtainMessage();
            message.what = MusicConstant.PLAY;
            message.obj = music;
            serviceHandler.sendMessage(message);
        }else{
            ToastUtils.showShort("没有更多");
        }
    }

    private void playLast() {
        if (currentMusicIndex >0) {
            MusicModel music= history.get(--currentMusicIndex);
            Message message = serviceHandler.obtainMessage();
            message.what = MusicConstant.PLAY;
            message.obj = music;
            serviceHandler.sendMessage(message);
        }else{
            ToastUtils.showShort("没有更多");
        }
    }

    public List<MusicModel> getHistory() {
        return history;
    }
}
