package com.recyclingsg.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondHandTabFragment extends Fragment {

    CheckBox cbSecondHand;
    public SecondHandTabFragment() {
        // Required empty public constructor
    }


    public boolean isCheck(){
        if (cbSecondHand.isChecked())
            return true;
        else return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_second_hand,container,false);
        cbSecondHand = (CheckBox) rootView.findViewById(R.id.checkbox_second_hand);
        return rootView;
    }

}
