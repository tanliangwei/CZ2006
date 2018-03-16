package com.recyclingsg.app;

import java.util.ArrayList;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class DatabaseManager {

    //The attributes
    private ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints;
    private ArrayList<PublicTrashCollectionPoint> RecyclablesPublicTrashCollectionPoints;
    private ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints;

    //get and set functions
    public void setEWastePublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        EWastePublicTrashCollectionPoints = list;
    }
    public void setRecyclablesPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        RecyclablesPublicTrashCollectionPoints = list;
    }
    public void setCashForTrashPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        CashForTrashPublicTrashCollectionPoints = list;
    }
    public ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getRecyclablesPublicTrashCollectionPoints(){return RecyclablesPublicTrashCollectionPoints;}
    public ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

    //the constructor and instance management code
    private static DatabaseManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    //constructor for database manger
    public DatabaseManager(){}

    //loads all data. This is called in the startup class
    public void loadData(){
        pullPublicCashForTrash();
        pullPublicEWasteFromDatabase();
        pullPublicRecyclablesFromDatabase();
    }

    //API functions
    public void pullPublicEWasteFromDatabase(){}
    public void pullPublicRecyclablesFromDatabase(){}
    public void pullPublicCashForTrash(){}


    //function for querying
    public ArrayList<TrashCollectionPoint> queryCollectionPoint(TrashPrices trashQuery){
        ArrayList<TrashCollectionPoint> implementthisshit = new ArrayList<TrashCollectionPoint>();
        return implementthisshit;
    }


}
