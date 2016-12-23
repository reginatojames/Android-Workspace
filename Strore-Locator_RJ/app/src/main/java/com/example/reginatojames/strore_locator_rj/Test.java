package com.example.reginatojames.strore_locator_rj;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;

public class Test extends FragmentActivity {
    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        Tab = (ViewPager)findViewById(R.id.pager);
        Tab.setAdapter(TabAdapter);

        //Enable Tabs on Action Bar
    }

}