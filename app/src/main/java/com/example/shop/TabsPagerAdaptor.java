package com.example.shop;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabsPagerAdaptor extends FragmentPagerAdapter {

    public TabsPagerAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new Fragment_2();
            case 1:
                return new Fragment_1();
            case 2:
                return new Fragment_3();
            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }


}