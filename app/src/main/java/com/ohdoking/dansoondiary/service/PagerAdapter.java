package com.ohdoking.dansoondiary.service;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ohdoking.dansoondiary.fragment.FragmentFour;
import com.ohdoking.dansoondiary.fragment.FragmentOne;
import com.ohdoking.dansoondiary.fragment.FragmentFive;
import com.ohdoking.dansoondiary.fragment.FragmentThree;
import com.ohdoking.dansoondiary.fragment.FragmentTwo;

public class PagerAdapter extends FragmentPagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        switch (arg0) {
            case 0:
                return new FragmentOne();
            case 1:
                return new FragmentTwo();
            case 2:
                return new FragmentThree();
            case 3:
                return new FragmentFour();
            case 4:
                return new FragmentFive();

            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 5;
    }


}