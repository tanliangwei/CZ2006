package com.recyclingsg.app.control;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.recyclingsg.app.boundary.GoogleMapFragment;

import static com.facebook.FacebookSdk.getApplicationContext;

public class QueryFacade implements GoogleMapFragment.OnFragmentInteractionListener{

    private DatabaseInterface databaseManager;
    private FilterManager filterManager;
    private static final String TAG = "QueryFacade";
    private static final QueryFacade ourInstance = new QueryFacade();
    private GoogleMapFragment mGoogleMapFragment;

    public static QueryFacade getInstance() {
        return ourInstance;
    }

    private QueryFacade() {
        databaseManager = DatabaseManager.getInstance();
        filterManager = FilterManager.getInstance();

    }


    public void setmGoogleMapFragment(GoogleMapFragment mGoogleMapFragment) {
        this.mGoogleMapFragment = mGoogleMapFragment;
    }

    public GoogleMapFragment getGoogleMapFragment() {
        return mGoogleMapFragment;
    }

    public void query(View view, String userSelectedTrashType) {
        Log.d(TAG, "onClick: taking user to query results");

        if (userSelectedTrashType == null){
            Context context = getApplicationContext();
            CharSequence text = "Please select a waste type before query";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        filterManager.filterByCurrentDate((databaseManager.getTrashCollectionPoint(userSelectedTrashType)));
        mGoogleMapFragment.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
        filterManager.getClosedTrashCollectionPoints().clear();

        // display relevant collection points
//        switch (userSelectedTrashType) {
//            case "eWaste":
//                filterManager.filterByCurrentDate((databaseManager.getTrashCollectionPoint(userSelectedTrashType)));
//                Log.d(TAG, "query: the number of private trash collection point is "+ databaseManager.getEWastePrivateTrashCollectionPoints().size());
//                mGoogleMapFragment.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
//                filterManager.getClosedTrashCollectionPoints().clear();
//                break;
//            case "Cash For Trash":
//                filterManager.filterByCurrentDate((databaseManager.getTrashCollectionPoint(userSelectedTrashType)));
//                mGoogleMapFragment.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
//                filterManager.getClosedTrashCollectionPoints().clear();
//
//                Log.d(TAG, "query: Collection Points are" + filterManager.getOpenTrashCollectionPoints());
//                break;
//
//            case "Second Hand Goods":
//                filterManager.filterPublicByCurrentDate(databaseManager.getSecondHandPublicTrashCollectionPoints());
//                filterManager.filterPrivateByCurrentDate(databaseManager.getSecondHandPrivateTrashCollectionPoints());
//                mGoogleMapFragment.displayCollectionPoints(filterManager.getClosedTrashCollectionPoints());
//                filterManager.getClosedTrashCollectionPoints().clear();
//                break;
//        }


        //move camera

        mGoogleMapFragment.moveCameraToUserSelectedLocation();



    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mGoogleMapFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
