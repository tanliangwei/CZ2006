package com.recyclingsg.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.res.Configuration.KEYBOARD_12KEY;

public class PostPrivateCollectionPointActivity extends AppCompatActivity {

    private static final String TAG = "PrivatePointActivity";
    EditText nameFillField;
    EditText addressFillField;
    EditText zipFillField;
    EditText contactDetailsFillField;
    EditText openingTimeFillField;
    EditText closingTimeFillField;
    EditText descriptionFillField;
    Button postPrivateCollectionPointButton;
    Spinner typeOfTrashSpinner;
    ArrayAdapter<String> mSpinnerAdapter;
    EditText trashNameFillField;
    EditText trashPricesFillField;
    EditText trashUnitFillField;
    Button addTypeOfTrash;
    String trashTypeSelected;
    List<TrashTypePost> ttpList;
    TrashTypeListAdapter trashTypeListAdapter;
    private String current = "";
    GoogleGeocoder googleGeocoder;
    List<String> typeOfTrashes;
    List<String> trashNames;
    List<Double> trashPrices;
    List<String> trashUnits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_private_collection_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        googleGeocoder = GoogleGeocoder.getInstance();
        typeOfTrashes = new ArrayList<>();
        trashNames = new ArrayList<>();
        trashPrices = new ArrayList<>();
        trashUnits = new ArrayList<>();

