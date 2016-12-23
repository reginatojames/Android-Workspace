package com.stage.reginatojames.imeichecker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class TabsEnabler extends FragmentActivity {

    ViewPager tabs;
    TabsPagerAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tabs_enabler);

        tabsAdapter = new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        tabs = (ViewPager)findViewById(R.id.pager);
        tabs.setAdapter(tabsAdapter);
    }
}