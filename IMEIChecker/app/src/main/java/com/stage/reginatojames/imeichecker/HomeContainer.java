package com.stage.reginatojames.imeichecker;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class HomeContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ViewPager v = (ViewPager)findViewById(R.id.pager);
        //fix per sostituire vecchia toolbar con toolbar tabbata
        setSupportActionBar(toolbar);

        TabsPagerAdapter tb = new TabsPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        v.setAdapter(tb);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(v);
    }
}
