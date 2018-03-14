package com.recyclingsg.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.Task;

import android.app.Dialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

import java.lang.reflect.Array;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    //vars
    private GoogleMapFragment mGoogleMapManager;
    private EditText mSearchText;
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        mGoogleMapManager = new GoogleMapFragment();
        fragmentTransaction.add(R.id.mapfragment,mGoogleMapManager);
        fragmentTransaction.commit();


        initAutoCompleteField();
        initWasteTypeSpinner();

        //add button

    }


//    private void initSearchField() {
//        Log.d(TAG, "initSearchField: initializing");
//
//        //mSearchText = (EditText) findViewById(R.id.search_field);
//        //initAutoComplete();
//
//
//
//        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH
//                        || actionId == EditorInfo.IME_ACTION_DONE
//                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
//                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
//                    Log.d(TAG, "onEditorAction: Searching..");
//
//                    //execute method for searching
//                }
//
//                return false;
//            }
//        });
//    }

    public void initWasteTypeSpinner(){
        Log.d(TAG, "initWasteTypeSpinner: initialising Waste Type dropdown menu");

        Spinner spinner = (Spinner) findViewById(R.id.trash_type_selection_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trash_types, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(mWasteTypeSpinnerListener);

    }

    public void initAutoCompleteField(){
        Log.d(TAG, "initAutoComplete: initializing autocomplete text field");
        mGeoDataClient = Places.getGeoDataClient(this,null);
        ArrayAdapter<Node> adapter = new ArrayAdapter<Node>(this, android.R.layout.select_dialog_item,new CollectionPointManager().getNodes());
        AutoCompleteTextView mAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutoCompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, GoogleMapFragment.getBoundsCoordSg(), null);
        mAutoCompleteView.setAdapter(mAdapter);


    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mGoogleMapManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }


    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            /*
             Retrieve the place ID of the selected item from the Adapter.
             The adapter stores each Place suggestion in a AutocompletePrediction from which we
             read the place ID and title.
              */
            final AutocompletePrediction item = mAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            //       placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);


            Toast.makeText(getApplicationContext(), "Selected Location: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

            if (placeResult.isSuccessful()){
                PlaceBufferResponse places = placeResult.getResult();
                mGoogleMapManager.setUserSelectedLocation(places.get(0).getLatLng());
                places.release();
            }
        }
    };
    private AdapterView.OnItemSelectedListener mWasteTypeSpinnerListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Object o = parent.getItemAtPosition(position);

            Toast.makeText(getApplicationContext(), "Selected Waste Type: " + o.toString(),
                    Toast.LENGTH_SHORT).show();

            Log.d(TAG, "onItemSelected: Selected " + id + ": " + o.toString());

            //set String of collectionpointmanager
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            
        }
    };


}