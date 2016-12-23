package com.stage.reginatojames.cgichecker;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import cz.msebera.android.httpclient.Header;
import static com.stage.reginatojames.cgichecker.GetPhoneCGI.returnCGIinfos;

public class HomeActivity extends AppCompatActivity {

    private final static String LAT = "latuitude", LONG = "longitude", CGI = "cgi", SIGNALSTRENGTH = "signal", ITEM = "item";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private GetPhoneCGI phoneInfos;
    private LocationManager locationManager;
    private Animation animationFadeIn, animationFadeAway;
    private Button sendBtn, getCGIbtn;
    private LinearLayout layoutLabels, layoutMCC, layoutMNC, layoutLAC, layoutCI;
    private TextView mccTxt, mncTxt, lacTxt, ciTxt, desc;
    private ImageView arrowF, arrowS, arrowT, arrowL;
    private boolean isShowing = false;
    private int arrowShowing = 1, signalStrengthCDMA, signal;
    private String latitude, longitude, position, cgi;

    TelephonyManager telephonManager;
    myPhoneStateListener pslistener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sendBtn = (Button) findViewById(R.id.sendInfoBtn);
        getCGIbtn = (Button) findViewById(R.id.cgiCheckBtn);
        layoutLabels = (LinearLayout) findViewById(R.id.layoutLabels);
        layoutMCC = (LinearLayout) findViewById(R.id.layoutMCC);
        layoutMNC = (LinearLayout) findViewById(R.id.layoutMNC);
        layoutLAC = (LinearLayout) findViewById(R.id.layoutLAC);
        layoutCI = (LinearLayout) findViewById(R.id.layoutCI);
        mccTxt = (TextView) findViewById(R.id.mccTxt);
        mncTxt = (TextView) findViewById(R.id.mncTxt);
        lacTxt = (TextView) findViewById(R.id.lacTxt);
        ciTxt = (TextView) findViewById(R.id.ciTxt);
        desc = (TextView) findViewById(R.id.description);
        arrowF = (ImageView) findViewById(R.id.firstArrow);
        arrowS = (ImageView) findViewById(R.id.secondArrow);
        arrowT = (ImageView) findViewById(R.id.thirdArrow);
        arrowL = (ImageView) findViewById(R.id.lastArrow);

