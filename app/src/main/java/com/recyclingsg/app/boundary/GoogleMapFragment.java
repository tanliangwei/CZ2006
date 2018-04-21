package com.recyclingsg.app.boundary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.recyclingsg.app.entity.PrivateTrashCollectionPoint;
import com.recyclingsg.app.entity.PublicTrashCollectionPoint;
import com.recyclingsg.app.R;
import com.recyclingsg.app.entity.TrashCollectionPoint;
import com.recyclingsg.app.control.TrashCollectionPointManager;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is the Google Map Fragment. It is embedded in main activity and is in charged of displaying the maps as well as related objects like
 * @author Honey Stars
 * @version 1.0
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GoogleMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GoogleMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnInfoWindowLongClickListener{
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
    private TrashCollectionPointManager trashCollectionPointManager = TrashCollectionPointManager.getInstance();

    private static final LatLngBounds BOUNDS_COORD_SG = new LatLngBounds(
            new LatLng( 1.22, 103.585), new LatLng(1.472823, 104.087221));

    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private OnFragmentInteractionListener mListener;
    private LatLng userSelectedLocation;
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button depositButton;
    private Button navigateButton;
    private OnInfoWindowElemTouchListener depositButtonListener;
    private OnInfoWindowElemTouchListener navigateButtonListener;


    /**
     * This creates an instance of Google Map Fragment
     */
    public GoogleMapFragment() {
    }

    /**
     * This returns the Coordinate Bounds of Singapore.
     * @return The bounds of Singapore in terms of latitude and longtitude
     */
    public static LatLngBounds getBoundsCoordSg() {
        return BOUNDS_COORD_SG;
    }

    /**
     * Returns the location selected by the user
     * @return Coordinates of the location selected by the user
     */
    public LatLng getUserSelectedLocation() {
        return userSelectedLocation;
    }

    /**
     * This sets the current location of the user to the location selected by him/her
     * @param userSelectedPlace The place selected by the user.
     */
    public void setUserSelectedLocation(Place userSelectedPlace) {
        this.userSelectedLocation = userSelectedPlace.getLatLng();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoogleMapFragment.
     */
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
//        Log.d(TAG, "onCreateView: ");
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_google_map, container, false);
//        getLocationPermission();
//        initMap();

        //temporary
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        getLocationPermission();
        Log.d(TAG, "initMap: initializing map");

//        final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
//        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map_new);
        Log.d(TAG, "initMap: Map Fragment "+ mapFragment);
        if (mapFragment==null)
        {
            Log.d(TAG, "initMap: null map fragment");
        }

        mapFragment.getMapAsync( this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)getView().findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(mMap, getPixelsFromDp(getActivity(), 39 + 20));

        this.infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.info_window, null);
        this.infoTitle = (TextView)infoWindow.findViewById(R.id.trashCollectionPointTitle);
        this.infoSnippet = (TextView)infoWindow.findViewById(R.id.descriptionText);
        this.depositButton = (Button)infoWindow.findViewById(R.id.depositButton);
        this.navigateButton=(Button)infoWindow.findViewById(R.id.navigateButton);


        this.depositButtonListener = new OnInfoWindowElemTouchListener(depositButton,
                getResources().getDrawable(R.drawable.common_google_signin_btn_icon_light),
                getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark_focused))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(getActivity(), marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();

                if(FacebookLogin.getLoginStatus()) {
                    Intent intent = new Intent(getActivity(), FacebookLogin.class);
                    String message = "Please login in to Facebook first.";
                    String activity = "Deposit";
                    intent.putExtra("activity",activity);
                    intent.putExtra("message", message);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(), DepositActivity.class);
                    startActivity(intent);
                }
            }
        };

        this.navigateButtonListener = new OnInfoWindowElemTouchListener(navigateButton,
                getResources().getDrawable(R.drawable.common_google_signin_btn_icon_light),
                getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark_focused))
        {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
//                Toast.makeText(getActivity(), marker.getTitle() + "'s button clicked!", Toast.LENGTH_SHORT).show();

                TrashCollectionPoint tcp= trashCollectionPointManager.getUserSelectedTrashCollectionPoint();
                String latlngLocation = trashCollectionPointManager.getUserSelectedTrashPointCoordinates().toString();
                latlngLocation=latlngLocation.substring(10,latlngLocation.length()-1);
                String collectionPointName = tcp.getCollectionPointName();
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+latlngLocation+"+"+collectionPointName);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        };


        this.depositButton.setOnTouchListener(depositButtonListener);
        this.navigateButton.setOnTouchListener(navigateButtonListener);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                TrashCollectionPoint tcp= (TrashCollectionPoint)marker.getTag();
                trashCollectionPointManager.setUserSelectedTrashCollectionPoint(tcp);
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                if (marker.getSnippet().length()<40){
                    String temp = marker.getSnippet();
                    for(int i=0;i<80;i++){
                        temp = temp +" ";
                    }
                    infoSnippet.setText(temp);
                }else {
                    infoSnippet.setText(marker.getSnippet());
                }
                navigateButtonListener.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);

                //to set certain things
                trashCollectionPointManager.setUserSelectedTrashPointID(marker.getId());
                trashCollectionPointManager.setUserSelectedTrashPointCoordinates(marker.getPosition());
                return infoWindow;
            }

        });

        //setting my location
        getDeviceLocation();
        try {
            if (getLocationPermission()) {
                //Location Permission already granted
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private LatLng offsetLatLng(LatLng x, double offset){
        return new LatLng(x.latitude, x.longitude - offset);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
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

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        Log.d(TAG, "onMapReady: Ready");
//        getDeviceLocation();
//        mMap = googleMap;
//        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this.getContext()));
//        mMap.setOnInfoWindowClickListener(this);
//        mMap.setOnInfoWindowLongClickListener(this);
//
//        try {
//            if (getLocationPermission()) {
//                //Location Permission already granted
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
//            }
//        }catch (SecurityException e){
//            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
//        }
//        Log.d(TAG, "onMapReady: Google Map" + mMap);
//    }

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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

    }

    /**
     * This centres the camera to the location selected by the user.
     */
    public void moveCameraToUserSelectedLocation (){
        moveCamera(userSelectedLocation,DEFAULT_ZOOM);
    }

    /**
     * This function centres the map to the Trash Collection Point selected by the user.
     * @param marker The marker of the Trash Collection Point which he created
     */
    public void moveCameraToUserSelectedCollectionPoint(Marker marker){
        Log.e("gege","i am in move camera to collectin point");
        Projection projection = mMap.getProjection();
        LatLng markerPosition = marker.getPosition();
        Point markerPoint = projection.toScreenLocation(markerPosition);
        Point targetPoint = new Point(markerPoint.x, markerPoint.y - 300);
        LatLng targetPosition = projection.fromScreenLocation(targetPoint);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(targetPosition), 1000, null);
    }

    /**
     * This function removes all the Trash Collection Points on the map
     */
    public void clearMapOfMarkers(){
        mMap.clear();
    }

    /**
     * This displays the Trash Collection Points on the map
     * @param collectionPoints The array of Trash Collection Points to be displayed
     */
    public void displayCollectionPoints(ArrayList<TrashCollectionPoint> collectionPoints){
        mMap.clear();
        Log.d(TAG, "displayCollectionPoints: Map clearing");
        for (TrashCollectionPoint c_point : collectionPoints){
            MarkerOptions options = new MarkerOptions()
                    .title(c_point.getCollectionPointName())
                    .snippet(c_point.getDescription())
                    .position(c_point.getCoordinate());
                    //.title(c_point.getCollectionPointName())
                    //.snippet(c_point.getDescription());
            options = assignIcon(c_point, options);
            Marker temp = mMap.addMarker(options);
            temp.setTag(c_point);
        }
    }

    private MarkerOptions assignIcon(TrashCollectionPoint tcp, MarkerOptions markerOptions){

        if( tcp instanceof PrivateTrashCollectionPoint)
            markerOptions.icon(BitmapDescriptorFactory.fromResource((R.mipmap.final_marker_person)));
        else if (tcp instanceof PublicTrashCollectionPoint)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.final_marker));
        return markerOptions;
    }


    public void getDeviceLocation(){
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

    @Override
    public void onInfoWindowClick(Marker marker) {
        TrashCollectionPoint tcp= trashCollectionPointManager.getUserSelectedTrashCollectionPoint();
        Date openTime= tcp.getOpenTime();
        Date closeTime=tcp.getCloseTime();
        String latlngLocation = trashCollectionPointManager.getUserSelectedTrashPointCoordinates().toString();
        int zipcode = trashCollectionPointManager.getUserSelectedTrashCollectionPoint().getZipCode();
        Toast.makeText(this.getContext(), "Info Window Clicked\n" +openTime+"\n"+closeTime+"\n"+tcp.getCollectionPointName() , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
        //Toast.makeText(this.getContext(), "Info Window Long Clicked", Toast.LENGTH_SHORT).show();
        TrashCollectionPoint tcp= trashCollectionPointManager.getUserSelectedTrashCollectionPoint();
        String latlngLocation = trashCollectionPointManager.getUserSelectedTrashPointCoordinates().toString();
        latlngLocation=latlngLocation.substring(10,latlngLocation.length()-1);
        String collectionPointName = tcp.getCollectionPointName();
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+latlngLocation+"+"+collectionPointName);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        //Log.d(TAG, "onInfoWindowLongClick: Long Clicked Info Window");
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
