package com.example.projek3.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.projek3.R;
import com.example.projek3.fragment.FollowersFragments;
import com.example.projek3.fragment.FollowingsFragments;

public class SteppagerAdapter extends FragmentPagerAdapter {
    private final Context secContext;

    public SteppagerAdapter(Context context, FragmentManager frgm) {
        super(frgm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        secContext = context;
    }

    @StringRes
    private final int[] TAB_TITLE = new int[]{
            R.string.followers,
            R.string.followings
    };

    @Nullable
    @Override
    public Fragment getItem(int positions) {
        Fragment fragments;
        if (positions == 0) {
            fragments = new FollowersFragments();
        } else {
            fragments = new FollowingsFragments();
        }
        return fragments;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return secContext.getResources().getString(TAB_TITLE[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }

}
