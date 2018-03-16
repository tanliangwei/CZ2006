package com.recyclingsg.app;

import java.util.ArrayList;

/**
 * Created by tanliangwei on 16/3/18.
 */

public class DatabaseManager {

    //The attributes
    private static ArrayList<PublicTrashCollectionPoint> EWastePublicTrashCollectionPoints;
    private static ArrayList<PublicTrashCollectionPoint> RecyclablesPublicTrashCollectionPoints;
    private static ArrayList<PublicTrashCollectionPoint> CashForTrashPublicTrashCollectionPoints;

    //get and set functions
    public static void setEWastePublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        EWastePublicTrashCollectionPoints = list;
    }
    public static void setRecyclablesPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        RecyclablesPublicTrashCollectionPoints = list;
    }
    public static void setCashForTrashPublicTrashCollectionPoints(ArrayList<PublicTrashCollectionPoint> list){
        CashForTrashPublicTrashCollectionPoints = list;
    }
    public static ArrayList<PublicTrashCollectionPoint> getEWastePublicTrashCollectionPoints(){return EWastePublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getRecyclablesPublicTrashCollectionPoints(){return RecyclablesPublicTrashCollectionPoints;}
    public static ArrayList<PublicTrashCollectionPoint> getCashForTrashPublicTrashCollectionPoints(){return CashForTrashPublicTrashCollectionPoints;}

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
    public static void loadData(){
        pullPublicCashForTrash();
        pullPublicEWasteFromDatabase();
        pullPublicRecyclablesFromDatabase();
    }

    //API functions
    public static void pullPublicEWasteFromDatabase(){}
    public static void pullPublicRecyclablesFromDatabase(){}
    public static void pullPublicCashForTrash(){}


    //function for querying
    public static ArrayList<TrashCollectionPoint> queryCollectionPoint(TrashPrices trashQuery){
        ArrayList<TrashCollectionPoint> implementthisshit = new ArrayList<TrashCollectionPoint>();
        return implementthisshit;
    }

    /**
     * add private trash collection point
     * @param collectionPoint the collection point to add
     * @return true if success
     */
    public boolean addPrivatePoint(PrivateTrashCollectionPoint collectionPoint){
        return true;
    }


}
