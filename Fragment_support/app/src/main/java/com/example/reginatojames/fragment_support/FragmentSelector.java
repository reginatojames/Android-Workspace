package com.example.reginatojames.fragment_support;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSelector extends Fragment{

    public interface IOnButtonSelected{
        public void onUpdateValue(String aCurrentValue);
    }
    private TextView mTextView;

    private static final String CURRENT_STRING = "stringa iniziale";
    private static final String START_VALUE = "stringa di partenza";
    public String mCurrentLAbel;

    private IOnButtonSelected mListener = new IOnButtonSelected() {
        @Override
        public void onUpdateValue(String aCurrentValue) {

        }
    };

    public static FragmentSelector getInstance(String startValue){
        FragmentSelector vFrag = new FragmentSelector();

        Bundle vBundle = new Bundle();
        vBundle.putString(START_VALUE, startValue);
        vFrag.setArguments(vBundle);

        return vFrag;
    }

    public static FragmentSelector getInstance(){
        return FragmentSelector.getInstance("---");
    }

    public FragmentSelector() {
        // Required empty public constructor
    }

    private void updateGui() {
        mTextView.setText(mCurrentLAbel);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IOnButtonSelected){
            mListener = (IOnButtonSelected)getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vView = inflater.inflate(R.layout.fragment_fragment_selector, container, false);

        if (savedInstanceState != null) {
            mCurrentLAbel = savedInstanceState.getString(CURRENT_STRING);
        } else {
            mCurrentLAbel = getArguments().getString(START_VALUE);
        }

        Button vBtnA = (Button) vView.findViewById(R.id.btn_a);
        Button vBtnB = (Button) vView.findViewById(R.id.btn_b);
        Button vBtnC = (Button) vView.findViewById(R.id.btn_c);

        mTextView = (TextView) vView.findViewById(R.id.textView);

        vBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentLAbel = "A";
                mListener.onUpdateValue(mCurrentLAbel);
                updateGui();
            }
        });

        vBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentLAbel = "B";
                mListener.onUpdateValue(mCurrentLAbel);
                updateGui();
            }
        });

        vBtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentLAbel = "C";
                mListener.onUpdateValue(mCurrentLAbel);
                updateGui();
            }
        });
        return vView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_STRING, mCurrentLAbel);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
