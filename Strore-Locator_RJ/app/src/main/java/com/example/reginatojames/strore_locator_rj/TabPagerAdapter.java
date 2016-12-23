package com.example.reginatojames.strore_locator_rj;

/**
 * Created by Reginato James on 20/04/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    String titles[]=new String[]{"cazzo", "pene", "culo"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Android Tab
                return new Android();
            case 1:
                //Fragment for Ios Tab
                return new Ios();
            case 2:
                //Fragment for Windows Tab
                return new Windows();
        }
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
