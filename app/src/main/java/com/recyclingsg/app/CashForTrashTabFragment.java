package com.recyclingsg.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.res.Configuration.KEYBOARD_12KEY;

/**
 * A simple {@link Fragment} subclass.
 */

public class CashForTrashTabFragment extends Fragment {

    private View mView;
    private static final String TAG="CashForTrashTabFragment";

    private int mPage;
    CheckBox cbCashForTrash;
    CheckBox cbAluminium;
    CheckBox cbMetalTin;
    CheckBox cbPapers;
    CheckBox cbOldClothing;
    CheckBox cbSmallAppliances;

    EditText etAluminium;
    EditText etMetalTin;
    EditText etPapers;
    EditText etOldClothing;

    TextView tvAluminium;
    TextView tvMetalTin;
    TextView tvPapers;
    TextView tvOldClothing;
    public static final String ARG_PAGE = "ARG_PAGE";

    CompoundButton.OnCheckedChangeListener checkBoxListener;

    public static CashForTrashTabFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        CashForTrashTabFragment fragment = new CashForTrashTabFragment();
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_layout_cashfortrash, container,false);
        mView = rootView;
        cbCashForTrash = (CheckBox) rootView.findViewById(R.id.checkbox_cashfortrash);
        cbAluminium = (CheckBox) rootView.findViewById(R.id.checkbox_aluminium_drink_cans);
        cbAluminium.setVisibility(View.INVISIBLE);
        etAluminium = (EditText) rootView.findViewById(R.id.price_aluminium_drink_cans);
        etAluminium.setVisibility(View.INVISIBLE);
        tvAluminium = (TextView) rootView.findViewById(R.id.unit_aluminium);
        tvAluminium.setVisibility(View.INVISIBLE);


        cbMetalTin = (CheckBox) rootView.findViewById(R.id.checkbox_metal_tins);
        cbMetalTin.setVisibility(View.INVISIBLE);
        etMetalTin= (EditText) rootView.findViewById(R.id.price_metal_tins);
        etMetalTin.setVisibility(View.INVISIBLE);
        tvMetalTin = (TextView) rootView.findViewById(R.id.unit_metal_tins);
        tvMetalTin.setVisibility(View.INVISIBLE);


        cbPapers = (CheckBox) rootView.findViewById(R.id.checkbox_papers);
        cbPapers.setVisibility(View.INVISIBLE);
        etPapers = (EditText) rootView.findViewById(R.id.price_papers);
        etPapers.setVisibility(View.INVISIBLE);
        tvPapers= (TextView) rootView.findViewById(R.id.unit_papers);
        tvPapers.setVisibility(View.INVISIBLE);


        cbOldClothing = (CheckBox) rootView.findViewById(R.id.checkbox_old_clothing);
        cbOldClothing.setVisibility(View.INVISIBLE);
        etOldClothing = (EditText) rootView.findViewById(R.id.price_old_clothing);
        etOldClothing.setVisibility(View.INVISIBLE);
        tvOldClothing= (TextView) rootView.findViewById(R.id.unit_old_clothing);
        tvOldClothing.setVisibility(View.INVISIBLE);


        cbSmallAppliances = (CheckBox) rootView.findViewById(R.id.checkbox_small_appliances);
        cbSmallAppliances.setVisibility(View.INVISIBLE);


        //createOnCheckedChangeListener();

        cbAluminium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });

        cbCashForTrash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });
        cbMetalTin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });
        cbPapers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });
        cbOldClothing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });
        cbSmallAppliances.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                respondOnClick(buttonView, isChecked);            }
        });

        etAluminium.setRawInputType(KEYBOARD_12KEY);
        etAluminium.addTextChangedListener( new PriceTextWatcher(etAluminium));
        etOldClothing.setRawInputType(KEYBOARD_12KEY);
        etOldClothing.addTextChangedListener( new PriceTextWatcher(etOldClothing));
        etPapers.setRawInputType(KEYBOARD_12KEY);
        etPapers.addTextChangedListener( new PriceTextWatcher(etPapers));
        etMetalTin.setRawInputType(KEYBOARD_12KEY);
        etMetalTin.addTextChangedListener( new PriceTextWatcher(etMetalTin));


        
        return rootView;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }




