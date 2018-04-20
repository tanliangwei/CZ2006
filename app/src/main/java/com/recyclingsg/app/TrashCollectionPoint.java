package com.recyclingsg.app;


import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * This abstract class contains the basic attributes of a Trash Collection Point. Extend from it and add your own extra features and functions.
 * @author Honey Stars
 * @version 1.0
 */

public abstract class TrashCollectionPoint{
    private String TrashCollectionPointID;
    private String collectionPointName;
    private int zipCode;
    private Date openTime;
    private Date closeTime;
    private ArrayList<TrashInfo> trash;
    private LatLng coordinate;
    private int[] dayOpen;
    private String description;
    private String address;


    /**
     * This constructs a Trash Collection Point
     * @param name The name of the Trash Collection Point
     * @param xCoordinate The X-Coordinate of the Trash Collection Point for navigation purposes
     * @param yCoordinate The Y-Coordinate of the Trash Collection Point for navigation purposes
     * @param openTime The opening time of the Trash Collection Point in HHMM format
     * @param closeTime The opening time of the Trash Collection Point in HHMM format
     * @param trashName The array of trash categories in which this trash collection point accepts
     * @param trashCost The respective prices for each trash category
     * @param daysOpen An array of size 7 which indicates the days in a week which the Trash Collection Point is open
     * @param description A description of the Trash Collection Point and preferences with regards to trash collection
     * @param address The address of the Trash Collection Point
     */
    public TrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName, ArrayList<Integer> trashCost, int[] daysOpen,String description,String address){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(xCoordinate,yCoordinate);
        setCoordinate(coordinates);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        ArrayList<TrashInfo> trashPrices = new ArrayList<TrashInfo>();
        for(int i=0;i<trashName.size();i++){
            TrashInfo trash = new TrashInfo(trashName.get(i));
            trashPrices.add(trash);
        }
        setTrash(trashPrices);
        setDayOpen(daysOpen);
        setDescription(description);
        setAddress(address);
    }

    /**
     * This constructs a Trash Collection Point
     * @param name The name of the Trash Collection Point
     * @param xCoordinate The X-Coordinate of the Trash Collection Point for navigation purposes
     * @param yCoordinate The Y-Coordinate of the Trash Collection Point for navigation purposes
     * @param openTime The opening time of the Trash Collection Point in HHMM format
     * @param closeTime The opening time of the Trash Collection Point in HHMM format
     * @param trashInfo Trash information regarding the Trash Collection Point
     * @param daysOpen An array of size 7 which indicates the days in a week which the Trash Collection Point is open
     * @param description A description of the Trash Collection Point and preferences with regards to trash collection
     * @param address The address of the Trash Collection Point
     */
    public TrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashInfo> trashInfo, int[] daysOpen, String description, String address ){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(xCoordinate,yCoordinate);
        setCoordinate(coordinates);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setTrash(trashInfo);
        setDayOpen(daysOpen);
        setDescription(description);
        setAddress(address);
    }

    /**
     * This constructs a Trash Collection Point. Go ahead and set the attributes yourself.
     */
    public TrashCollectionPoint(){};



    /**
     * This returns the ID of the Trash Collection Point. It's unique identifier.
     * @return the ID of the Trash Collection Point
     */
    public String getTrashCollectionPointID() {
        return TrashCollectionPointID;
    }

    /**
     * This sets the ID of the Trash Collection Point. It's unique identifier.
     * @param trashCollectionPointID the ID that u set which is allocated by the SQLite Databse
     */
    public void setTrashCollectionPointID(String trashCollectionPointID) {
        TrashCollectionPointID = trashCollectionPointID;
    }

    /**
     * This returns you the Trash Collection Point Name
     * @return the Trash Collection Point Name
     */
    public String getCollectionPointName(){
        return collectionPointName;
    }

    /**
     * Sets the Trash Collection Point name.
     * @param name The name which you wish to set
     */
    public void setCollectionPointName(String name){
        this.collectionPointName = name;
    }

    /**
     * This returns you the Trash Collection Point's ZIP code
     * @return the ZIP code of the Trash Collection Point
     */
    public int getZipCode(){
        return zipCode;
    }

    /**
     * This sets the ZIP code of the Trash Collection Point
     * @param zipCode The ZIP code of the Trash Collection Point
     */
    public void setZipCode(String zipCode) {this.zipCode = Integer.parseInt(zipCode);}


    /**
     * This returns you the opening time of the Trash Collection Point
     * @return Date object containing the opening time of the Trash Collection Point
     */
    public Date getOpenTime(){
        return openTime;
    }

    /**
     * Sets the opening time of the Trash Collection Point
     * @param openTime Date object containing the opening time of the Trash Collection Point
     */
    public void setOpenTime(Date openTime){
        this.openTime=openTime;
    }

    /**
     * Sets the opening time of the Trash Collection Point
     * @param openTime Integer containing opening Time in the HHMM format.
     */
    public void setOpenTime(int openTime){
        //getting the hours
        int hours = openTime/100;
        int minutes = openTime%100;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE, minutes);

        setOpenTime(calendar.getTime());
    }


    /**
     * This returns you the closing time of the Trash Collection Point
     * @return Date object containing the closing time of the Trash Collection Point
     */
    public Date getCloseTime(){return closeTime;}

    /**
     * Sets the closing time of the Trash Collection Point
     * @param closeTime Date object containing the closing time of the Trash Collection Point
     */
    public void setCloseTime(Date closeTime){ this.closeTime = closeTime;}

    /**
     * Sets the closing time of the Trash Collection Point
     * @param closeTime Date object containing the closing time of the Trash Collection Point
     */
    public void setCloseTime(int closeTime){
        //getting the hours
        int hours = closeTime/100;
        int minutes = closeTime%100;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE, minutes);

        setCloseTime(calendar.getTime());
    }


    /**
     * This returns trash information about the Trash Collection Point
     * @return ArrayList of TrashInfo about the Trash Collection Point
     */
    public ArrayList<TrashInfo> getTrash(){return trash; }

    /**
     * This function sets the trash information about the Trash Collection Point
     * @param trashInfo ArrayList of TrashInfo about the Trash Collection Point
     */
    public void setTrash(ArrayList<TrashInfo> trashInfo){
        this.trash=trashInfo;
    }


    public void setCoordinate(LatLng coordinate){this.coordinate=coordinate;}
    public LatLng getCoordinate(){return coordinate;}

    public void setDayOpen(int[] dayOpen){this.dayOpen=dayOpen;}
    public int[] getDayOpen(){return dayOpen;}

    /**
     * This returns the opening time of the Trash Collection Point
     * @return The opening time of the Trash Collection Point in HHMM format
     */
    public int getOpenTimeInInt(){
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String openTimeDateString = sdf.format(this.getOpenTime());
        int openTimeInt = Integer.parseInt(openTimeDateString);
        return openTimeInt;
    }

    /**
     * This returns the closing time of the Trash Collection Point
     * @return The closing time of the Trash Collection Point in HHMM format
     */
    public int getCloseTimeInInt(){
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String closeTimeDateString = sdf.format(this.getOpenTime());
        int closeTimeInt = Integer.parseInt(closeTimeDateString);
        return closeTimeInt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

}
