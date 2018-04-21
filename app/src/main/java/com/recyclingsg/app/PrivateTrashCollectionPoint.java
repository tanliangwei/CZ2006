package com.recyclingsg.app;

import java.util.ArrayList;

/**
 * This class is a Public Trash Collection Point. It represents public firms and organisation which collects trash.
 * @author Honey Stars
 * @version 1.0
 */

public class PrivateTrashCollectionPoint extends TrashCollectionPoint {
    private String ownerName;
    private String ownerId;

    /**
     * This constructs a Trash Collection Point
     * @param name The name of the Trash Collection Point
     * @param xCoordinate The X-Coordinate of the Trash Collection Point for navigation purposes
     * @param yCoordinate The Y-Coordinate of the Trash Collection Point for navigation purposes
     * @param openTime The opening time of the Trash Collection Point in HHMM format
     * @param closeTime The opening time of the Trash Collection Point in HHMM format
     * @param trashInfo The array of trash categories in which this trash collection point accept
     * @param daysOpen An array of size 7 which indicates the days in a week which the Trash Collection Point is open
     * @param description A description of the Trash Collection Point and preferences with regards to trash collection
     * @param address The address of the Trash Collection Point
     */
    public PrivateTrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashInfo, int[] daysOpen, String description, String address){

        super(name,xCoordinate,yCoordinate,openTime,closeTime, trashInfo,daysOpen,description, address);
    }

    /**
     *
     * This constructs a Trash Collection Point
     * @param name The name of the Trash Collection Point
     * @param xCoordinate The X-Coordinate of the Trash Collection Point for navigation purposes
     * @param yCoordinate The Y-Coordinate of the Trash Collection Point for navigation purposes
     * @param openTime The opening time of the Trash Collection Point in HHMM format
     * @param closeTime The opening time of the Trash Collection Point in HHMM format
     * @param trashInfo The array of trash categories in which this trash collection point accept
     * @param daysOpen An array of size 7 which indicates the days in a week which the Trash Collection Point is open
     * @param description A description of the Trash Collection Point and preferences with regards to trash collection
     * @param address The address of the Trash Collection Point
     * @param ownerName The owner's name
     * @param ownerID The owner's ID Unique Identifier
     */
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
