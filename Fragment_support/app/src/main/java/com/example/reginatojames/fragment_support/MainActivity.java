package com.example.reginatojames.fragment_support;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements FragmentSelector.IOnButtonSelected{

    private final static String FRAGMENT_TAG = "current fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG) == null){
            FragmentTransaction vTr = getSupportFragmentManager().beginTransaction();    //getSupportFragmentManager().beginTransaction().add(R.id.container, FragmentSelector.getInstance(),FRAGMENT_TAG).commit();
            vTr.add(R.id.container, FragmentSelector.getInstance(), FRAGMENT_TAG);
            vTr.commit();

        }
    }

    @Override
    public void onUpdateValue(String aCurrentValue) {
        /*FragmentTransaction vTrans = getSupportFragmentManager().beginTransaction();
        vTrans.replace(R.id.container, FragmentA.getIstanceOF());
        vTrans.addToBackStack(null);
        vTrans.commit();*/

        FirstDialog vDialog = FirstDialog.getInstance();
        vDialog.show(getSupportFragmentManager(),"DIALOG");
    }
}
