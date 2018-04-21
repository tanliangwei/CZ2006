package com.recyclingsg.app.control;

import android.util.Log;

import com.recyclingsg.app.entity.TrashInfo;

/**
 * This class is the Score Manager Class. It is in charge of calculating the scores earned for deposits.
 * @author Honey Stars
 * @version 1.0
 */

public class ScoreManager {
    private static final String TAG = "ScoreManager";

    //the constructor and instance management code
    private static ScoreManager instance;

    /**
     * This returns a singleton instance of the Score Manager.
     * @return Singleton instance of Score Manager
     */
    public static ScoreManager getInstance(){
        if (instance == null) {
            try {
                instance = new ScoreManager();
            } catch (Exception e) {
                Log.e(TAG, "failed to construct ScoreManager instance");
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Creates an instance of Score Manager.
     */
    private ScoreManager(){}

    /**
     * This function calculates the score obtained by the particular deposit.
     * @param trashInfo Trash Information object containing information about the trash being deposited
     * @param units Units of Trash deposited
     * @return The score to rewarded to users, -1 if the units param is negative
     */
    public float calculateScore(TrashInfo trashInfo, float units){
        if (units<=0)
            return 0;
        switch(trashInfo.getTrashTypeForSpinner()) {
            case ("Second Hand Goods"):
                return units * 2;
            case ("    EWaste    "):
                return units * 3;
            case ("Cash For Trash"):
                return units * 4;
        }
        return (units);
    }
}
