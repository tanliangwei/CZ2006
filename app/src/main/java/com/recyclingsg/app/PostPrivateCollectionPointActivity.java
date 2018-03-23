package com.recyclingsg.app;

import android.content.Intent;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_private_collection_point);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final GoogleMapFragment mGoogleMapFragment = new GoogleMapFragment();

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
        if (addressFillField.getText()==((EditText) findViewById(R.id.address_fill_up_field)).getHint() ||
                zipFillField.getText()==(EditText) findViewById(R.id.zip_fill_up_field) ||
                contactDetailsFillField.getText()==(EditText) ((EditText) findViewById(R.id.zip_fill_up_field)).getHint() ||
                typeOfTrashFillField.getText()==null ||
                pricesFillField.getText()==null ||
                openingTimeFillField.getText()==null ||
                closingTimeFillField.getText()==null ){
            Log.d(TAG, "onClick: null fields present");
            Toast.makeText(getApplicationContext(), "Fill up all fields",Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d(TAG, "onClick: submitted post");

            Log.d(TAG, "onClick: address" + addressFillField.getText().toString());
            TrashCollectionPointManager.getInstance();
            //TrashPrices trashPrice = new TrashPrices(typeOfTrashFillField.getText(),pricesFillField.getText().)


                    //save texts



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
