package com.br.gamifit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.br.gamifit.fragment.FeedSocialFragment;
import com.br.gamifit.fragment.ProfileListFragment;
import com.br.gamifit.fragment.UsersSocialFragment;

public class ProfilePageAdapter extends FragmentPagerAdapter {
    private int numTabs;

    public ProfilePageAdapter(FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new UsersSocialFragment();
            case 1:
                return new FeedSocialFragment();
            case 2:
                return new ProfileListFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
