package com.recyclingsg.app;

import android.content.Context;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleMapFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    //public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    //vars
    private GoogleMapFragment mGoogleMapManager;
    private EditText mSearchText;
    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAutoCompleteAdapter;
    private boolean selectedLocation = false;
    private DatabaseManager databaseManager = DatabaseManager.getInstance();
    private FilterManager filterManager = new FilterManager();
    private String userSelectedTrashType;

    public MainActivity() throws Exception {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to call startup functions.
        Configuration.getInstance();
        Configuration.startUp();
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mGoogleMapManager = new GoogleMapFragment();
        fragmentTransaction.add(R.id.mapfragment, mGoogleMapManager);
        fragmentTransaction.commit();


        initAutoCompleteField();
        initWasteTypeSpinner();
        initSearchButton();

        //Setting up side Navigation
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StatisticsManager.refreshData();

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        //TODO [need to figure out how to get username and user id]
//        UserManager mine = new UserManager();
//        UserManager.getInstance();
//
//        View headerView = navigationView.getHeaderView(0);
//        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_userName);
//        navUsername.setText("UserName");
//        //navUsername.setText(UserManager.getUserName());
//
//        TextView navUserId = (TextView) headerView.findViewById(R.id.nav_userId);
//        //navUserId.setText(UserManager.getUserId());
//        navUserId.setText("User ID");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView){
                Log.d(TAG, "drawer opened");
                updateLoginView();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        functionWhichRemovesKeyboardOnExternalTouch((DrawerLayout)findViewById(R.id.drawer_layout));
        functionWhichRemovesKeyboardOnExternalTouch((ConstraintLayout)findViewById(R.id.constraintLayout));
    }

//Test comment

    public void updateLoginView(){
        //String userID = UserManager.getUserId();
        String userName = UserManager.getUserName();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        //ImageView navPicture = findViewById(R.id.nav_profile);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_userName);
        TextView navPoints = (TextView) headerView.findViewById(R.id.nav_points);
        if(FacebookLogin.getLoginStatus()){
            navUsername.setText("User Name");
            navPoints.setText("Points: 0");
        }
        else{
           // navPicture.setImageBitmap(UserManager.getFacebookProfilePicture());
            navUsername.setText(userName);
            navPoints.setText("Points: "+StatisticsManager.getUserScore());
            //TODO Please make sure that statistic manager is constructed before calling the following function
            //StatisticsManager.getInstance();
            //navPoints.setText("Points: " + StatisticsManager.getUserScore());
        }

    }

    /*public void navigate(View view) {
        //format: "geo: latitude,longitude? q="" "
        Uri gmmIntentUri = Uri.parse("geo:1.290270,103.851959?q=restaurant");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }*/


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
        for (String x : TrashInfo.typeOfTrash)
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
//

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

        if (userSelectedTrashType == null){
            Context context = getApplicationContext();
            CharSequence text = "Please select a waste type before query";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        // display relevant collection points
        switch (userSelectedTrashType) {
            case "eWaste":
                filterManager.filterPublicByCurrentDate((databaseManager.getEWastePublicTrashCollectionPoints()));
                filterManager.filterPrivateByCurrentDate(databaseManager.getEWastePrivateTrashCollectionPoints());
                Log.d(TAG, "query: the number of private trash collection point is "+ databaseManager.getEWastePrivateTrashCollectionPoints().size());
                mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
                filterManager.getClosedTrashCollectionPoints().clear();
                break;
            case "Cash For Trash":
                Log.d(TAG, "query: selected Cash for Trash");;
                filterManager.filterPublicByCurrentDate(databaseManager.getCashForTrashPublicTrashCollectionPoints());
                filterManager.filterPrivateByCurrentDate(databaseManager.getCashForTrashPrivateTrashCollectionPoints());
                mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
                filterManager.getClosedTrashCollectionPoints().clear();

                Log.d(TAG, "query: Collection Points are" + filterManager.getOpenTrashCollectionPoints());
                break;

            case "Second Hand Goods":
                filterManager.filterPublicByCurrentDate(databaseManager.getSecondHandPublicTrashCollectionPoints());
                filterManager.filterPrivateByCurrentDate(databaseManager.getSecondHandPrivateTrashCollectionPoints());
                mGoogleMapManager.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
                filterManager.getClosedTrashCollectionPoints().clear();
                break;
        }


        //move camera

        if (selectedLocation==true) {
            mGoogleMapManager.moveCameraToUserSelectedLocation();
        }

    }

    //Side navigation

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_trashPool) {
            if(FacebookLogin.getLoginStatus()){
                Intent intent = new Intent(MainActivity.this, FacebookLogin.class);
                String message = "Please login in to Facebook first.";
                String activity = "TrashPool";
                intent.putExtra("message", message);
                intent.putExtra("activity",activity);
                startActivity(intent);


            }
            else {
                Intent intent = new Intent(MainActivity.this, PostPrivateCollectionPointActivity.class);
                startActivity(intent);
            }
        }  else if (id == R.id.nav_standings) {
            if(FacebookLogin.getLoginStatus()){
                Intent intent = new Intent(MainActivity.this, FacebookLogin.class);
                String message = "Please login in to Facebook first.";
                String activity = "Statistics";
                intent.putExtra("message", message);
                intent.putExtra("activity",activity);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_settings) {
            //TODO this will jump to settings activity
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void removeKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void functionWhichRemovesKeyboardOnExternalTouch(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    removeKeyboard(MainActivity.this);
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
