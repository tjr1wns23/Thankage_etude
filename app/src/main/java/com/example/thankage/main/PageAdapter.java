package com.example.thankage.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.thankage.fragments.BlankFragment1;
import com.example.thankage.fragments.BlankFragment2;
import com.example.thankage.fragments.BlankFragment3;
import com.example.thankage.fragments.BlankFragment4;
import com.example.thankage.fragments.BlankFragment5;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mData;
    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);

        mData = new ArrayList<>();
        mData.add(new BlankFragment1());
        mData.add(new BlankFragment2());
        mData.add(new BlankFragment3());
        mData.add(new BlankFragment4());
        mData.add(new BlankFragment5());
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return (position+1) + "";
    }

    @Nullable
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return  mData.size();
    }
}
