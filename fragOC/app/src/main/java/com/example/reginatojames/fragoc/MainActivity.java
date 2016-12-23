package com.example.reginatojames.fragoc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String TAG = "Activity";
    private static final String FRAGMENT = "primoFAGment";
    MyFragment mCounterFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        FragmentManager fragManager = getFragmentManager();
        mCounterFragment = (MyFragment) fragManager.findFragmentByTag(FRAGMENT);

        if(mCounterFragment == null){
            FragmentTransaction vTrans = fragManager.beginTransaction();
            mCounterFragment = new MyFragment();
            vTrans.add(R.id.container, mCounterFragment, FRAGMENT);
            vTrans.commit();
        }

        Button btnP = (Button)findViewById(R.id.button3);
        btnP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterFragment.inc();
            }
        });

        Button btnM = (Button)findViewById(R.id.button4);
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounterFragment.dec();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


}
