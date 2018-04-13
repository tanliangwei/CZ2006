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
public class CashForTrashTabFragment extends Fragment  {


    public CashForTrashTabFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_cashfortrash,container,false);

        CheckBox cbAluminium = (CheckBox) getActivity().findViewById(R.id.checkbox_aluminium_drink_cans);
        CheckBox cbMetalTin = (CheckBox) getActivity().findViewById(R.id.checkbox_metal_tins_checkbox);
        CheckBox cbPapers = (CheckBox) getActivity().findViewById(R.id.checkbox_papers_checkbox);
        CheckBox cbOldClothing = (CheckBox) getActivity().findViewById(R.id.checkbox_old_clothing);
        CheckBox cbSmallAppliances = (CheckBox) getActivity().findViewById(R.id.checkbox_small_appliances);

        View.OnClickListener checkBoxListener = checkBoxListener();
        cbAluminium.setOnClickListener(checkBoxListener);
        cbMetalTin.setOnClickListener(checkBoxListener);
        cbPapers.setOnClickListener(checkBoxListener);
        cbOldClothing.setOnClickListener(checkBoxListener);
        cbSmallAppliances.setOnClickListener(checkBoxListener);

        return rootView;
    }

    public View.OnClickListener checkBoxListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((CheckBox) view).isChecked();

                // Check which checkbox was clicked
                switch (view.getId()) {
                    case R.id.checkbox_aluminium_drink_cans:
                        if (checked) break;
                            // Put some meat on the sandwich
                        else
                            // Remove the meat
                            break;
                    case R.id.checkbox_metal_tins_checkbox:
                        if (checked) break;
                            // Cheese me
                        else
                            // I'm lactose intolerant
                            break;

                    case R.id.checkbox_papers_checkbox:
                        if (checked) break;
                            // Cheese me
                        else
                            // I'm lactose intolerant
                            break;

                    case R.id.checkbox_old_clothing:
                        if (checked) break;
                            // Cheese me
                        else
                            // I'm lactose intolerant
                            break;

                    case R.id.checkbox_small_appliances:
                        if (checked) break;
                            // Cheese me
                        else
                            // I'm lactose intolerant
                            break;
                }
            }

        };
    }



}
