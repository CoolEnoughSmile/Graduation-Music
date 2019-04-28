package com.ge.music.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ge.music.R;
import com.ge.music.adapter.TabLayoutPageAdapter;
import com.ge.music.base.BaseActivity;
import com.ge.music.base.BaseFragment;
import com.ge.music.fragment.MusicFragment;
import com.ge.music.fragment.VideoFragment;
import com.ge.music.media.PlayMusicService;


public class MainActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutPageAdapter tabLayoutPageAdapter;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageView mainTabBarLeft;
    private ImageView mainTabBarRight;

    private ImageView avatorIv;

    private ImageView posterIv;

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

        mainTabBarLeft = findViewById(R.id.main_tab_bar_left);
        mainTabBarRight = findViewById(R.id.main_tab_bar_right);

        mainTabBarLeft.setOnClickListener(this);
        mainTabBarRight.setOnClickListener(this);

        findViewById(R.id.play_or_pause_btn).setOnClickListener(this);
        posterIv = findViewById(R.id.poster_iv);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_logout:
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                    break;
                case R.id.nav_exit:
                    finish();
                    break;
            }
            return false;
        });

        avatorIv = navigationView.getHeaderView(0).findViewById(R.id.avator_iv);
        Glide.with(this)
                .load("http://img5.duitang.com/uploads/item/201506/07/20150607110911_kY5cP.jpeg")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.ic_qq))
                .into(avatorIv);

        Glide.with(this)
                .load(R.raw.testjpg)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()).placeholder(R.mipmap.placeholder_music_poster))
                .into(posterIv);
    }

    private BaseFragment[] prepareFragments() {
        BaseFragment[] fragments = new BaseFragment[2];
        fragments[0] = new MusicFragment();
        fragments[1] = new VideoFragment();
        return fragments;
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
                Intent intent = new Intent(this, PlayMusicService.class);
                intent.putExtra("action","playOrPause");
                startService(intent);
                break;
        }
    }

    //监听手机的物理按键点击事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtils.startHomeActivity();
        }
        return false;
    }
}
