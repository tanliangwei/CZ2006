package com.recyclingsg.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.CheckBox;
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
    ViewPager viewPager;
    TabLayout tabLayout;
    SimpleFragmentPagerAdapter tabAdapter;

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
//    EditText trashNameFillField;
//    EditText trashPricesFillField;
//    EditText trashUnitFillField;
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
        descriptionFillField = (EditText) findViewById(R.id.description_fill_up_field);
        postPrivateCollectionPointButton = (Button) findViewById(R.id.postPrivateCollectionPointButton);
        postPrivateCollectionPointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitCollectionPointForm();
            }
        });

        ttpList = new ArrayList<>();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabAdapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);


    }





    public void compileTtpList() {
        if (((CheckBox)findViewById(R.id.checkbox_cashfortrash)).isChecked()){
            typeOfTrashes.add("Cash for Trash");
            compileCFTposts();
        }
        if (((CheckBox)findViewById(R.id.checkbox_eWaste)).isChecked()){
            typeOfTrashes.add("eWaste");
        }
        if (((CheckBox)findViewById(R.id.checkbox_second_hand)).isChecked()){
            typeOfTrashes.add("Second Hand Goods");
        }


    }
    public void compileCFTposts(){
        if (((CheckBox)findViewById(R.id.checkbox_aluminium_drink_cans)).isChecked()) {
            Double price = Double.parseDouble(((EditText) findViewById(R.id.price_aluminium_drink_cans)).toString().substring(1));
            trashNames.add("Aluminium drink cans");
            trashPrices.add(price);
        }
        if (((CheckBox)findViewById(R.id.checkbox_metal_tins)).isChecked()) {
            Double price = Double.parseDouble(((EditText) findViewById(R.id.price_metal_tins)).toString().substring(1));
            trashNames.add("Metal Tins");
            trashPrices.add(price);
        }
        if (((CheckBox)findViewById(R.id.checkbox_old_clothing)).isChecked()) {
            Double price = Double.parseDouble(((EditText) findViewById(R.id.price_old_clothing)).toString().substring(1));
            trashNames.add("Old Clothing / bedsheet ");
            trashPrices.add(price);
        }
        if (((CheckBox)findViewById(R.id.checkbox_papers)).isChecked()) {
            Double price = Double.parseDouble(((EditText) findViewById(R.id.price_papers)).toString().substring(1));
            trashNames.add("Papers");
            trashPrices.add(price);
        }
        if (((CheckBox)findViewById(R.id.checkbox_small_appliances)).isChecked()) {
            trashNames.add("Small Appliances");
            trashPrices.add((double) 0);
        }

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



            //calling trash collection point manager.
            TrashCollectionPointManager.getInstance();

            Log.d(TAG, "submitCollectionPointForm: trashes being posted" + typeOfTrashes);

            TrashCollectionPointManager.createPrivateTrashCollectionPoint(name, address, zipcode, contact,
                    (ArrayList<String>) typeOfTrashes, (ArrayList<String>) trashUnits, (ArrayList<String>) trashNames, (ArrayList<Double>) trashPrices,
                    "0000", "0000", description,  daysOpen, this);

            Toast.makeText(this, "Private Collection Point added!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(PostPrivateCollectionPointActivity.this, MainActivity.class);
            startActivity(intent);
        }
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






}
