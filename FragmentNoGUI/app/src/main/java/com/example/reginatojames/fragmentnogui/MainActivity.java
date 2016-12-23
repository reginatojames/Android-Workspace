package com.example.reginatojames.fragmentnogui;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT = "current fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BlankFragment vFra  = (BlankFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT);
        if(vFra == null){
            vFra = BlankFragment.getInstance();
            FragmentTransaction ftr = getFragmentManager().beginTransaction();
//            FragmentTransaction = ftr.add(R.id.container, vFra, FRAGMENT);
//            ftr.commit();
        }
    }
}
