package com.recyclingsg.app;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class PublicTrashCollectionPoint extends TrashCollectionPoint {

    public PublicTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, String trash, int[] daysOpen ){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(yCoordinate,xCoordinate);
        setOpenTime(openTime);
        setCloseTime(closeTime);


    }

}
