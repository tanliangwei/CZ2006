package com.recyclingsg.app;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Howard on 12/3/2018.
 */

public class TrashCollectionPointManager {

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


    public static void createPrivateTrashCollectionPoint(String address, int zip, int contactDetail, ArrayList<TrashPrices> trashPrices, int openTIme, int closeTime, String description,int[] days) {

        PrivateTrashCollectionPoint ptcp = null;
        UserManager.getInstance();
        UserManager.addPrivateTrashCollectionPointToUser(ptcp);

    }


//    public PrivateTrashCollectionPoint createCollectionPoint(String name, int zipCode, int openTime, int closeTime, TrashPrices[] trash, LatLng coordinates, int[]dayOpen, User owner, Date expiryDate, Date startDate)
//    {
//        PrivateTrashCollectionPoint newPrivateTrashCollectionPoint = new PrivateTrashCollectionPoint(name,zipCode,openTime,closeTime,trash,coordinates,dayOpen,owner,expiryDate,startDate);
//        return newPrivateTrashCollectionPoint;
//    }




}
