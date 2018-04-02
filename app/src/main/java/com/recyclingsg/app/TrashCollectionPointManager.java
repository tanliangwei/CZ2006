package com.recyclingsg.app;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.ContentValues.TAG;
import static com.recyclingsg.app.UserManager.*;
import android.content.Context;

import android.location.Geocoder;


/**
 * Created by Howard on 12/3/2018.
 */

public class TrashCollectionPointManager {
    private String userSelectedTrashPointID;
    private LatLng userSelectedTrashPointCoordinates;

    //to be removed

    private static TrashCollectionPointManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static TrashCollectionPointManager getInstance(){
        if (instance == null) {
            try {
                instance = new TrashCollectionPointManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct TrashCollectionPointManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }

    public TrashCollectionPointManager(){}

    public String getUserSelectedTrashPointID(){
        return userSelectedTrashPointID;
    }

    public void setUserSelectedTrashPointID(String id){
        userSelectedTrashPointID = id;
    }

    public static void createPrivateTrashCollectionPoint(String name, String address, String zip, String contactDetail, ArrayList<String> trashTypes,ArrayList<String> units, ArrayList<String> trashNames, ArrayList<Double> trashPrices, String openTime, String closeTime, String description, int[] days,Context context) {

        Log.d(TAG, "createPrivateTrashCollectionPoint: creating..");

        ArrayList<TrashInfo> trashInfoList = new ArrayList<TrashInfo>();
        for(int i=0;i<trashTypes.size();i++){
            if(!trashTypes.get(i).equalsIgnoreCase("Cash For Trash")) {
                TrashInfo trashinfo = new TrashInfo(trashTypes.get(i));
                trashInfoList.add(trashinfo);
            }else if(trashTypes.get(i).equalsIgnoreCase("Cash For Trash")){
                TrashInfo trashInfo = new TrashInfo(trashTypes.get(i),trashNames,units,trashPrices);
                trashInfoList.add(trashInfo);
            }
        }
        int contact = Integer.parseInt(contactDetail);
        int openingTime = Integer.parseInt(openTime);
        int closingTime = Integer.parseInt(closeTime);

        GoogleGeocoder googleGeocoder;
        googleGeocoder = GoogleGeocoder.getInstance();
        LatLng privateCollectionCoordinates = googleGeocoder.getLatLngFromAddress(zip, context);
        PrivateTrashCollectionPoint ptcp = new PrivateTrashCollectionPoint(name,privateCollectionCoordinates.latitude,privateCollectionCoordinates.longitude, openingTime,closingTime,trashInfoList,days,description,address);
        UserManager.getInstance();
        addPrivateTrashCollectionPointToUser(ptcp);

    }

    public LatLng getUserSelectedTrashPointCoordinates() {
        return userSelectedTrashPointCoordinates;
    }

    public void setUserSelectedTrashPointCoordinates(LatLng userSelectedTrashPointCoordinates) {
        this.userSelectedTrashPointCoordinates = userSelectedTrashPointCoordinates;
    }
}
