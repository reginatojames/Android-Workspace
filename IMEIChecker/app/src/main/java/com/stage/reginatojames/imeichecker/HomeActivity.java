package com.stage.reginatojames.imeichecker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.stage.reginatojames.imeichecker.PhoneInfoItem.getPhoneInfos;

public class HomeActivity extends Fragment {

    public HomeActivity(){

    }

    static final private String RETRIEVE_STEP = "step", ITEM = "item", COVERTEDARRAY = "converted array";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private TextView tacTxt, serialTxt, cdTxt, descriptionTxt, tacSecondTxt, serialSecondTxt, cdSecondTxt, customIMEI;
    private Button getImeiBtn, sendInfoBtn;
    private ImageView nextStepBtn;
    private LinearLayout layoutFirstStep, layoutSecondStep;
    private int step = 0, finalSum = 0, calculatedCheckDigit = 0, givenCheckDigit = 0;
    private static PhoneInfoItem item;
    private Animation animation, animationFadeAway;

    private String convertedArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.activity_home, container, false);

        customIMEI = (TextView) vView.findViewById(R.id.customIMEI);
        customIMEI.setBackgroundColor(Color.GRAY);
        nextStepBtn = (ImageView) vView.findViewById(R.id.nextStepBtn);
        getImeiBtn = (Button) vView.findViewById(R.id.automaticCheckBtn);
        sendInfoBtn = (Button) vView.findViewById(R.id.sendInfoBtn);
        tacTxt = (TextView) vView.findViewById(R.id.tacTxt);
        serialTxt = (TextView) vView.findViewById(R.id.serialTxt);
        cdTxt = (TextView) vView.findViewById(R.id.checkDigitTxt);
        tacSecondTxt = (TextView) vView.findViewById(R.id.tacSecondStepTxt);
        serialSecondTxt = (TextView) vView.findViewById(R.id.serialSecondStepTxt);
        cdSecondTxt = (TextView) vView.findViewById(R.id.checkDigitSecondStepTxt);
        descriptionTxt = (TextView) vView.findViewById(R.id.description);
        descriptionTxt.setMovementMethod(new ScrollingMovementMethod());
        layoutFirstStep = (LinearLayout) vView.findViewById(R.id.layoutLabels);
        layoutSecondStep = (LinearLayout) vView.findViewById(R.id.layoutSecondStep);

        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade);
        animationFadeAway = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeaway);

        //recupero dati dopo app mandata in background in caso di keepActivities = false
        if(savedInstanceState != null){
            step = savedInstanceState.getInt(RETRIEVE_STEP);
            item = savedInstanceState.getParcelable(ITEM);
            convertedArray = savedInstanceState.getString(COVERTEDARRAY);

            switch (step){
                case 1:
                    verifyIMEIfirstStep();
                    break;
                case 2:
                    loadUIFirst();
                    verifyIMEIsecondStep(item.imei);
                    break;
                case 3:
                    loadAllUI();
                    verifyIMEIthirdStep();
                    break;
                default:
                    break;
            }
        }

        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step++;
                if(step == 2)
                    verifyIMEIsecondStep(item.imei);
                else if(step == 3)
                    verifyIMEIthirdStep();
                else if(step > 3){
                    layoutFirstStep.startAnimation(animationFadeAway);
                    layoutSecondStep.startAnimation(animationFadeAway);
                    descriptionTxt.startAnimation(animationFadeAway);
                    sendInfoBtn.startAnimation(animationFadeAway);

                    layoutFirstStep.setVisibility(View.INVISIBLE);
                    layoutSecondStep.setVisibility(View.INVISIBLE);
                    descriptionTxt.setVisibility(View.INVISIBLE);
                    sendInfoBtn.setVisibility(View.INVISIBLE);
                    customIMEI.setText("");
                    customIMEI.setBackgroundColor(Color.GRAY);
                    step = 0;
                }
            }
        });

        getImeiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(step == 0){
                    step++;
                    //controllo permessi per accesso a READ_PHONE_STATE
                    int hasReadPhoneStatePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);
                    if (hasReadPhoneStatePermission != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), R.string.permission_explanation, Toast.LENGTH_LONG).show();
                        requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_ASK_PERMISSIONS);
                        return;
                    }else{
                        getIMEI();
                    }
                }
            }
        });

        sendInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                //setta messaggio dialogo
                alertDialogBuilder
                        .setMessage(R.string.alert_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.alert_positive ,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                sendIMEI();
                                //postPhoneInfos(getActivity(), true);
                            }
                        })
                        .setNegativeButton(R.string.alert_negative,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                //crea messaggio dialogo
                AlertDialog alertDialog = alertDialogBuilder.create();

                //mostra dialogo
                alertDialog.show();
            }
        });

        return vView;
    }

    private void loadUIFirst(){
        tacTxt.setText(item.tac);
        serialTxt.setText(item.imei.substring(8, 14));
        cdTxt.setText(item.imei.substring(14, 15));

        descriptionTxt.setText(R.string.firststep_explanation);

        layoutFirstStep.setVisibility(View.VISIBLE);
        descriptionTxt.setVisibility(View.VISIBLE);
    }

    private void loadAllUI(){
        tacTxt.setText(item.tac);
        serialTxt.setText(item.imei.substring(8, 14));
        cdTxt.setText(item.imei.substring(14, 15));
        layoutFirstStep.setVisibility(View.VISIBLE);
        descriptionTxt.setVisibility(View.VISIBLE);
        layoutSecondStep.setVisibility(View.VISIBLE);
        tacSecondTxt.setText(convertedArray.substring(0, 8));
        serialSecondTxt.setText(convertedArray.substring(8, 14));
        cdSecondTxt.setText(convertedArray.substring(14, 15));
        descriptionTxt.setText(R.string.secondstep_explanation);
    }

    private void getIMEI(){
        item = getPhoneInfos(getActivity());
        verifyIMEIfirstStep();
    }

    private void verifyIMEIfirstStep(){
        loadUIFirst();

        descriptionTxt.setText(R.string.firststep_explanation);

        layoutFirstStep.setVisibility(View.VISIBLE);
        descriptionTxt.setVisibility(View.VISIBLE);
        layoutFirstStep.startAnimation(animation);
        descriptionTxt.startAnimation(animation);
    }

    private void verifyIMEIsecondStep(String vIMEI){
        char[] vArray = vIMEI.toCharArray();
        int[] imeiArray = new int[15];

        int i = 0, vNumb = 0, vSum = 0;

        for(i = 0; i < vArray.length; i++){
            imeiArray[i] = Character.getNumericValue(vArray[i]);
        }

        //mi salvo il checkDigit
        givenCheckDigit = imeiArray[14];

        for(i = 0; i < 15; i++){
            if(i%2 != 0){ //controllo se in posizione dispari
                imeiArray[i] = imeiArray[i] * 2;
                if(imeiArray[i] >= 10) {
                    vNumb =  imeiArray[i] / 10;     //se trova due cifre le sommo tra di loro
                    vSum = vNumb + imeiArray[i]%10;
                    imeiArray[i] = vSum;
                }
            }
        }

        //somma di tutti gli elementi eccetto 'check digit'
        finalSum = 0;
        for(i = 0; i < 14; i++){
            finalSum += imeiArray[i];
        }

        StringBuilder builder = new StringBuilder();
        for (int j : imeiArray) {
            builder.append(j);
        }
        convertedArray = builder.toString();

        tacSecondTxt.setText(convertedArray.substring(0, 8));
        serialSecondTxt.setText(convertedArray.substring(8, 14));
        cdSecondTxt.setText(convertedArray.substring(14, 15));

        descriptionTxt.setText(R.string.secondstep_explanation);

        layoutSecondStep.setVisibility(View.VISIBLE);
        layoutSecondStep.startAnimation(animation);
        descriptionTxt.startAnimation(animation);
    }

    private void verifyIMEIthirdStep(){
        if(finalSum%10 == 0){
            calculatedCheckDigit = 0;
        }else{
            calculatedCheckDigit = 10 - (finalSum%10);
        }

        if(calculatedCheckDigit == givenCheckDigit){
            customIMEI.setText(R.string.output_forcode);
            customIMEI.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }else
            customIMEI.setText(R.string.wrong_imei);

        descriptionTxt.setText(R.string.thirdstep_explanation);
        descriptionTxt.startAnimation(animation);
        sendInfoBtn.setVisibility(View.VISIBLE);
    }

    public void sendIMEI() {

        android.text.format.Time time = new android.text.format.Time();
        time.setToNow();
        long _time = time.toMillis(false);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        try {
            params.add("imei", AeSimpleSHA1.SHA1(item.imei));
            params.add("tac", AeSimpleSHA1.SHA1(item.tac));
            params.add("manufacturer", item.manufacturer);
            params.add("model", item.model);
            params.add("product", item.product);
            params.add("time", Long.toString(_time));
            params.add("operatorName", item.operatorName);
            params.add("operatorCode", item.operatorCode);

            String preSha1Checksum = item.tac.concat(item.model).concat(Long.toString(_time % 57));
            String checksum = AeSimpleSHA1.SHA1(preSha1Checksum);
            params.add("checksum", checksum);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        asyncHttpClient.post("http://tac.devfarm.it/add/index.php", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {

                    //in caso server dia risposta
                    if (true) {
                        Toast.makeText(getActivity(), R.string.send_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.error + e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), R.string.connection_failed , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permesso dato
                    getIMEI();
                } else {
                    // Permesso negato
                    Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(RETRIEVE_STEP, step);
        if(item != null)
            outState.putParcelable(ITEM, item);
        if(convertedArray != null)
            outState.putString(COVERTEDARRAY, convertedArray);
    }
}