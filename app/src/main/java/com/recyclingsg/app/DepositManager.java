package com.recyclingsg.app;

import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by tanliangwei on 23/3/18.
 */

public class DepositManager {

    private static final String TAG = "DepositManager";

    //the constructor and instance management code
    private static DepositManager instance;
    //this ensures that there is only one instance of  DepositManager in the whole story
    public static DepositManager getInstance(){
        if (instance == null) {
            try {
                instance = new DepositManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct DepositManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }

    //constructor for database manger
    public DepositManager(){}

    //public DepositRecord(String userid, Date date, float units, TrashInfo trashInfo, float score, String TrashCollectionPointID, float Reveneue, String nameOfUser)
    public DepositRecord createDepositRecord(TrashInfo trashInfo, float units, Date date, TrashCollectionPoint trashCollectionPoint){
        String UserID = UserManager.getInstance().getUserId();
        String UserName = UserManager.getInstance().getUserName();

        //get score and revenue
        float score = ScoreManager.getInstance().calculateScore(trashInfo, units);
        float revenue = calculateRevenue(trashInfo, units);

        String trashCollectionPointID =  trashCollectionPoint.getTrashCollectionPointID();


        DepositRecord dr = new DepositRecord(UserID, date,units, trashInfo,score,trashCollectionPointID,revenue,UserName);

        //adding to data base
        DatabaseInterface databaseManager = DatabaseManager.getInstance();
        databaseManager.addDepositRecord(dr);

        return dr;
    }

    // to calculate revenue
    //TODO TrashInfo compatibility issue
    public float calculateRevenue(TrashInfo trashInfo, float Units){
        // float price = trashInfo.getPrices();
        return 0;
    }

}

//calculateRevenuee