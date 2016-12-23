package com.example.reginatojames.contatore;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends Activity {

    public DetailActivity() {
        super();
    }

    private static final String TAG = "TEST_ACTIVITY";
    public static final String VALORE = "TEST";
    public static final String CONTATORE="contatore_raddoppiato_molte_volte";
    int vCounter = 0;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "D:OnCREATE");
        setContentView(R.layout.activity_detail);

        Bundle vBundle = getIntent().getExtras();
        if(vBundle!=null){
            vCounter = vBundle.getInt(VALORE);
            txt = (TextView)findViewById(R.id.textView2);
            txt.setText("" + vCounter);
        }

        if( savedInstanceState!=null ){
            vCounter = savedInstanceState.getInt(CONTATORE);
        }



        Button vBtn = (Button)findViewById(R.id.button);
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vCounter = vCounter * 2;
                txt.setText("" + vCounter);
            }
        });

        Button vBtn2 = (Button)findViewById(R.id.button2);
        vBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);

                Bundle vBundle = new Bundle();
                vBundle.putInt(DetailActivity.VALORE, vCounter);
                intent.putExtras(vBundle);

                startActivity(intent);
            }
        });

        Button vBtn3 = (Button)findViewById(R.id.button5);
        vBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, triple.class);

                Bundle vBundle = new Bundle();
                vBundle.putInt(DetailActivity.VALORE, vCounter);
                intent.putExtras(vBundle);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, this+"D:OnSTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, this+"D:OnRESUME");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d(TAG, this+"S:onSavedInstanceState");
        outState.putInt(CONTATORE,vCounter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, this+"D:OnPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, this+"D:OnSTOP");
    }
}
