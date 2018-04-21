package com.recyclingsg.app.boundary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.recyclingsg.app.R;
import com.recyclingsg.app.control.TrashCollectionPointManager;
import com.recyclingsg.app.control.GoogleGeocoder;

import java.util.ArrayList;
import java.util.List;


public class PostPrivateCollectionPointActivity extends AppCompatActivity {

    private static final String TAG = "PrivatePointActivity";
    ViewPager viewPager;
    TabLayout tabLayout;
    SimpleFragmentPagerAdapter tabAdapter;

    EditText nameFillField;
    EditText addressFillField;
    EditText zipFillField;
    EditText contactDetailsFillField;
    EditText descriptionFillField;
    Button postPrivateCollectionPointButton;

    GoogleGeocoder googleGeocoder;
    List<String> typeOfTrashes;
    List<String> trashNames;
    List<Double> trashPrices;
    List<String> trashUnits;
    private TrashCollectionPointManager trashCollectionPointManager = TrashCollectionPointManager.getInstance();


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
        descriptionFillField = (EditText) findViewById(R.id.description_fill_up_field);
        postPrivateCollectionPointButton = (Button) findViewById(R.id.postPrivateCollectionPointButton);
        postPrivateCollectionPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCollectionPointForm();
            }
        });



        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabAdapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }



    //this function is called when the submit button is pressed
    private void submitCollectionPointForm() {
        String name = nameFillField.getText().toString();
        String address = addressFillField.getText().toString();
        String zipcode = zipFillField.getText().toString();
        String contact = contactDetailsFillField.getText().toString();
        String description = descriptionFillField.getText().toString();
        Log.d(TAG, "submitCollectionPointForm: " + name);
        Log.d(TAG, "submitCollectionPointForm: " + address);
        Log.d(TAG, "submitCollectionPointForm: " + zipcode);
        Log.d(TAG, "submitCollectionPointForm: " + contact);
        Log.d(TAG, "submitCollectionPointForm: " + description);






        if (name.equals("") ||
                address.equals("") ||
                zipcode.equals("") ||
                contact.equals("")) {
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

            tabAdapter.compileTtpList((ArrayList<String>)typeOfTrashes, (ArrayList<String>) trashNames, (ArrayList<Double>) trashPrices, (ArrayList<String>) trashUnits);


            Log.d(TAG, "submitCollectionPointForm: trashes being posted" + typeOfTrashes);

            trashCollectionPointManager.createPrivateTrashCollectionPoint(name, address, zipcode, contact,
                    (ArrayList<String>) typeOfTrashes, (ArrayList<String>) trashUnits, (ArrayList<String>) trashNames, (ArrayList<Double>) trashPrices,
                    "0000", "0000", description,  daysOpen, this);

            Toast.makeText(this, "Private Collection Point added!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostPrivateCollectionPointActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


}
