package com.recyclingsg.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class PublicTrashCollectionPoint extends TrashCollectionPoint {

    public PublicTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName, ArrayList<Integer> trashCost, int[] daysOpen,String description ){
        super(name, xCoordinate, yCoordinate, openTime, closeTime, trashName, trashCost,daysOpen,description );
    }

}