//    public void createOnCheckedChangeListener() {
//        Log.d(TAG, "createOnCheckedChangeListener: Just created a listener");
//        checkBoxListener = new CompoundButton.OnCheckedChangeListener() {
//
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
//                Log.d(TAG, "onCheckedChanged: a Button is clicked");
//                switch (compoundButton.getId()) {
//                    case R.id.checkbox_cashfortrash:
//                        if (checked) {
//                            Log.d(TAG, "onClick: Checked cft checkbox");
//                            cbAluminium.setVisibility(View.VISIBLE);
//                            cbMetalTin.setVisibility(View.VISIBLE);
//                            cbPapers.setVisibility(View.VISIBLE);
//                            cbOldClothing.setVisibility(View.VISIBLE);
//                            cbSmallAppliances.setVisibility(View.VISIBLE);
//                            break;
//                        } else {
//                            Log.d(TAG, "onClick: Unchecked cft checkbox");
//                            cbAluminium.setVisibility(View.INVISIBLE);
//                            cbMetalTin.setVisibility(View.INVISIBLE);
//                            cbPapers.setVisibility(View.INVISIBLE);
//                            cbOldClothing.setVisibility(View.INVISIBLE);
//                            cbSmallAppliances.setVisibility(View.INVISIBLE);
//                            break;
//                        }
//
//                    case R.id.checkbox_aluminium_drink_cans:
//                        if (checked) {
//                            tvAluminium.setVisibility(View.VISIBLE);
//                            etAluminium.setVisibility(View.VISIBLE);
//                            break;
//                        } else {
//                            tvAluminium.setVisibility(View.INVISIBLE);
//                            etAluminium.setVisibility(View.INVISIBLE);
//                            break;
//                        }
//                    case R.id.checkbox_metal_tins:
//                        if (checked) {
//                            tvMetalTin.setVisibility(View.VISIBLE);
//                            etMetalTin.setVisibility(View.VISIBLE);
//                            break;
//                        } else {
//                            tvMetalTin.setVisibility(View.INVISIBLE);
//                            etMetalTin.setVisibility(View.INVISIBLE);
//                            break;
//                        }
//
//                    case R.id.checkbox_papers:
//                        if (checked) {
//                            tvPapers.setVisibility(View.VISIBLE);
//                            etPapers.setVisibility(View.VISIBLE);
//                            break;
//                        } else {
//                            tvPapers.setVisibility(View.INVISIBLE);
//                            etPapers.setVisibility(View.INVISIBLE);
//                            break;
//                        }
//
//                    case R.id.checkbox_old_clothing:
//                        if (checked) {
//                            tvOldClothing.setVisibility(View.VISIBLE);
//                            etOldClothing.setVisibility(View.VISIBLE);
//                            break;
//                        } else {
//                            tvOldClothing.setVisibility(View.INVISIBLE);
//                            etOldClothing.setVisibility(View.INVISIBLE);
//                            break;
//                        }
//                    case R.id.checkbox_small_appliances:
//                        if (checked) break;
//
//                        else
//                            // I'm lactose intolerant
//                            break;
//
//
//                }
//
//
//            }
//        };
//    }


    public void respondOnClick(CompoundButton compoundButton, boolean checked) {
        Log.d(TAG, "onCheckedChanged: a Button is clicked");
        switch (compoundButton.getId()) {
            case R.id.checkbox_cashfortrash:
                if (checked) {
                    Log.d(TAG, "onClick: Checked cft checkbox");
                    cbAluminium.setVisibility(View.VISIBLE);
                    cbMetalTin.setVisibility(View.VISIBLE);
                    cbPapers.setVisibility(View.VISIBLE);
                    cbOldClothing.setVisibility(View.VISIBLE);
                    cbSmallAppliances.setVisibility(View.VISIBLE);
                    break;
                } else {
                    Log.d(TAG, "onClick: Unchecked cft checkbox");
                    cbAluminium.setVisibility(View.INVISIBLE);
                    cbMetalTin.setVisibility(View.INVISIBLE);
                    cbPapers.setVisibility(View.INVISIBLE);
                    cbOldClothing.setVisibility(View.INVISIBLE);
                    cbSmallAppliances.setVisibility(View.INVISIBLE);
                    break;
                }

            case R.id.checkbox_aluminium_drink_cans:
                if (checked) {
                    tvAluminium.setVisibility(View.VISIBLE);
                    etAluminium.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tvAluminium.setVisibility(View.INVISIBLE);
                    etAluminium.setVisibility(View.INVISIBLE);
                    break;
                }
            case R.id.checkbox_metal_tins:
                if (checked) {
                    tvMetalTin.setVisibility(View.VISIBLE);
                    etMetalTin.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tvMetalTin.setVisibility(View.INVISIBLE);
                    etMetalTin.setVisibility(View.INVISIBLE);
                    break;
                }

            case R.id.checkbox_papers:
                if (checked) {
                    tvPapers.setVisibility(View.VISIBLE);
                    etPapers.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tvPapers.setVisibility(View.INVISIBLE);
                    etPapers.setVisibility(View.INVISIBLE);
                    break;
                }

            case R.id.checkbox_old_clothing:
                if (checked) {
                    tvOldClothing.setVisibility(View.VISIBLE);
                    etOldClothing.setVisibility(View.VISIBLE);
                    break;
                } else {
                    tvOldClothing.setVisibility(View.INVISIBLE);
                    etOldClothing.setVisibility(View.INVISIBLE);
                    break;
                }
            case R.id.checkbox_small_appliances:
                if (checked) break;

                else
                    // I'm lactose intolerant
                    break;


        }

    }

    public boolean isChecked(){
        if ( cbCashForTrash.isChecked())
            return true;
        else return false;
    }

    private ArrayList<String> trashNames = new ArrayList<>();
    private ArrayList<Double> trashPrices = new ArrayList<>();
    private ArrayList<String> trashUnits = new ArrayList<>();

    public ArrayList<String> getTrashNames() {
        return trashNames;
    }

    public ArrayList<Double> getTrashPrices() {
        return trashPrices;
    }

    public ArrayList<String> getTrashUnits() {
        return trashUnits;
    }

    public void compileCFTposts(){
        if (cbAluminium.isChecked()) {
            Log.d(TAG, "compileCFTposts: add aluminium to lists");
            Double price = Double.parseDouble(etAluminium.getText().toString().substring(1));
            trashNames.add("Aluminium drink cans");
            trashPrices.add(price);
            trashUnits.add("$/kg");
        }
        if (cbMetalTin.isChecked()) {
            Double price = Double.parseDouble(etMetalTin.getText().toString().substring(1));
            trashNames.add("Metal Tins");
            trashPrices.add(price);
            trashUnits.add("$/kg");

        }
        if (cbOldClothing.isChecked()) {
            Double price = Double.parseDouble(etOldClothing.getText().toString().substring(1));
            trashNames.add("Old Clothing / bedsheet ");
            trashPrices.add(price);
            trashUnits.add("$/kg");

        }
        if (cbPapers.isChecked()) {
            Double price = Double.parseDouble(etPapers.getText().toString().substring(1));
            trashNames.add("Papers");
            trashPrices.add(price);
            trashUnits.add("$/kg");

        }
        if (cbSmallAppliances.isChecked()) {
            trashNames.add("Small Appliances");
            trashPrices.add((double) 0);
            trashUnits.add("Varies by article");
        }
    }

    class PriceTextWatcher implements TextWatcher{
            EditText editText;
        PriceTextWatcher(EditText editText){
            this.editText = editText;
        }

        DecimalFormat dec = new DecimalFormat("0.00");

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!charSequence.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                String userInput = "" + charSequence.toString().replaceAll("[^\\d]", "");
                if (userInput.length() > 0) {
                    Float in = Float.parseFloat(userInput);
                    float percen = in / 100;
                    editText.setText("$" + dec.format(percen));
                    editText.setSelection(editText.getText().length());
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }





}
