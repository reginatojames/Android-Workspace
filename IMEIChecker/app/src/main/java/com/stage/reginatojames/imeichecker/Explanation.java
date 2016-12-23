package com.stage.reginatojames.imeichecker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class Explanation extends Fragment {

    public Explanation() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vView = inflater.inflate(R.layout.fragment_explanation, container, false);

        return vView;
    }

}
