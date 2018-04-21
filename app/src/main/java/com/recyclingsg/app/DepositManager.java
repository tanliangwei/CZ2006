package com.recyclingsg.app;

import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * This class is a the Deposit Manager class. It is used to handle deposition of trash.
 * @author Honey Stars
 * @version 1.0
 */

public class DepositManager {

    private static final String TAG = "DepositManager";
    DatabaseInterface databaseManager;

    public void addDatabaseInterface(DatabaseInterface db){
        this.databaseManager = db;
    }

    //the constructor and instance management code
    private static DepositManager instance;
    //this ensures that there is only one instance of  DepositManager in the whole story
    /**
     * This returns a singleton instance of the Deposit Manager.
     * @return Singleton instance of Deposit Manager
     */
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

    /**
     * Creates an instance of Deposit Manager
     */
    public DepositManager(){}

    /**
     * Creates the Deposit Record using the information written by the User.
     * @param trashInfo The trash category which the User deposited
     * @param units The units of trash deposited
     * @param date The date in which the User deposited
     * @param trashCollectionPoint The Trash Collection Point where the User deposited the trash
     * @return The Deposit Record which was created by the User
     */
    public DepositRecord createDepositRecord(TrashInfo trashInfo, float units, Date date, TrashCollectionPoint trashCollectionPoint){
        String UserID = UserManager.getInstance().getUserId();
        String UserName = UserManager.getInstance().getUserName();

        //get score and revenue
        float score = ScoreManager.getInstance().calculateScore(trashInfo, units);
        float revenue = calculateRevenue(trashInfo, units);

        String trashCollectionPointID =  trashCollectionPoint.getTrashCollectionPointID();


        DepositRecord dr = new DepositRecord(UserID, date,units, trashInfo,score,trashCollectionPointID,revenue,UserName);

        //adding to data base
        databaseManager.addDepositRecord(dr);

        return dr;
    }

    // to calculate revenue

    /**
     * This function returns the revenue earned by the User
     * @param trashInfo The Trash Information of the trash the User deposited which contains price information
     * @param Units units of the trash deposited
     * @return The revenue earned by depositing
     */
    public float calculateRevenue(TrashInfo trashInfo, float Units){
        // float price = trashInfo.getPrices();
        return 0;
    }

}

//calculateRevenuee