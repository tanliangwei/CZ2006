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

    //public DepositRecord(String userid, Date date, float units, TrashPrices trashPrices, float score, String TrashCollectionPointID, float Reveneue, String nameOfUser)
    public static void createDepositRecord(TrashPrices trashPrices, float units, Date date, TrashCollectionPoint trashCollectionPoint){
        UserManager.getInstance();
        String ID = UserManager.getUserId();
        String Name = UserManager.getUserName();

        //get score and revenue
        ScoreManager.getInstance();
        float score = ScoreManager.calculateScore(trashPrices, units);
        float revenue = calculateRevenue(trashPrices, units);


        DepositRecord dr = new DepositRecord(ID, date,units, trashPrices,score,"id",revenue,Name);


    }

    public static float calculateRevenue(TrashPrices trashPrices, float Units){
        float price = trashPrices.getPrices();
        return price*Units;
    }

}
