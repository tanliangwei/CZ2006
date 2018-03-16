package com.recyclingsg.app;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by tanliangwei on 16/3/18.
 */

public abstract class TrashCollectionPoint{
    private String collectionPointName;
    private int zipCode;
    private Date openTime;
    private Date closetime;
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
    public Date getOpenTime(){
        return openTime;
    }

    public void setClosetime(Date closetime){ closetime = this.closetime;}
    public Date getClosetime(){return closetime;}

    public void setTrash(ArrayList<TrashPrices> trash){
        trash=this.trash;
    }
    public ArrayList<TrashPrices> getTrash(){return trash; }

    public void setCoordinate(LatLng coordinate){coordinate=this.coordinate;}
    public LatLng getCoordinate(){return coordinate;}

    public void setDayOpen(int[] dayOpen){dayOpen=this.dayOpen;}
    public int[] getDayOpen(){return dayOpen;}

}
