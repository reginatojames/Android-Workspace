package com.example.reginatojames.fragment_support;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Reginato James on 22/04/2016.
 */
public class FirstDialog extends DialogFragment {

    public interface IOnDialogSelected{
        public void onButtonSelected(String a);
    }

    public static FirstDialog getInstance(){
        return new FirstDialog();
    }

    private IOnDialogSelected mListener = new IOnDialogSelected() {
        @Override
        public void onButtonSelected(String a) {

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder vBuilder = new AlertDialog.Builder(getActivity());
        vBuilder.setMessage("culo");

        vBuilder.setNegativeButton("Negative", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onButtonSelected("---");
            }
        });

        vBuilder.setPositiveButton("vai", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onButtonSelected("+++");
            }
        });

        vBuilder.setNeutralButton("stallo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onButtonSelected("cueo");
            }
        });

        return vBuilder.create();
    }
}
