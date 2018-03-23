package com.recyclingsg.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.text.TextUtils;

/**
 * Created by kelvin on 21/3/18.
 */

public class DepositActivity extends AppCompatActivity {
    private static final String TAG = "DepositActivity";
    Spinner trashTypeSpinner;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_activity);

        Bundle b = getIntent().getExtras();
        String selectedCategory = "none"; // Category of trash selected
        if(b != null) {
            selectedCategory = b.getString("trashCategory");

            Log.d(TAG, "trashCategory is :" + selectedCategory);

            if (selectedCategory.equals("cash_for_trash")) {
                trashTypeSpinner = (Spinner) findViewById(R.id.trashTypeSpinner);
                adapter = ArrayAdapter.createFromResource(this, R.array.trashTypeName, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                trashTypeSpinner.setAdapter(adapter);

                trashTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString() + " is selected", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            } else if (selectedCategory.equals("eWaste")) {
                trashTypeSpinner = (Spinner) findViewById(R.id.trashTypeSpinner);
                adapter = ArrayAdapter.createFromResource(this, R.array.trashTypeName, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                trashTypeSpinner.setAdapter(adapter);

                trashTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString() + " is selected", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //Do nothing
                    }
                });
            } else { //(selectedCategory.equals("secondHand")
                trashTypeSpinner = (Spinner) findViewById(R.id.trashTypeSpinner);
                adapter = ArrayAdapter.createFromResource(this, R.array.trashTypeName, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                trashTypeSpinner.setAdapter(adapter);

                trashTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString() + " is selected", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        //Do nothing
                    }
                });
            }
        }

    }


    public void onClick_deposit_enter(View v){
        if(v.getId() == R.id.btn_deposit_enter){
            //Intent intent = new Intent(this, .class);
            //startActivity(intent);
            //TODO

        }
    }

}
