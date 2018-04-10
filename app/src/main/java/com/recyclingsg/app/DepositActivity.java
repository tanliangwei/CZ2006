package com.recyclingsg.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by kelvin on 21/3/18.
 */

public class DepositActivity extends AppCompatActivity {
    private static final String TAG = "DepositActivity";
    Spinner trashTypeSpinner;
    ArrayAdapter<CharSequence> adapter;
    TextView trashCollectionPointText;
    EditText dateEditText;
    TextView unitText;
    EditText unitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_activity);

        initWasteTypeSpinner();
        initTexts();
    }

    public void initTexts(){
        trashCollectionPointText = (TextView) findViewById(R.id.trashCollectionPointText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        unitText = (TextView) findViewById(R.id.unitText);
        unitEditText = (EditText) findViewById(R.id.unitEditText);
        unitText.setVisibility(View.INVISIBLE);
        unitEditText.setVisibility(View.INVISIBLE);
    }

    public void initWasteTypeSpinner(){
        Log.d(TAG, "initWasteTypeSpinner: initialising Waste Type dropdown menu");

        Spinner spinner = (Spinner) findViewById(R.id.trashTypeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> mSpinnerAdapter = createSpinnerAdapter();
        // Specify the layout to use when the list of choices appears
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initialise values of Spinner
        for (String x : TrashInfo.typeOfTrash)
            mSpinnerAdapter.add(x);

        mSpinnerAdapter.add("Select Waste Type");

        // Apply the adapter to the spinner
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setSelection(mSpinnerAdapter.getCount());
        spinner.setOnItemSelectedListener(mWasteTypeSpinnerListener);

    }

    private ArrayAdapter<String> createSpinnerAdapter() {
        ArrayAdapter<String> mSpinnerAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1; // you dont display last item. It is used as hint.
            }

        };

        return mSpinnerAdapter;
    }

    private AdapterView.OnItemSelectedListener mWasteTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (id != parent.getCount()) {
//
                Object trashTypeSelected = parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Selected Waste Type: " + trashTypeSelected.toString(),
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onItemSelected: Selected " + id + ": " + trashTypeSelected.toString());

                if(id ==1 || id==0){
                    unitText.setVisibility(View.VISIBLE);
                    unitEditText.setVisibility(View.VISIBLE);
                }else if(id==2){
                    //call function to generate second spinner
                    generateSpinnerForCashForTrash();
                }else{
                    unitText.setVisibility(View.INVISIBLE);
                    unitEditText.setVisibility(View.INVISIBLE);
                }
            }
        }
        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }

    };

    public void generateSpinnerForCashForTrash(){
        Log.d("generate spinner cash for trash", "coooooool");
        int[] coordinates = new int[2];
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.constraintLayout);
        ConstraintLayout.LayoutParams params;
        params = new ConstraintLayout.LayoutParams(unitEditText.getWidth(), unitEditText.getHeight());

        unitEditText.getLocationOnScreen(coordinates);



        params.leftMargin = coordinates[0];
        params.topMargin = coordinates[1] + 60;
        Log.d("generate spinner cash for trash", "cooorinates"+coordinates[0]+"    "+coordinates[1]);
        Log.d("generate spinner cash for trash", "coooooool"+params.leftMargin+"    "+params.topMargin);

        Spinner cashForTrashSpinner = new Spinner(this);
        ArrayList<String> spinnerArray = new ArrayList<String>();
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        spinnerArray);
        adapter = ArrayAdapter.createFromResource(this, R.array.cashForTrashSubCategories, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        trashTypeSpinner.setAdapter(adapter);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        cashForTrashSpinner.setAdapter(adapter);
        cl.addView(cashForTrashSpinner, params);
        cashForTrashSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString() + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onClick_deposit_enter(View v){
        if(v.getId() == R.id.btn_deposit_enter){
            //Intent intent = new Intent(this, .class);
            //startActivity(intent);
            //TODO

        }
    }

}
