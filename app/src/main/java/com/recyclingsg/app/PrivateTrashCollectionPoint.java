package com.recyclingsg.app;

import java.util.ArrayList;

/**
 * Created by quzhe on 2018-3-16.
 */

public class PrivateTrashCollectionPoint extends TrashCollectionPoint {
    private String ownerName;
    private String ownerId;

    /**
     *
     * @param name
     * @param xCoordinate
     * @param yCoordinate
     * @param openTime
     * @param closeTime
     * @param trashInfo
     * @param daysOpen
     * @param description
     * @param address
     */
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashInfo, int[] daysOpen, String description, String address){

        super(name,xCoordinate,yCoordinate,openTime,closeTime, trashInfo,daysOpen,description, address);
    }
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashInfo, int[] daysOpen, String description, String address, String ownerName, String ownerID){

        super(name,xCoordinate,yCoordinate,openTime,closeTime, trashInfo,daysOpen,description, address);
        setOwnerName(ownerName);
        setOwnerId(ownerID);
    }

    public PrivateTrashCollectionPoint(){};

    public String getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