        nameFillField = (EditText) findViewById(R.id.name_fill_up_field);
        addressFillField = (EditText) findViewById(R.id.address_fill_up_field);
        zipFillField = (EditText) findViewById(R.id.zip_fill_up_field);
        contactDetailsFillField = (EditText) findViewById(R.id.contact_details_fill_up_field);
        openingTimeFillField = (EditText) findViewById(R.id.opening_time_fill_up_field);
        closingTimeFillField = (EditText) findViewById(R.id.closing_time_fill_up_field);
        descriptionFillField = (EditText) findViewById(R.id.description_fill_up_field);
        postPrivateCollectionPointButton = (Button) findViewById(R.id.postPrivateCollectionPointButton);
        postPrivateCollectionPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCollectionPointForm();
            }
        });

        ttpList = new ArrayList<>();
        typeOfTrashSpinner = initWasteTypeSpinner();
        addTypeOfTrash = (Button) findViewById(R.id.add_trash);
        trashNameFillField = (EditText) findViewById(R.id.trash_name);
        trashNameFillField.setVisibility(View.INVISIBLE);
        trashPricesFillField = (EditText) findViewById(R.id.trash_price);
        trashPricesFillField.setVisibility(View.INVISIBLE);
        trashUnitFillField = (EditText) findViewById(R.id.trash_unit);
        trashUnitFillField.setVisibility(View.INVISIBLE);
        trashTypeListAdapter = new TrashTypeListAdapter(this, ttpList);
        ListView trashTypeListView = (ListView) findViewById(R.id.added_trash_list);
        trashTypeListView.setAdapter(trashTypeListAdapter);


        addTypeOfTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (trashTypeSelected == null) {
                    Log.d(TAG, "onClick: trashType Null");
                    return;
                }

                TrashTypePost ttp = new TrashTypePost();
                ttp.typeOfTrash = trashTypeSelected;
                ttp.trashName = trashNameFillField.getText().toString();
                ttp.trashPrice = trashPricesFillField.getText().toString();
                ttp.trashUnit = trashUnitFillField.getText().toString();
                ttpList.add(ttp);
                Log.d(TAG, "onClick: " + ttpList.toString());
                trashTypeListAdapter.notifyDataSetChanged();
                saveTtpToList(ttp);

                typeOfTrashSpinner.setSelection(mSpinnerAdapter.getCount());
                trashNameFillField.setText("");
                trashPricesFillField.setText("");
                trashUnitFillField.setText("");



        trashPricesFillField.setRawInputType(KEYBOARD_12KEY);
        trashPricesFillField.addTextChangedListener(new TextWatcher() {

            DecimalFormat dec = new DecimalFormat("0.00");
            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    if (userInput.length() > 0) {
                        Float in = Float.parseFloat(userInput);
                        float percen = in / 100;
                        trashPricesFillField.setText("$" + dec.format(percen));
                        trashPricesFillField.setSelection(trashPricesFillField.getText().length());
                    }
                }
          }
        });


            }
        });
    }

    void saveTtpToList(TrashTypePost ttp){

        if (ttp.typeOfTrash.equals("Cash For Trash")){
            trashNames.add(ttp.trashName);
            trashPrices.add(Double.parseDouble(ttp.trashPrice));
            trashUnits.add(ttp.trashUnit);
        }
        typeOfTrashes.add(ttp.typeOfTrash);
    }


    //this function is called when the submit button is pressed
    private void submitCollectionPointForm() {
        String name = nameFillField.getText().toString();
        String address = addressFillField.getText().toString();
        String zipcode = zipFillField.getText().toString();
        String contact = contactDetailsFillField.getText().toString();
        String openingTime = openingTimeFillField.getText().toString();
        String closingTime = closingTimeFillField.getText().toString();
        String description = descriptionFillField.getText().toString();
        Log.d(TAG, "submitCollectionPointForm: " + name);
        Log.d(TAG, "submitCollectionPointForm: " + address);
        Log.d(TAG, "submitCollectionPointForm: " + zipcode);
        Log.d(TAG, "submitCollectionPointForm: " + contact);
        Log.d(TAG, "submitCollectionPointForm: " + openingTime);
        Log.d(TAG, "submitCollectionPointForm: " + closingTime);
        Log.d(TAG, "submitCollectionPointForm: " + description);


        if(trashTypeSelected != null){
            if trashTypeSelected.equals("Cash for Trash"){
                trashNames.add(trashNameFillField.getText().toString());
                trashPrices.add(Double.parseDouble(trashPricesFillField.getText().toString()));
                trashUnits.add(trashUnitFillField.getText().toString());
            }
            typeOfTrashes.add(trashTypeSelected);

        }



        if (name.equals("Name") ||
                address.equals("Address") ||
                zipcode.equals("Zip Code") ||
                contact.equals("Contact Number") ||
                openingTime.equals("Opening Time") ||
                closingTime.equals("Closing Time")) {
            Log.d(TAG, "onClick: null fields present");
            Toast.makeText(getApplicationContext(), "Please fill up all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Submitting Form..", Toast.LENGTH_SHORT).show();

            int[] daysOpen = new int[7];

            // TODO: 28/3/18 Howard please create some basic controls to fill in the 2 arrays below
            //Howard, the TRASHTYPE consist of 3 types, so Cash for Trash, E-waste, SecondHand.
            //The units will only come when there are prices, so it is for cash for trash, if there are cash for trash,
            // the TRASHTYPE will contain a cash for trash
            //TRASHNAME and TRASHPRICES will be the names and prices arranged in order. so like aluminium, $2, units will be '$/kg' in string



            //calling trash collection point manager.
            TrashCollectionPointManager.getInstance();



            TrashCollectionPointManager.createPrivateTrashCollectionPoint(name, address, zipcode, contact,
                    (ArrayList<String>) typeOfTrashes, (ArrayList<String>) trashUnits, (ArrayList<String>) trashNames, (ArrayList<Double>) trashPrices,
                    openingTime, closingTime, description,  daysOpen, this);

            Toast.makeText(this, "Private Collection Point added!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostPrivateCollectionPointActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public Spinner initWasteTypeSpinner(){
        Log.d(TAG, "initWasteTypeSpinner: initialising Waste Type dropdown menu");

        Spinner spinner = (Spinner) findViewById(R.id.trash_type_to_post_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        mSpinnerAdapter = createSpinnerAdapter();
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
        return spinner;

    }


    private AdapterView.OnItemSelectedListener mWasteTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (id != parent.getCount()) {
//
                Object trashTypeObjectSelected = parent.getItemAtPosition(position);
                trashTypeSelected = trashTypeObjectSelected.toString();

                if(!trashTypeSelected.equals("Cash For Trash")) {
                    trashNameFillField.setVisibility(View.INVISIBLE);
                    trashPricesFillField.setVisibility(View.INVISIBLE);
                    trashUnitFillField.setVisibility(View.INVISIBLE);
                }
                else {
                    trashNameFillField.setVisibility(View.VISIBLE);
                    trashPricesFillField.setVisibility(View.VISIBLE);
                    trashUnitFillField.setVisibility(View.VISIBLE);


                }

            }
        }
        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }

    };


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

    class TrashTypePost{
        String typeOfTrash;
        String trashName;
        String trashPrice;
        String trashUnit;
    }



    class TrashTypeViewHolder {
        TextView list_typeOfTrash;
        TextView list_trashName;
        TextView list_trashPrice;
        TextView list_trashUnit;

        TrashTypeViewHolder(View view){
            list_typeOfTrash = (TextView) view.findViewById(R.id.trash_type_display);
            list_trashName = (TextView) view.findViewById(R.id.trash_name_display);
            list_trashPrice = (TextView) view.findViewById(R.id.trash_price_display);
            list_trashUnit = (TextView) view.findViewById(R.id.trash_unit_display);

        }

        public void setText(TrashTypePost ttp){

            list_typeOfTrash.setText(ttp.typeOfTrash);

            if (ttp.trashName == null || ttp.trashPrice == null || ttp.trashUnit == null){
                list_trashName.setVisibility(View.INVISIBLE);
                list_trashPrice.setVisibility(View.INVISIBLE);
                list_trashUnit.setVisibility(View.INVISIBLE);
            } else{
                list_trashName.setText(ttp.trashName);
                list_trashPrice.setText(ttp.trashPrice);
                list_trashUnit.setText(ttp.trashUnit);
            }
        }


    }


    class TrashTypeListAdapter extends ArrayAdapter<TrashTypePost>{

        List<TrashTypePost> list;
        public TrashTypeListAdapter(Activity context, List<TrashTypePost> list){
            super(context, 0, list);
            this.list = list;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TrashTypeViewHolder trashTypeViewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_trashtype_to_post,parent,false);
                trashTypeViewHolder = new TrashTypeViewHolder(convertView);
                convertView.setTag(trashTypeViewHolder);
            }else{
                trashTypeViewHolder = (TrashTypeViewHolder) convertView.getTag();
            }
            trashTypeViewHolder.setText(list.get(position));
            return convertView;
        }

    }




//    mEditPrice.setRawInputType(Configuration.KEYBOARD_12KEY);
//    public void priceClick(View view) {
//        pricesFillField.addTextChangedListener(new TextWatcher() {
//            DecimalFormat dec = new DecimalFormat("0.00");
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
//                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
//                    if (userInput.length() > 0) {
//                        Float in = Float.parseFloat(userInput);
//                        float percen = in / 100;
//                        pricesFillField.setText("$" + dec.format(percen));
//                        pricesFillField.setSelection(pricesFillField.getText().length());
//                    }
//                }
//            }
//        });
//    }






}
