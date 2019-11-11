package com.bolla.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bolla.myapplication.fragments.AccountFragment;
import com.bolla.myapplication.fragments.AudioFragment;
import com.bolla.myapplication.fragments.ScreenFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountFragment();
            case 1:
                return new AudioFragment();
            case 2:
                return new ScreenFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Account";
            case 1:
                return "Audio";
            case 2:
                return "Screen";

        }
        return super.getPageTitle(position);
    }
}
