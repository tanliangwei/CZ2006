package com.recyclingsg.app;

import android.util.Log;

import java.util.Date;

/**
 * Created by tanliangwei on 23/3/18.
 */

public class DepositManager {

    private static final String TAG = "DepositManager";

    //the constructor and instance management code
    private static DepositManager instance;
    //this ensures that there is only one instance of  DatabaseManager in the whole story
    public static DepositManager getInstance(){
        if (instance == null) {
            try {
                instance = new DepositManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct DatabaseManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }
    //constructor for database manger
    public DepositManager(){}

    public static void createDepositRecord(TrashPrices trashPrices, float unit, Date date, TrashCollectionPoint trashCollectionPoint){
        UserManager.getInstance();
        String ID = UserManager.getUserId();

        
    }
}
