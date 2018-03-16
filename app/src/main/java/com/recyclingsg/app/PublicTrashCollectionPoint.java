package com.recyclingsg.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class PublicTrashCollectionPoint extends TrashCollectionPoint {

    public PublicTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName, int[] daysOpen ){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(yCoordinate,xCoordinate);
        setCoordinate(coordinates);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        ArrayList<TrashPrices> trashPrices = new ArrayList<TrashPrices>();
        for(String t:trashName){
            TrashPrices trash = new TrashPrices(t, 0);
            trashPrices.add(trash);
        }
        setTrash(trashPrices);
        setDayOpen(daysOpen);
    }

}
