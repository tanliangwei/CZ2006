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

    public TrashCollectionPointManager(){
//        nodes = new ArrayList<Node>(){{
//            add (new Node("Jurong Recycling Station", "a1234", new LatLng(1.3343,(103.742))));
//            add (new Node("Pasir Ris Recyling Station", "b4321", new LatLng(1.3721, 103.9474)));
//            add (new Node("SouthWest Bound", "swb", new LatLng(1.22, 103.585)));
//            add (new Node("NorthEast Bound", "neb", new LatLng(1.472823, 104.087221)));
//
//
//        }};

    }

    public String getUserSelectedTrashPointID(){
        return userSelectedTrashPointID;
    }

    public void setUserSelectedTrashPointID(String id){
        userSelectedTrashPointID = id;
    }


    //name, privateCollectionCoordinates.latitude, privateCollectionCoordinates.longitude,
    //openingTime, closingTime, trashInfoList, daysOpen, description,address

    public static void createPrivateTrashCollectionPoint(String name, String address, String zip, String contactDetail, ArrayList<String> trashNames, ArrayList<Double> trashPrices, String openTime, String closeTime, String description, int[] days,Context context) {

        Log.d(TAG, "createPrivateTrashCollectionPoint: creating..");

        TrashInfo userTrashInfo = new TrashInfo();

        ArrayList<TrashInfo> trashInfoList = new ArrayList<TrashInfo>();
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
    //String name, double xCoordinate, double yCoordinate, int openTime,
    // int closeTime, ArrayList<String> trashName,ArrayList<Integer> trashPrice,
    // int[] daysOpen,String description, String address, String ownerName, String ownerID


//    public PrivateTrashCollectionPoint createCollectionPoint(String name, int zipCode, int openTime, int closeTime, TrashInfo[] trash, LatLng coordinates, int[]dayOpen, User owner, Date expiryDate, Date startDate)
//    {
//        PrivateTrashCollectionPoint newPrivateTrashCollectionPoint = new PrivateTrashCollectionPoint(name,zipCode,openTime,closeTime,trash,coordinates,dayOpen,owner,expiryDate,startDate);
//        return newPrivateTrashCollectionPoint;
//    }




}
