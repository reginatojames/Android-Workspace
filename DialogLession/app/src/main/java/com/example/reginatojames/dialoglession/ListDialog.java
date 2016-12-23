package com.example.reginatojames.dialoglession;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ListDialog extends DialogFragment {

    private final static String MESSAGGIO = "BUTTON";

    String[] s = {"caio","telo","mangoio"};

    public static ListDialog getInstance(String messaggio) {
        ListDialog myDialog = new ListDialog();
        Bundle vBundle = new Bundle();
        vBundle.putString(MESSAGGIO, messaggio);
        myDialog.setArguments(vBundle);
        return myDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getArguments().getString(MESSAGGIO))
                .setItems(s, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        ((MainActivity)getActivity()).setDecision(s[which]);
                    }
                });
        return builder.create();
    }
}