        try {
            pslistener = new myPhoneStateListener();
            telephonManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            telephonManager.listen(pslistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        animationFadeAway = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        //per ciclo di vita
        if(savedInstanceState != null){
            latitude = savedInstanceState.getString(LAT);
            longitude = savedInstanceState.getString(LONG);
            cgi = savedInstanceState.getString(CGI);
            phoneInfos = savedInstanceState.getParcelable(ITEM);
            signalStrengthCDMA = savedInstanceState.getInt(SIGNALSTRENGTH);
            if(phoneInfos != null){
                sendBtn.setVisibility(View.VISIBLE);
                displayCgi();
            }
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);

                //setta messaggio dialogo
                alertDialogBuilder
                        .setMessage(R.string.alert_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_positive, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                getUserLocation();
                                sendData();
                            }
                        })
                        .setNegativeButton(R.string.alert_negative, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //crea messaggio dialogo
                AlertDialog alertDialog = alertDialogBuilder.create();

                //mostra dialogo
                alertDialog.show();
            }
        });

        getCGIbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowing) {
                    isShowing = true;

                    int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_PHONE_STATE);
                    if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(HomeActivity.this, R.string.permission_explanation, Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    } else {
                        getTheCGI();
                    }
                }
            }
        });

        layoutMCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrowF.setVisibility(View.VISIBLE);
                arrowF.startAnimation(animationFadeIn);

                switch (arrowShowing) {
                    case 2:
                        arrowS.setVisibility(View.INVISIBLE);
                        arrowS.startAnimation(animationFadeAway);
                        break;
                    case 3:
                        arrowT.setVisibility(View.INVISIBLE);
                        arrowT.startAnimation(animationFadeAway);
                        break;
                    case 4:
                        arrowL.setVisibility(View.INVISIBLE);
                        arrowL.startAnimation(animationFadeAway);
                        break;
                }
                desc.startAnimation(animationFadeIn);
                desc.setText(R.string.mccDesc);
                arrowShowing = 1;
            }
        });

        layoutMNC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrowS.setVisibility(View.VISIBLE);
                arrowS.startAnimation(animationFadeIn);

                switch (arrowShowing) {
                    case 1:
                        arrowF.setVisibility(View.INVISIBLE);
                        arrowF.startAnimation(animationFadeAway);
                        break;
                    case 3:
                        arrowT.setVisibility(View.INVISIBLE);
                        arrowT.startAnimation(animationFadeAway);
                        break;
                    case 4:
                        arrowL.setVisibility(View.INVISIBLE);
                        arrowL.startAnimation(animationFadeAway);
                        break;
                }
                desc.startAnimation(animationFadeIn);
                desc.setText(R.string.mncDesc);
                arrowShowing = 2;
            }
        });

        layoutLAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arrowT.setVisibility(View.VISIBLE);
                arrowT.startAnimation(animationFadeIn);

                switch (arrowShowing) {
                    case 1:
                        arrowF.setVisibility(View.INVISIBLE);
                        arrowF.startAnimation(animationFadeAway);
                        break;
                    case 2:
                        arrowS.setVisibility(View.INVISIBLE);
                        arrowS.startAnimation(animationFadeAway);
                        break;
                    case 4:
                        arrowL.setVisibility(View.INVISIBLE);
                        arrowL.startAnimation(animationFadeAway);
                        break;
                }
                desc.startAnimation(animationFadeIn);
                desc.setText(R.string.lacDesc);
                arrowShowing = 3;
            }
        });

        layoutCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrowL.setVisibility(View.VISIBLE);
                arrowL.startAnimation(animationFadeIn);

                switch (arrowShowing) {
                    case 1:
                        arrowF.setVisibility(View.INVISIBLE);
                        arrowF.startAnimation(animationFadeAway);
                        break;
                    case 3:
                        arrowT.setVisibility(View.INVISIBLE);
                        arrowT.startAnimation(animationFadeAway);
                        break;
                    case 2:
                        arrowS.setVisibility(View.INVISIBLE);
                        arrowS.startAnimation(animationFadeAway);
                        break;
                }
                desc.startAnimation(animationFadeIn);
                desc.setText(R.string.ciDesc);
                arrowShowing = 4;
            }
        });
    }

    private String getUserLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            longitude = String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude());
            latitude = String.valueOf(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude());
            position = "LATITUDE: " + latitude + " " + "LONGITUDE: " + longitude;
        }
        return position;
    }

    private void displayCgi() {
        mccTxt.setText(cgi.substring(0, 3));
        mncTxt.setText(cgi.substring(3, 5));
        lacTxt.setText(cgi.substring(5, 10));
        ciTxt.setText(cgi.substring(10, 15));
        layoutLabels.setVisibility(View.VISIBLE);
        layoutLabels.startAnimation(animationFadeIn);
        arrowF.setVisibility(View.VISIBLE);
        arrowF.startAnimation(animationFadeIn);
        desc.setText(R.string.mccDesc);
        desc.setVisibility(View.VISIBLE);
        desc.startAnimation(animationFadeIn);
    }

    private void getTheCGI() {

        phoneInfos = new GetPhoneCGI();
        phoneInfos = returnCGIinfos(this);
        cgi = phoneInfos.cgi;

        if (cgi.length() < 10) {
            Toast.makeText(HomeActivity.this, R.string.connection_error, Toast.LENGTH_SHORT).show();
            isShowing = false;
        } else {
            sendBtn.setVisibility(View.VISIBLE);
            desc.setVisibility(View.VISIBLE);
            arrowF.setVisibility(View.VISIBLE);
            displayCgi();
        }
    }

    private void sendData(){

        android.text.format.Time time = new android.text.format.Time();
        time.setToNow();
        long _time = time.toMillis(false);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        try {

            params.add("cgi", phoneInfos.cgi);
            params.add("mcc", phoneInfos.mcc);
            params.add("mnc", phoneInfos.mnc);
            params.add("lac", phoneInfos.lac);
            params.add("ci", phoneInfos.ci);
            params.add("time", Long.toString(_time));
            params.add("signalstrength", String.valueOf(signal));
            params.add("latitude", latitude);
            params.add("longitude", longitude);

            String preSha1Checksum = phoneInfos.mcc.concat(phoneInfos.mnc).concat(Long.toString(_time % 57));
            String checksum = AeSimpleSHA1.SHA1(preSha1Checksum);
            params.add("checksum", checksum);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        asyncHttpClient.post("http://androidservice.devfarm.it/add_cgi/cgi_service.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    //in caso di risposta da server sosituire true con con boolean della risposta
                    if (true) {
                        Toast.makeText(HomeActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HomeActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this, R.string.error + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Toast.makeText(HomeActivity.this, R.string.error + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLastPermission(){
        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(permissions[0].contains("PHONE")){
                        getLastPermission();
                    }else if(permissions[0].contains("FINE")){
                        getUserLocation();
                    }else{
                        getTheCGI();
                    }
                } else {
                    // Permesso negato
                    Toast.makeText(HomeActivity.this, R.string.no_permission, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //per intensità segnale
    class myPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);

            signal = signalStrength.getCdmaDbm();
            //signal = (2 * signal) - 113; // -> dBm CONVERSIONE
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(latitude != null){
            outState.putString(LAT, latitude);
            outState.putString(LONG, longitude);
        }else if(phoneInfos !=null){
            outState.putParcelable(ITEM, phoneInfos);
            outState.putString(CGI, cgi);
            outState.putInt(SIGNALSTRENGTH, signalStrengthCDMA);
        }
    }
}