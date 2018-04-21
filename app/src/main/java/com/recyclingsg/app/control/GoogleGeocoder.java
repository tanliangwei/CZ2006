package com.recyclingsg.app.control;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * This class is GoogleGeocoder class. It converts addresses to coordinates so that they can be displayed by Google Maps
 * @author Honey Stars
 * @version 1.0
 */

public class GoogleGeocoder {
    private static final GoogleGeocoder ourInstance = new GoogleGeocoder();

    /**
     * This returns a singleton instance of the Google.
     * @return Singleton instance of Deposit Manager
     */
    public static GoogleGeocoder getInstance() {
        return ourInstance;
    }

    private GoogleGeocoder() {
    }


    /**
     * This function converts a ZIP code to coordinates so that it can be displayed on a map.
     * @param zipCode The ZIP code to be converted
     * @param context The context of the current activity.
     * @return The Lat-Long object containing coordinates.
     */
    public LatLng getLatLngFromAddress(String zipCode, Context context){
    
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng latLng = null;

        try {
            address = coder.getFromLocationName("Singapore " +zipCode,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);

            latLng = new LatLng(location.getLatitude(),location.getLongitude());
            Log.d(TAG, "getLatLngFromAddress: created coordinate from zipcode");

        }catch (IOException e){
            e.printStackTrace();
        }
        return latLng;
    }
}
