package com.recyclingsg.app;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tanliangwei on 16/3/18.
 */

public abstract class TrashCollectionPoint{
    private String collectionPointName;
    private int zipCode;
    private Date openTime;
    private Date closeTime;
    private ArrayList<TrashPrices> trash;
    private LatLng coordinate;
    private int[] dayOpen;

    public void setCollectionPointName(String name){
        collectionPointName = name;
    }
    public String getCollectionPointName(){
        return collectionPointName;
    }

    public void setZipCode(int zipCode){
        zipCode = this.zipCode;
    }
    public int getZipCode(){
        return zipCode;
    }

    public void setOpenTime(Date openTime){
        openTime=this.openTime;
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

    public void setCloseTime(Date closetime){ closetime = this.closeTime;}
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
        trash=this.trash;
    }
    public ArrayList<TrashPrices> getTrash(){return trash; }

    public void setCoordinate(LatLng coordinate){coordinate=this.coordinate;}
    public LatLng getCoordinate(){return coordinate;}

    public void setDayOpen(int[] dayOpen){dayOpen=this.dayOpen;}
    public int[] getDayOpen(){return dayOpen;}

}
