package com.example.reginatojames.contatore;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class triple extends AppCompatActivity {

    int counter;
    int secondcounter;
    TextView txt;
    TextView t;
    public static final String VALORE = "TEST";
    Button trip;
    Button quad;
    String s;
    public static final String CONTATORE="contatore_raddoppiato_molte_volte";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triple);

        Bundle vBundle = getIntent().getExtras();
        if(vBundle!=null){
            counter = vBundle.getInt(VALORE);
            txt = (TextView)findViewById(R.id.textView3);
            txt.setText("" + counter);
            secondcounter = counter;
        }

        if( savedInstanceState!=null ){
            secondcounter = savedInstanceState.getInt(CONTATORE);
            t.setText("" + secondcounter);
        }

        t = (TextView)findViewById(R.id.textView4);

        trip = (Button) findViewById(R.id.button3);
        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondcounter = secondcounter * 3;
                t.setText("" + secondcounter);
            }
        });

        quad = (Button) findViewById(R.id.button4);
        quad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondcounter = secondcounter * 4;
                t.setText("" + secondcounter);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(CONTATORE, secondcounter);
    }
}
