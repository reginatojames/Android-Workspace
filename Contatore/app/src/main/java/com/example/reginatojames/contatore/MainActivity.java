package com.example.reginatojames.contatore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

        private TextView mEtichetta;
        private int mcounter;

    private static final String TAG = "TEST_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "M:onCreate");
        setContentView(R.layout.activity_main);

       /* if(savedInstanceState!=null){
            mcounter = savedInstanceState.getInt("MIO_CONTATORE");
        }*/

        mEtichetta = (TextView)findViewById(R.id.textView);

        Button btnPiu = (Button)findViewById(R.id.btn1);
        btnPiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcounter++;
                updateGUI();
            }
        });

        Button btnMeno = (Button)findViewById(R.id.btn2);
        btnMeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcounter--;
                updateGUI();
            }
        });

        updateGUI();

        Button btnNext = (Button)findViewById(R.id.btnext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Bundle vBundle = new Bundle();
                vBundle.putInt(DetailActivity.VALORE, mcounter);
                intent.putExtras(vBundle);

                startActivity(intent);
            }
        });
    }

    private void launchActivity(){
        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    private void updateGUI(){
        mEtichetta.setText(""+mcounter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "M:onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "M:onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "M:onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "M:onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "M:onStart");
    }
}
