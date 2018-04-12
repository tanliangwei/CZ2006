package com.recyclingsg.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kelvin on 21/3/18.
 */

public class DepositActivity extends Activity {
    private static final String TAG = "DepositActivity";
    ArrayAdapter<CharSequence> adapter;
    TextView trashCollectionPointText;
    TextView dateText;
    Spinner trashTypeSpinner;
    TextView unitText;
    EditText unitEditText;
    TextView perUnitText;
    Spinner subTrashSpinner;
    CardView subTrashCardView;
    CardView unitsCardView;
    CardView trashTypeCardView;
    RelativeLayout rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_activity_2);

        TrashCollectionPointManager.getInstance();
        initViews();
        initialiseDateButtons();
        initWasteTypeSpinner();
    }

    public void initViews(){
        rl = (RelativeLayout) findViewById(R.id.depositRelativeLayout);
        trashCollectionPointText = (TextView) findViewById(R.id.textViewName2);
        trashCollectionPointText.setText(TrashCollectionPointManager.getUserSelectedTrashCollectionPoint().getCollectionPointName());
        dateText = (TextView) findViewById(R.id.textViewDate2);
        unitText = (TextView) findViewById(R.id.textViewUnit2);
        unitEditText = (EditText) findViewById(R.id.unitEditText);
        perUnitText = (TextView) findViewById(R.id.textViewUnit2);
        trashTypeSpinner = (Spinner) findViewById(R.id.trashTypeSpinner);
        subTrashSpinner = (Spinner) findViewById(R.id.subTrashTypeSpinner);
        subTrashCardView = (CardView) findViewById(R.id.cardViewSubTrash);
        trashTypeCardView = (CardView) findViewById(R.id.cardViewTrashType);
        unitsCardView = (CardView) findViewById(R.id.cardViewUnit);

        subTrashCardView.setVisibility(View.INVISIBLE);
        subTrashCardView.setClickable(false);

    }

    public void initialiseDateButtons(){

        Calendar mCurrentDate;
        mCurrentDate = Calendar.getInstance();
        int curDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        int curMonth = mCurrentDate.get(Calendar.MONTH);
        int curYear= mCurrentDate.get(Calendar.YEAR);
        dateText.setText(curDay+"/"+curMonth+"/"+curYear);
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("generate ", "DATE DIALOG");
                Calendar mCurrentDate;
                mCurrentDate = Calendar.getInstance();
                int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                int month = mCurrentDate.get(Calendar.MONTH);
                int year= mCurrentDate.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(DepositActivity.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                        monthOfYear = monthOfYear+1;
                        dateText.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                    }
                }, year,month,day);
                datePickerDialog.show();
            }
        });
    }

    public void initWasteTypeSpinner(){
        TrashCollectionPoint tcp = TrashCollectionPointManager.getUserSelectedTrashCollectionPoint();
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> mSpinnerAdapter = createSpinnerAdapter();
        // Specify the layout to use when the list of choices appears
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initialise values of Spinner
        if (tcp.getTrash().size()>0){
            mSpinnerAdapter.add(tcp.getTrash().get(0).getTrashType());
            for (TrashInfo x : tcp.getTrash()){
                String Temp = x.getTrashType();
                Log.d("INITIALISE SPINNER","THROUGH ARRAY " + Temp);
                mSpinnerAdapter.add(Temp);
            }
        } else{
            Log.d("INITIALISE SPINNER","THROUGH DEFAULT");
                for (String x : TrashInfo.typeOfTrash)
                    mSpinnerAdapter.add(x);
            }

        // Apply the adapter to the spinner
        //Log.d("SIZE OF ADAPTER","IS "+mSpinnerAdapter.getCount());
        trashTypeSpinner.setAdapter(mSpinnerAdapter);
        trashTypeSpinner.setSelection(mSpinnerAdapter.getCount());
        trashTypeSpinner.setOnItemSelectedListener(mWasteTypeSpinnerListener);
        Log.e("SELECTED","VALUES"+trashTypeSpinner.getSelectedItem().toString());

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

                if(trashTypeSelected.toString().equalsIgnoreCase("cash-for-trash") || trashTypeSelected.toString().equalsIgnoreCase("cash for trash") ){
                    generateSpinnerForCashForTrash();
                }else{
                    unitText.setVisibility(View.VISIBLE);
                    unitEditText.setVisibility(View.VISIBLE);
                }
            }
        }
        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }

    };

    private void generateSpinnerForCashForTrash(){

        RelativeLayout.LayoutParams subTrashCardParams = (RelativeLayout.LayoutParams) subTrashCardView.getLayoutParams();
        subTrashCardParams.removeRule(RelativeLayout.BELOW);
        subTrashCardParams.addRule(RelativeLayout.BELOW,R.id.cardViewTrashType);
        subTrashCardView.setVisibility(View.VISIBLE);
        subTrashCardView.setClickable(true);

        RelativeLayout.LayoutParams unitsCardParams = (RelativeLayout.LayoutParams) unitsCardView.getLayoutParams();
        unitsCardParams.removeRule(RelativeLayout.BELOW);
        unitsCardParams.addRule(RelativeLayout.BELOW,R.id.cardViewSubTrash);

        TrashCollectionPointManager.getInstance();
        TrashCollectionPoint tcp = TrashCollectionPointManager.getUserSelectedTrashCollectionPoint();
        int index = 0;
        for(int i =0;i<tcp.getTrash().size();i++){
            if (tcp.getTrash().get(i).getTrashType().equalsIgnoreCase("cash-for-trash")){
                index = i;
                break;
            }
        }
        Log.d("INITIALISE CFT SPINNER", "THROUGH ARRAY " + tcp.getTrash().get(index).getTrashType() + " "+ tcp.getTrash().get(index).getPriceInfoList().size());
        ArrayList<String> spinnerArray = new ArrayList<String>();
        ArrayAdapter<String> spinnerArrayAdapter = createSpinnerAdapter();
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (tcp.getTrash().get(index).getPriceInfoList().size()>0) {
            spinnerArrayAdapter.add(tcp.getTrash().get(index).getPriceInfoList().get(0).getTrashName());
            for (PriceInfo x : tcp.getTrash().get(index).getPriceInfoList()) {
                String Temp = x.getTrashName();
                Log.d("INITIALISE CFT SPINNER", "THROUGH ARRAY " + Temp);
                spinnerArrayAdapter.add(Temp);
            }
        }
        //adapter = ArrayAdapter.createFromResource(this, R.array.cashForTrashSubCategories, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subTrashSpinner.setAdapter(spinnerArrayAdapter);
        subTrashSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i).toString() + " is selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void removeSpinnerForCashForTrash(){
        RelativeLayout.LayoutParams unitsCardParams = (RelativeLayout.LayoutParams) unitsCardView.getLayoutParams();
        unitsCardParams.removeRule(RelativeLayout.BELOW);
        unitsCardParams.addRule(RelativeLayout.BELOW,R.id.cardViewTrashType);

        RelativeLayout.LayoutParams subTrashCardParams = (RelativeLayout.LayoutParams) subTrashCardView.getLayoutParams();
        subTrashCardParams.removeRule(RelativeLayout.BELOW);
        subTrashCardParams.addRule(RelativeLayout.BELOW,R.id.cardViewUnit);
        subTrashCardView.setVisibility(View.INVISIBLE);
        subTrashCardView.setClickable(false);
    }

    public void onClick_deposit_enter(View v){
        if(v.getId() == R.id.btn_deposit_enter){
            Log.d("on_click deposit enter", "Enter button clicked");
            //String trashType, ArrayList<String> cashForTrashNames, ArrayList<String> CashForTrashUnits, ArrayList<Double> cashForTrashPrices)
            //public static void createDepositRecord(TrashInfo trashInfo, float units, Date date, TrashCollectionPoint trashCollectionPoint)
            //creating the new trash info
            TrashCollectionPointManager.getInstance();
            TrashCollectionPoint currentTCP = TrashCollectionPointManager.getUserSelectedTrashCollectionPoint();
            ArrayList<String> CashForTrashUnits=new ArrayList<String>();
            ArrayList<String> CashForTrashNames=new ArrayList<String>();
            ArrayList<Double> CashForTrashPrices=new ArrayList<Double>();
            //get user selected trash
            String trashType = trashTypeSpinner.getSelectedItem().toString();
            //cash for trash nameseee
            if(subTrashSpinner!=null){
                String trashName = subTrashSpinner.getSelectedItem().toString();
                TrashCollectionPoint tcp = TrashCollectionPointManager.getUserSelectedTrashCollectionPoint();
                int index = 0;
                for(int i =0;i<tcp.getTrash().size();i++){
                    if (tcp.getTrash().get(i).getTrashType().equalsIgnoreCase("cash-for-trash")){
                        index = i;
                        break;
                    }
                }
                // getting the appropriate trash info
                TrashInfo temp = tcp.getTrash().get(index);
                for(int i =0;i<temp.getPriceInfoList().size();i++){
                    if (temp.getPriceInfoList().get(i).getTrashName().equalsIgnoreCase(trashName)){
                        index = i;
                        break;
                    }
                }
                CashForTrashUnits.add(temp.getPriceInfoList().get(index).getUnit());
                CashForTrashNames.add(temp.getPriceInfoList().get(index).getTrashName());
                CashForTrashPrices.add(temp.getPriceInfoList().get(index).getPricePerUnit());
            }

            TrashInfo depositTrash = new TrashInfo(trashType,CashForTrashNames,CashForTrashUnits,CashForTrashPrices);
            float units = Float.valueOf(unitEditText.getText().toString());
            DepositManager.getInstance();
            DepositManager.createDepositRecord(depositTrash,units,new Date(),currentTCP);

            Intent intent = new Intent(DepositActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

}
