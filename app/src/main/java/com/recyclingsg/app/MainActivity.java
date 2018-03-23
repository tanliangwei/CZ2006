package com.recyclingsg.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();


    //vars
    private GoogleMapFragment mGoogleMapManager;
    private TrashCollectionPointManager mCollectionPointManager;
    private EditText mSearchText;
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAutoCompleteAdapter;
    private boolean selectedLocation = false;
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private FilterManager filterManager = new FilterManager();
    private String userSelectedTrashType;

    Button loginButton;
    Button addPostButton;
    Button navigate;

    public MainActivity() throws Exception {
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // to call startup functions.
        Configuration.getInstance();
        Configuration.startUp();

        setContentView(R.layout.activity_main);

        mCollectionPointManager = new TrashCollectionPointManager();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mGoogleMapManager = new GoogleMapFragment();
        fragmentTransaction.add(R.id.mapfragment,mGoogleMapManager);
        fragmentTransaction.commit();


        initAutoCompleteField();
        initWasteTypeSpinner();
        initSearchButton();
        navigate=findViewById(R.id.Navigation);
        navigate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:1.290270,103.851959?q=restaurant");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(mapIntent);
                }
            }
        });
        loginButton=(Button) findViewById(R.id.Login);
        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacebookLogin.class);
                startActivity(intent);
            }
        });

        addPostButton=(Button) findViewById(R.id.addPost);
        addPostButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(FacebookLogin.getLoginStatus()==null){
//                    Intent intent = new Intent(MainActivity.this, FacebookLogin.class);
//                    startActivity(intent);
//                }
//                else {
                    Intent intent = new Intent(MainActivity.this, PostPrivateCollectionPointActivity.class);
                    startActivity(intent);
//                }
            }
        });

        Button depositBtn = findViewById(R.id.depositTrash);
        depositBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if(FacebookLogin.getLoginStatus()==null) {
                   // Intent intent = new Intent(MainActivity.this, FacebookLogin.class);
                   // startActivity(intent);
               // }
               // else {
                    Intent intent = new Intent(MainActivity.this, DepositCategoryActivity.class);
                    startActivity(intent);
                //}
            }
        });

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

    public void initSearchButton(){
        Log.d(TAG, "initButton: initializing Search Button");
        Button button = (Button) findViewById(R.id.search_button);
        button.setOnClickListener(mSearchButtonListener);


    }

    public void initWasteTypeSpinner(){
        Log.d(TAG, "initWasteTypeSpinner: initialising Waste Type dropdown menu");

        Spinner spinner = (Spinner) findViewById(R.id.trash_type_selection_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> mSpinnerAdapter = createSpinnerAdapter();
// Specify the layout to use when the list of choices appears
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Initialise values of Spinner
        for (String x : TrashPrices.typeOfTrash)
            mSpinnerAdapter.add(x);

        mSpinnerAdapter.add("Select Waste Type");

// Apply the adapter to the spinner
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setSelection(mSpinnerAdapter.getCount());
        spinner.setOnItemSelectedListener(mWasteTypeSpinnerListener);

    }

    public void initAutoCompleteField(){
        Log.d(TAG, "initAutoComplete: initializing autocomplete text field");
        mGeoDataClient = Places.getGeoDataClient(this,null);
        //ArrayAdapter<TrashCollectionPoint> adapter = new ArrayAdapter<TrashCollectionPoint>(this, android.R.layout.select_dialog_item, mCollectionPointManager.getNodes());
        //ArrayAdapter<Node> adapter = new ArrayAdapter<Node>(this, android.R.layout.select_dialog_item, mCollectionPointManager.getNodes());

        AutoCompleteTextView mAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        // Register a listener that receives callbacks when a suggestion has been selected
        mAutoCompleteView.setOnItemClickListener(mAutocompleteClickListener);

        // Set up the adapter that will retrieve suggestions from the Places Geo Data Client.
        mAutoCompleteAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, GoogleMapFragment.getBoundsCoordSg(), null);
        mAutoCompleteView.setAdapter(mAutoCompleteAdapter);


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
            final AutocompletePrediction item = mAutoCompleteAdapter.getItem(position);
            final String placeId = item.getPlaceId();
            final CharSequence primaryText = item.getPrimaryText(null);

            Log.i(TAG, "Autocomplete item selected: " + primaryText);


            Toast.makeText(getApplicationContext(), "Selected Location: " + primaryText,
                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);

            /*
             Issue a request to the Places Geo Data Client to retrieve a Place object with
             additional details about the place.
              */
            Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeId);
            placeResult.addOnCompleteListener(new OnCompleteListener<PlaceBufferResponse>() {
                @Override
                public void onComplete(@NonNull Task<PlaceBufferResponse> task) {
                    if (task.isSuccessful()) {
                        PlaceBufferResponse places = task.getResult();
                        Place userSelectedPlace = places.get(0);

                        mGoogleMapManager.setUserSelectedLocation(userSelectedPlace);
                        Log.i(TAG, "onComplete: Location in CollectionPointManager changed to " + userSelectedPlace.getName());
                        places.release();
                        selectedLocation = true;
                    } else {
                        Log.e(TAG, "Place not found.");
                    }
                }
            });

        }
    };
    private AdapterView.OnItemSelectedListener mWasteTypeSpinnerListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (id != parent.getCount()) {
//
                Object trashTypeSelected = parent.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(), "Selected Waste Type: " + trashTypeSelected.toString(),
                        Toast.LENGTH_SHORT).show();

                Log.d(TAG, "onItemSelected: Selected " + id + ": " + trashTypeSelected.toString());

                //set trashType in collectionpointmanager
                userSelectedTrashType = (trashTypeSelected.toString());

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

    private AdapterView.OnClickListener mSearchButtonListener = new AdapterView.OnClickListener(){
        @Override
        public void onClick(View view) {

            query(view);

        }
    };

    private void query(View view) {
        Log.d(TAG, "onClick: taking user to query results");

        // display relevant collection points
            switch (userSelectedTrashType) {
                case "eWaste":
                    filterManager.filterByCurrentDate(databaseManager.getEWastePublicTrashCollectionPoints());
                    mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
                    break;
                case "Cash For Trash":
                    Log.d(TAG, "query: selected Cash for Trash");;
                    filterManager.filterByCurrentDate(databaseManager.getCashForTrashPublicTrashCollectionPoints());
                    mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());

                    Log.d(TAG, "query: Collection Points are" + filterManager.getOpenTrashCollectionPoints());
                    break;

                case "Second Hand":
                    filterManager.filterByCurrentDate(databaseManager.getSecondHandPublicTrashCollectionPoints());
                    mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
                    break;

            }


        //move camera

        if (selectedLocation==true) {
            mGoogleMapManager.moveCameraToUserSelectedLocation();
        }

    }

}