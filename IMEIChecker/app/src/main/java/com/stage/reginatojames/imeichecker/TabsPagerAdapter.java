package com.stage.reginatojames.imeichecker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Fragment fragImeiChecker = new HomeActivity();
                return fragImeiChecker;
            case 1:
                Fragment fragLuhnExp = new Explanation();
                return fragLuhnExp;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2; //numero di tab
    }

    public CharSequence getPageTitle(int position) {
        String name = null;
        if (position==0) {
            name = mContext.getResources().getString(R.string.tab_IMEI_formule);
        }else if (position==1) {
            name = mContext.getResources().getString(R.string.tab_luhn_formule);
        }
        return name;
    }
}
