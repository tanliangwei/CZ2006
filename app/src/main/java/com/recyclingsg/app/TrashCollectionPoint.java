package com.recyclingsg.app;


import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tanliangwei on 16/3/18.
 */

public abstract class TrashCollectionPoint{
    private String TrashCollectionPointID;
    private String collectionPointName;
    private int zipCode;
    private Date openTime;
    private Date closeTime;
    private ArrayList<TrashPrices> trash;
    private LatLng coordinate;
    private int[] dayOpen;
    private String description;
    private String address;

    public String getTrashCollectionPointID() {
        return TrashCollectionPointID;
    }
    public void setTrashCollectionPointID(String trashCollectionPointID) {
        TrashCollectionPointID = trashCollectionPointID;
    }

    public void setCollectionPointName(String name){
        this.collectionPointName = name;
    }
    public String getCollectionPointName(){
        return collectionPointName;
    }

    public void setZipCode(int zipCode){
        this.zipCode = zipCode;
    }
    public int getZipCode(){
        return zipCode;
    }

    public void setOpenTime(Date openTime){
        this.openTime=openTime;
    }
    public void setOpenTime(int x){
        //getting the hours
        int hours = x/100;
        int minutes = x%100;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE, minutes);

        setOpenTime(calendar.getTime());
    }
    public Date getOpenTime(){
        return openTime;
    }

    public void setCloseTime(Date closeTime){ this.closeTime = closeTime;}
    public void setCloseTime(int x){
        //getting the hours
        int hours = x/100;
        int minutes = x%100;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE, minutes);

        setCloseTime(calendar.getTime());
    }
    public Date getCloseTime(){return closeTime;}

    public void setTrash(ArrayList<TrashPrices> trash){
        this.trash=trash;
    }
    public ArrayList<TrashPrices> getTrash(){return trash; }

    public void setCoordinate(LatLng coordinate){this.coordinate=coordinate;}
    public LatLng getCoordinate(){return coordinate;}

    public void setDayOpen(int[] dayOpen){this.dayOpen=dayOpen;}
    public int[] getDayOpen(){return dayOpen;}

    public int getOpenTimeInInt(){
        DateFormat sdf = new SimpleDateFormat("HHmm");
        String openTimeDateString = sdf.format(this.getOpenTime());
        int openTimeInt = Integer.parseInt(openTimeDateString);
        return openTimeInt;
    }

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

    //constructor
    public TrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<String> trashName, ArrayList<Integer> trashCost, int[] daysOpen,String description){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(xCoordinate,yCoordinate);
        setCoordinate(coordinates);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        ArrayList<TrashPrices> trashPrices = new ArrayList<TrashPrices>();
        for(int i=0;i<trashPrices.size();i++){
            TrashPrices trash = new TrashPrices(trashName.get(i), trashCost.get(i));
            trashPrices.add(trash);
        }
        setTrash(trashPrices);
        setDayOpen(daysOpen);
        setDescription(description);
    }

    public TrashCollectionPoint(String name, double xCoordinate, double yCoordinate, int openTime, int closeTime, ArrayList<TrashPrices> trashPrices, int[] daysOpen,String description ){
        setCollectionPointName(name);
        LatLng coordinates = new LatLng(xCoordinate,yCoordinate);
        setCoordinate(coordinates);
        setOpenTime(openTime);
        setCloseTime(closeTime);
        setTrash(trashPrices);
        setDayOpen(daysOpen);
        setDescription(description);
    }
}
