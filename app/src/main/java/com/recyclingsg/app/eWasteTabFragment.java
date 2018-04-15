package com.recyclingsg.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class eWasteTabFragment extends Fragment {


    public eWasteTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_ewaste,container,false);
        CheckBox cbEWaste = (CheckBox) rootView.findViewById(R.id.checkbox_eWaste);

        return rootView;
    }

}
