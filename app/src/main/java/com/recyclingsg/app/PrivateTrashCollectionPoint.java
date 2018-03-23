package com.recyclingsg.app;

import java.util.ArrayList;

/**
 * Created by quzhe on 2018-3-16.
 */

public class PrivateTrashCollectionPoint extends TrashCollectionPoint {
    private String ownerName;
    private String ownerId;

    //constructor
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName,ArrayList<Integer> trashPrice, int[] daysOpen,String description){

        super(name,xCoordinate,yCoordinate,openTime,closeTime,trashName, trashPrice,daysOpen,description);
    }
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashPrice, int[] daysOpen, String description){

        super(name,xCoordinate,yCoordinate,openTime,closeTime, trashPrice,daysOpen,description);
    }
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashPrice, int[] daysOpen, String description, String ownerName, String ownerID){

        super(name,xCoordinate,yCoordinate,openTime,closeTime, trashPrice,daysOpen,description);
        setOwnerName(ownerName);
        setOwnerId(ownerID);
    }
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName,ArrayList<Integer> trashPrice, int[] daysOpen,String description,String ownerName, String ownerID){

        super(name,xCoordinate,yCoordinate,openTime,closeTime,trashName, trashPrice,daysOpen,description);
        setOwnerName(ownerName);
        setOwnerId(ownerID);
    }

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
