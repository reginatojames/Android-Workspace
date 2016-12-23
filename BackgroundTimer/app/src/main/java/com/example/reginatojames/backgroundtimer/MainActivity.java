package com.example.reginatojames.backgroundtimer;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String TIMERFRAG = "TEST-APP";
    static TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.time);

        TimerFragment vFrag = (TimerFragment)getSupportFragmentManager().findFragmentByTag(TIMERFRAG);
        if(vFrag == null){
            FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
            ftr.add(TimerFragment.getInstance(), TIMERFRAG);
            ftr.commit();
        }
    }

    public static void updateGUI(int aValue){
        txt.setText("" + aValue);
    }

}
