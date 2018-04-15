package com.recyclingsg.app;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    static EditText unitEditText;
    TextView perUnitText;
    Spinner subTrashSpinner;
    CardView subTrashCardView;
    CardView unitsCardView;
    CardView trashTypeCardView;
    RelativeLayout rl;
    static Button depositButton;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deposit_activity_2);

        //initialising context
        context = this.getApplicationContext();
        TrashCollectionPointManager.getInstance();
        initViews();
        initialiseDateButtons();
        initWasteTypeSpinner();
        initUnitEditText();
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
        depositButton = (Button) findViewById(R.id.depositButton);

        functionWhichRemovesKeyboardOnExternalTouch(rl);

        subTrashCardView.setVisibility(View.INVISIBLE);
        subTrashCardView.setClickable(false);
        depositButton.setVisibility(View.INVISIBLE);
        depositButton.setClickable(false);

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
            mSpinnerAdapter.add("Select Trash");
            for (TrashInfo x : tcp.getTrash()){
                String Temp = x.getTrashType();
                mSpinnerAdapter.add(Temp);
            }
        } else{
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

    private void initUnitEditText(){
        unitEditText.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                Log.e("THIS KEY IS CLICKED", " "+keyCode);
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {

                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            removeKeyboard(DepositActivity.this);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public static void removeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
        checkToSeeIfWeShouldGenerateConfirmButton();
    }

    private static void generateConfirmButton(){
        RelativeLayout.LayoutParams depositButtonLayoutParams = (RelativeLayout.LayoutParams) depositButton.getLayoutParams();
        depositButtonLayoutParams.removeRule(RelativeLayout.BELOW);
        depositButtonLayoutParams.addRule(RelativeLayout.BELOW,R.id.cardViewUnit);
        depositButton.setVisibility(View.VISIBLE);
        depositButton.setClickable(true);
    }

    private static void checkToSeeIfWeShouldGenerateConfirmButton(){

        String text = unitEditText.getText().toString();
        try {
            int num = Integer.parseInt(text);
            Log.i("",num+" is a number");
            generateConfirmButton();
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Enter valid units",
                    Toast.LENGTH_SHORT).show();
        }
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


                if(trashTypeSelected.toString().equalsIgnoreCase("cash-for-trash") || trashTypeSelected.toString().equalsIgnoreCase("cash for trash") ){
                    generateSpinnerForCashForTrash();
                }else{
                    removeSpinnerForCashForTrash();
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
            spinnerArrayAdapter.add("Select SubTrash");
            for (PriceInfo x : tcp.getTrash().get(index).getPriceInfoList()) {
                String Temp = x.getTrashName();
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
        if(v.getId() == R.id.depositButton){
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
            //cash for trash names
            if(trashType.equalsIgnoreCase("cash for trash")||trashType.equalsIgnoreCase("cash-for-trash")){
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
                Intent intentToConfirmationPage = new Intent(this, DepositCompleteActivity.class);
                startActivity(intentToConfirmationPage);
                Log.d(TAG, "onClick_deposit_enter: DepositConfirmClicked");
            }

            TrashInfo depositTrash = new TrashInfo(trashType,CashForTrashNames,CashForTrashUnits,CashForTrashPrices);
            float units = Float.valueOf(unitEditText.getText().toString());
            DepositManager.getInstance();
            DepositManager.createDepositRecord(depositTrash,units,new Date(),currentTCP);

            Intent intent = new Intent(DepositActivity.this, MainActivity.class);
            startActivity(intent);

        }
    }

    public void functionWhichRemovesKeyboardOnExternalTouch(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    removeKeyboard(DepositActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                functionWhichRemovesKeyboardOnExternalTouch(innerView);
            }
        }
    }

}
