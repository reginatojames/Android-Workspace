package com.example.reginatojames.backgroundtimer;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {

    private final static String TIMERFRAG = "TEST-APP";
    MyTimer mTimer;

    public static TimerFragment getInstance(){
        return new TimerFragment();
    }

    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TIMERFRAG, "OnDetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer.stop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TIMERFRAG, "OnAttach");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TIMERFRAG, "OnResume");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mTimer = new MyTimer(this);
        Thread vth = new Thread (mTimer);
        vth.start();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TIMERFRAG, "OnViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TIMERFRAG, "OnStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TIMERFRAG, "OnPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TIMERFRAG, "OnStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return null;
    }

    public void onTimerValue(int aValue){
        Log.d(TIMERFRAG, "onTimerValue: "+ aValue);
    }

    private static class MyTimer implements Runnable{
        WeakReference<TimerFragment> mRef;
        int mCounter;
        boolean mRunning;

        public MyTimer(TimerFragment aRef){
            mRef = new WeakReference<TimerFragment>(aRef);
            mRunning = true;
        }

        public void stop(){
            mRunning = false;
        }

        @Override
        public void run() {
            while(mRunning){
                if(mRef.get() != null){
                    mCounter++;
                    mRef.get().onTimerValue(mCounter);
                    MainActivity.updateGUI(mCounter);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
