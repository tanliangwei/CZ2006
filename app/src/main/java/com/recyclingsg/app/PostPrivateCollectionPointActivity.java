package com.recyclingsg.app;

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
import java.util.List;

import static android.content.res.Configuration.KEYBOARD_12KEY;

public class PostPrivateCollectionPointActivity extends AppCompatActivity {

    private static final String TAG = "PrivatePointActivity";
    EditText addressFillField;
    EditText zipFillField;
    EditText contactDetailsFillField;
    EditText typeOfTrashFillField;
    EditText pricesFillField ;
    EditText openingTimeFillField;
    EditText closingTimeFillField;
    Button postPrivateCollectionPointButton;
    private String current = "";
    GoogleMapFragment googleMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_private_collection_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        googleMapFragment = new GoogleMapFragment();

        addressFillField = (EditText) findViewById(R.id.address_fill_up_field);
        zipFillField = (EditText) findViewById(R.id.zip_fill_up_field);
        contactDetailsFillField = (EditText) findViewById(R.id.contact_details_fill_up_field);
        typeOfTrashFillField = (EditText) findViewById(R.id.typeOfTrash_fill_up_field);
        pricesFillField = (EditText) findViewById(R.id.prices_fill_up_field);
        openingTimeFillField = (EditText) findViewById(R.id.opening_time_fill_up_field);
        closingTimeFillField = (EditText) findViewById(R.id.closing_time_fill_up_field);
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
        if (addressFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[0]) ||
                zipFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[1]) ||
                contactDetailsFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[2]) ||
                typeOfTrashFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[3]) ||
                pricesFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[4]) ||
                openingTimeFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[5]) ||
                closingTimeFillField.getText().equals(getResources().getStringArray(R.array.privateCollectionPointFields)[6]) ){
            Log.d(TAG, "onClick: null fields present");
            Toast.makeText(getApplicationContext(), "Fill up all fields",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "onClick: submitted post");


            TrashCollectionPointManager.getInstance();
            //TrashInfo trashPrice = new TrashInfo(typeOfTrashFillField.getText(),pricesFillField.getText().)


            //save texts
            LatLng privateCollectionCoordinates = getLatLngFromAddress(zipFillField.getText().toString());
            Log.d(TAG, "onClick: address" + zipFillField.getText().toString() + "LatLng = "
                    + privateCollectionCoordinates);

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

    public LatLng getLatLngFromAddress(String zipCode){

        Geocoder coder = new Geocoder(getBaseContext());
        List<Address> address;
        LatLng latLng = null;

        try {
            address = coder.getFromLocationName("Singapore " +zipCode,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);

            latLng = new LatLng(location.getLatitude(),location.getLongitude());


        }catch (IOException e){
            e.printStackTrace();
        }
        return latLng;
    }

}
