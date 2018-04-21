package com.recyclingsg.app.boundary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.recyclingsg.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class eWasteTabFragment extends Fragment {

    CheckBox cbEWaste;
    public eWasteTabFragment() {
        // Required empty public constructor
    }

    public boolean isChecked(){
        if (cbEWaste.isChecked())
            return true;
        else
            return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_ewaste,container,false);
        cbEWaste = (CheckBox) rootView.findViewById(R.id.checkbox_eWaste);

        return rootView;
    }

}
