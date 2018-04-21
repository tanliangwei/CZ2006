package com.recyclingsg.app.control;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.recyclingsg.app.entity.PrivateTrashCollectionPoint;
import com.recyclingsg.app.entity.TrashCollectionPoint;
import com.recyclingsg.app.entity.TrashInfo;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * This class is the Trash Collection Point Manager. It is in charge of adding Trash Collection Point to users as well as storing the Trash Collection Point selected by the User.
 * @author Honey Stars
 * @version 1.0
 */

public class TrashCollectionPointManager {
    private String userSelectedTrashPointID;
    private  LatLng userSelectedTrashPointCoordinates;
    private TrashCollectionPoint userSelectedTrashPoint;
    private final static String TAG = "TrashPointManager";
    //to be removed

    private static TrashCollectionPointManager instance;

    /**
     * This returns a singleton instance of the Trash Collection Point Manager.
     * @return Singleton instance of Trash Collection Point Manager
     */
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

    public String getUserSelectedTrashCollectionPointID(){
        return userSelectedTrashPointID;
    }

    public void setUserSelectedTrashPointID(String id){
        userSelectedTrashPointID = id;
    }


    /**
     * This function creates a Private Trash Collection Point based on information entered by user so that it can be saved.
     * @param name Name of Trash Collection Point
     * @param address Address of Trash Collection Point
     * @param zip ZIP code of Trash Collection Point
     * @param contactDetail Contact details to be used at Trash Collection Point
     * @param trashTypes Trash types collected at Trash Collection Point
     * @param units Units of trash types
     * @param trashNames Sub-Trash Types. To be entered if you wish to collect Cash-For-Trash
     * @param trashPrices The trash prices of the Sub-Trash Types. To be entered if you wish to collect Cash-For-Trash
     * @param openTime The opening time of the Trash Collection Point
     * @param closeTime The closing time of the Trash Collection Point
     * @param description The preference of the Trash Collector
     * @param days The days which the Trash Collection Point is open
     * @param context The context of the current activity
     */
    public void createPrivateTrashCollectionPoint(String name, String address, String zip, String contactDetail, ArrayList<String> trashTypes,ArrayList<String> units, ArrayList<String> trashNames, ArrayList<Double> trashPrices, String openTime, String closeTime, String description, int[] days,Context context) {

        Log.d(TAG, "createPrivateTrashCollectionPoint: creating..");

        ArrayList<TrashInfo> trashInfoList = new ArrayList<TrashInfo>();
        Log.d(TAG, "createPrivateTrashCollectionPoint: length of trashtypes " + trashTypes.size());
        for(int i=0;i<trashTypes.size();i++){
            if(!trashTypes.get(i).equalsIgnoreCase("Cash For Trash")) {
                TrashInfo trashinfo = new TrashInfo(trashTypes.get(i));
                Log.d(TAG, "creating trash info object:"+trashinfo.getTrashType());
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
        //Log.d(TAG, "createPrivateTrashCollectionPoint: "+ptcp.getTrash().get(0).getTrashType());
        UserManager.getInstance().addPrivateTrashCollectionPointToUser(ptcp);
        Log.d(TAG, "createPrivateTrashCollectionPoint: adding private trach collection point to user");

    }

    public TrashCollectionPoint getUserSelectedTrashCollectionPoint(){
        return userSelectedTrashPoint;
    }

    public void setUserSelectedTrashCollectionPoint(TrashCollectionPoint tcp){
        userSelectedTrashPoint = tcp;
    }

    public LatLng getUserSelectedTrashPointCoordinates() {
        return userSelectedTrashPointCoordinates;
    }

    public void setUserSelectedTrashPointCoordinates(LatLng ustcpc) {
        userSelectedTrashPointCoordinates = ustcpc;
    }
}
