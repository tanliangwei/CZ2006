package com.recyclingsg.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoogleMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoogleMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final float DEFAULT_ZOOM = 15f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int ERROR_DIALOG_REQUEST = 1234;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String TAG = "GoogleMapFragment";

    private static final LatLngBounds BOUNDS_COORD_SG = new LatLngBounds(
            new LatLng( 1.22, 103.585), new LatLng(1.472823, 104.087221));


//static final variables


    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private OnFragmentInteractionListener mListener;
    private LatLng userSelectedLocation;



    public static LatLngBounds getBoundsCoordSg() {
        return BOUNDS_COORD_SG;
    }

    public LatLng getUserSelectedLocation() {
        return userSelectedLocation;
    }

    public void setUserSelectedLocation(Place userSelectedPlace) {
        Log.d(TAG, "setUserSelectedLocation: User Selects Location: " + userSelectedPlace.getName() );
        this.userSelectedLocation = userSelectedPlace.getLatLng();
    }

    public GoogleMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoogleMapFragment newInstance(String param1, String param2) {
        GoogleMapFragment fragment = new GoogleMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_google_map, container, false);
        getLocationPermission();
        initMap();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.d(TAG, "onButtonPressed: ");
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: Ready");
        getDeviceLocation();
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getContext()));
        try {
            if (getLocationPermission()) {
                //Location Permission already granted
                mMap.setMyLocationEnabled(true);
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        Log.d(TAG, "onMapReady: Google Map" + mMap);


    }



    public void initMap(){
        Log.d(TAG, "initMap: initializing map");

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        Log.d(TAG, "initMap: Map Fragment "+ mapFragment);
        if (mapFragment==null)
        {
            Log.d(TAG, "initMap: null map fragment");
            return;
        }

        mapFragment.getMapAsync( this);


    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    public void moveCameraToUserSelectedLocation (){
        moveCamera(userSelectedLocation,DEFAULT_ZOOM);
    }


    public void displayCollectionPoints(ArrayList<TrashCollectionPoint> collectionPoints){
        mMap.clear();
        Log.d(TAG, "displayCollectionPoints: Map clearing");
        for (TrashCollectionPoint c_point : collectionPoints){
            MarkerOptions options = new MarkerOptions()
                    .position(c_point.getCoordinate())
                    .title(c_point.getCollectionPointName());
            mMap.addMarker(options);
        }

    }


    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());



        try{
            if(getLocationPermission()){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(getActivity(),  new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful() && task.getResult()!=null){
                            Log.d(TAG, "onComplete: found location! Task: " + task.getResult());

                            Location currentLocation = (Location) task.getResult();
                            Log.d(TAG, "onComplete: " + currentLocation.toString());

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(), "Unable to get current location", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }

    }

    public boolean getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "getLocationPermission: Location granted");
                return true;
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions( getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
        return false;
    }





    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");


        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){

                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");

                }
            }
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
