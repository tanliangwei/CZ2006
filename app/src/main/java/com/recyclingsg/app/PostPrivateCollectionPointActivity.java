package com.recyclingsg.app;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.res.Configuration.KEYBOARD_12KEY;

public class PostPrivateCollectionPointActivity extends AppCompatActivity {

    private static final String TAG = "PrivatePointActivity";
    EditText nameFillField;
    EditText addressFillField;
    EditText zipFillField;
    EditText contactDetailsFillField;
    EditText typeOfTrashFillField;
    EditText pricesFillField ;
    EditText openingTimeFillField;
    EditText closingTimeFillField;
    EditText descriptionFillField;
    Button postPrivateCollectionPointButton;
    private String current = "";
    GoogleGeocoder googleGeocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_private_collection_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        googleGeocoder = GoogleGeocoder.getInstance();

        nameFillField = (EditText) findViewById(R.id.name_fill_up_field);
        addressFillField = (EditText) findViewById(R.id.address_fill_up_field);
        zipFillField = (EditText) findViewById(R.id.zip_fill_up_field);
        contactDetailsFillField = (EditText) findViewById(R.id.contact_details_fill_up_field);
        typeOfTrashFillField = (EditText) findViewById(R.id.typeOfTrash_fill_up_field);
        pricesFillField = (EditText) findViewById(R.id.prices_fill_up_field);
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

        pricesFillField.setRawInputType(KEYBOARD_12KEY);
        pricesFillField.addTextChangedListener(new TextWatcher() {

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
                        pricesFillField.setText("$" + dec.format(percen));
                        pricesFillField.setSelection(pricesFillField.getText().length());
                    }
                }
            }
        });

    }


    //this function is called when the submit button is pressed
    private void submitCollectionPointForm(){
        String name = nameFillField.getText().toString();
        String address = addressFillField.getText().toString();
        String zipcode = zipFillField.getText().toString();
        String contact = contactDetailsFillField.getText().toString();
        String typeOfTrash = typeOfTrashFillField.getText().toString();
        String prices = pricesFillField.getText().toString().substring(1);
        Log.d(TAG, "submitCollectionPointForm: " + prices);
        String openingTime = openingTimeFillField.getText().toString();
        String closingTime = closingTimeFillField.getText().toString();
        String description = descriptionFillField.getText().toString();

        if (name.equals("Name") ||
                address.equals("Address") ||
                zipcode.equals("Zip Code") ||
                contact.equals("Contact Number") ||
                typeOfTrash.equals("Type Of Trash") ||
                prices.equals("Prices") ||
                openingTime.equals("Opening Time") ||
                closingTime.equals("Closing Time") ){
            Log.d(TAG, "onClick: null fields present");
            Toast.makeText(getApplicationContext(), "Please fill up all fields",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Submitting Form..",Toast.LENGTH_SHORT).show();

            int[] daysOpen = new int[7];

            // TODO: 28/3/18 Howard please create some basic controls to fill in the 2 arrays below
            //Howard, the TRASHTYPE consist of 3 types, so Cash for Trash, E-waste, SecondHand.
            //The units will only come when there are prices, so it is for cash for trash, if there are cash for trash,
            // the TRASHTYPE will contain a cash for trash
            //TRASHNAME and TRASHPRICES will be the names and prices arranged in order. so like aluminium, $2, units will be '$/kg' in string

            ArrayList<String> trashTypes = new ArrayList<String>();
            ArrayList<String> units = new ArrayList<String>();
            ArrayList<String> trashNames = new ArrayList<String>();
            ArrayList<Double> trashPrices = new ArrayList<Double>();
            //calling trash collection point manager.
            TrashCollectionPointManager.getInstance();
            TrashCollectionPointManager.createPrivateTrashCollectionPoint(name, zipcode, openingTime, closingTime, trashTypes,units,trashNames, trashPrices, openingTime, description,address, daysOpen, this);

            Toast.makeText(this, "Private Collection Point added!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostPrivateCollectionPointActivity.this, MainActivity.class);
            startActivity(intent);
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
