package com.example.reginatojames.dialoglession;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyDialog extends DialogFragment {

    private final static String MESSAGGIO = "BUTTON";

    public static MyDialog getInstance(String messaggio){
        MyDialog myDialog = new MyDialog();
        Bundle vBundle = new Bundle();
        vBundle.putString(MESSAGGIO, messaggio);
        myDialog.setArguments(vBundle);
        return  myDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString(MESSAGGIO))
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity)getActivity()).setDecision("TASTO PREMUTO");
                    }
                })
                .setNegativeButton(R.string.nega, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MainActivity)getActivity()).setDecision("TASTO FANTASMA");
                    }
                });

        return builder.create();
    }
}
