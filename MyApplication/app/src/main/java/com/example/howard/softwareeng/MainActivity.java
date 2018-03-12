package com.example.howard.softwareeng;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GoogleMapFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    //vars
    private GoogleMapFragment mGoogleMapManager;
    private EditText mSearchText;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();



        mGoogleMapManager = new GoogleMapFragment();
        fragmentTransaction.add(R.id.mapfragment,mGoogleMapManager);
        fragmentTransaction.commit();


        initSearchField();



    }


    private void initSearchField() {
        Log.d(TAG, "initSearchField: initializing");

        mSearchText = (EditText) findViewById(R.id.search_field);
        initAutoComplete();



        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    Log.d(TAG, "onEditorAction: Searching..");

                    //execute method for searching
                }

                return false;
            }
        });
    }

    public void initAutoComplete(){
        ArrayAdapter<Node> adapter = new ArrayAdapter<Node>(this, android.R.layout.select_dialog_item,new CollectionPointManager().getNodes());
        AutoCompleteTextView mAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mAutoComplete.setThreshold(1);
        mAutoComplete.setAdapter(adapter);

    }




    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mGoogleMapManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {


    }
}