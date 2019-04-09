package com.ge.music.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.ge.music.base.BaseFragment;

public class TabLayoutPageAdapter extends FragmentPagerAdapter {

    private BaseFragment[] fragments;

    public TabLayoutPageAdapter(FragmentManager fm,@NonNull BaseFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("PageTitle:",fragments[position].getName());
        return fragments[position].getName();
    }

}
