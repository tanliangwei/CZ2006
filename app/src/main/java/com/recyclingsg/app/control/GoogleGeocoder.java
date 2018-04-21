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
 * Created by Howard on 24/3/2018.
 */

public class GoogleGeocoder {
    private static final GoogleGeocoder ourInstance = new GoogleGeocoder();

    public static GoogleGeocoder getInstance() {
        return ourInstance;
    }

    private GoogleGeocoder() {
    }

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
