package com.example.reginatojames.fragcalc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {


    private static final String FRAGMENT = "dispy";
    display cifra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragManager = getFragmentManager();
        cifra = (display) fragManager.findFragmentByTag(FRAGMENT);

        if(cifra == null){
            FragmentTransaction vTrans = fragManager.beginTransaction();
            cifra = new display();
            vTrans.add(R.id.dissing, cifra, FRAGMENT);
            vTrans.commit();
        }
    }
}
